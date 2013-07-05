package ch.ethz.inf.vs.californium.endpoint;

import java.net.SocketException;

import ch.ethz.inf.vs.californium.coap.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.Communicator;
import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.coap.ObservingManager;
import ch.ethz.inf.vs.californium.coap.OptionNumberRegistry;
import ch.ethz.inf.vs.californium.coap.PUTRequest;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.util.Properties;

/**
 * The class LocalEndpoint provides the functionality of a server endpoint
 * as a subclass of {@link Endpoint}. A server implementation using Cf will
 * override this class to provide custom resources. Internally, the main
 * purpose of this class is to forward received requests to the corresponding
 * resource specified by the Uri-Path option. Furthermore, it implements the
 * root resource to return a brief server description to GET requests with
 * empty Uri-Path.
 * 
 * @author Dominique Im Obersteg, Daniel Pauli, and Matthias Kovatsch
 */
public class LocalEndpoint extends Endpoint {
	
	public static final String ENDPOINT_INFO = 
		"************************************************************\n" +
		"This server is using the Californium (Cf) CoAP framework\n" +
		"developed by Dominique Im Obersteg, Daniel Pauli, and\n" +
		"Matthias Kovatsch.\n" +
		"Cf is available under BSD 3-clause license on GitHub:\n" +
		"https://github.com/mkovatsc/Californium\n" +
		"\n" +
		"(c) 2012, Institute for Pervasive Computing, ETH Zurich\n" +
		"Contact: Matthias Kovatsch <kovatsch@inf.ethz.ch>\n" +
		"************************************************************";

	private class RootResource extends LocalResource {

		public RootResource() {
			super("", true);
		}

		@Override
		public void performGET(GETRequest request) {

			// create response
			Response response = new Response(CodeRegistry.RESP_CONTENT);

			response.setPayload(ENDPOINT_INFO);

			// complete the request
			request.respond(response);
		}
	}

	// TODO Constructor with custom root resource; check for resourceIdentifier==""
	
	public LocalEndpoint(int port, int defaultBlockSze, boolean daemon) throws SocketException {

		// initialize communicator
		Communicator.setupPort(port);
		Communicator.setupTransfer(defaultBlockSze);
		Communicator.setupDeamon(daemon);
		Communicator.getInstance().registerReceiver(this);

		// initialize resources
		this.rootResource = new RootResource();
		this.addResource(new DiscoveryResource(this.rootResource));
	}

	public LocalEndpoint(int port, int defaultBlockSze) throws SocketException {
		this(port, defaultBlockSze, false); // no daemon, keep JVM running to handle requests
	}
	public LocalEndpoint(int port) throws SocketException {
		this(port, 0); // let TransferLayer decide default
	}
	public LocalEndpoint() throws SocketException {
		this(Properties.std.getInt("DEFAULT_PORT"));
	}

	@Override
	public void execute(Request request) {

		// check if request exists
		if (request != null) {

			// retrieve resource identifier
			String resourcePath = request.getUriPath();

			// lookup resource
			LocalResource resource = getResource(resourcePath);

			// check if resource available
			if (resource != null) {
				
				LOG.info(String.format("Dispatching execution: %s", resourcePath));

				// invoke request handler of the resource
				request.dispatch(resource);

				// check if resource did generate a response
				if (request.getResponse()!=null) {
				
					// check if resource is to be observed
					if (resource.isObservable() &&
						request instanceof GETRequest &&
						CodeRegistry.responseClass(request.getResponse().getCode())==CodeRegistry.CLASS_SUCCESS) {
						
						if (request.hasOption(OptionNumberRegistry.OBSERVE)) {
							
							// establish new observation relationship
							ObservingManager.getInstance().addObserver((GETRequest) request, resource);
	
						} else if (ObservingManager.getInstance().isObserved(request.getPeerAddress().toString(), resource)) {
	
							// terminate observation relationship on that resource
							ObservingManager.getInstance().removeObserver(request.getPeerAddress().toString(), resource);
						}
						
					}
					
					// send response here
					request.sendResponse();
				}
			
			} else if (request instanceof PUTRequest) {
				// allows creation of non-existing resources through PUT
				this.createByPUT((PUTRequest) request);
				
			} else {
				// resource does not exist
				LOG.info(String.format("Cannot find resource: %s", resourcePath));

				request.respond(CodeRegistry.RESP_NOT_FOUND);
				request.sendResponse();
			}
		}
	}

	/**
	 * Delegates a {@link PUTRequest} for a non-existing resource to the
	 * {@link LocalResource#createSubResource(Request, String)} method of the
	 * first existing resource up the path.
	 * 
	 * @param request - the PUT request
	 */
	private void createByPUT(PUTRequest request) {

		String path = request.getUriPath(); // always starts with "/"
		
		// find existing parent up the path
		String parentIdentifier = new String(path);
		String newIdentifier = "";
		Resource parent = null;
		// will end at rootResource ("")
		do {
			newIdentifier = path.substring(parentIdentifier.lastIndexOf('/')+1);
			parentIdentifier = parentIdentifier.substring(0, parentIdentifier.lastIndexOf('/'));
		} while ((parent = getResource(parentIdentifier))==null);

		parent.createSubResource(request, newIdentifier);
	}

	public LocalResource getResource(String resourcePath) {
		if (rootResource != null) {
			return (LocalResource) rootResource.getResource(resourcePath, false);
		} else {
			return null;
		}
	}

	/**
	 * Adds a resource to the root resource of the endpoint. If the resource
	 * identifier is actually a path, it is split up into multiple resources.
	 * 
	 * @param resource - the resource to add to the root resource
	 */
	public void addResource(LocalResource resource) {
		if (rootResource != null) {
			
			while (resource.getName().startsWith("/")) {
				resource.setName(resource.getName().substring(1));
			}
			
			String[] path = resource.getName().split("/");
			
			if (path.length>1) {
				
				LOG.config(String.format("Splitting up compound resource into %d: %s", path.length, resource.getName()));
				
				LocalResource base = null;
				
				resource.setName(path[path.length-1]);
				
				// insert middle segments
				for (int i=path.length-2; i >=0; --i) {
					
					// do not create middle segment that already exists
					if ((base = getResource("/"+path[i]))!=null) {
						base.addSubResource(resource);
						return;
					}
					
					base = new LocalResource(path[i], true); 
					base.addSubResource(resource);
					resource = base;
				}
			}
			rootResource.addSubResource(resource);
		}
	}

	public void removeResource(String resourceIdentifier) {
		if (rootResource != null) {
			rootResource.removeSubResource(resourceIdentifier);
		}
	}

//	@Override YOANN COMMENT
	public void handleRequest(Request request) {
		execute(request);
	}

//	@Override YOANN COMMENT
	public void handleResponse(Response response) {
		// response.handle();
	}
	
	/**
	 * Provides access to the root resource that contains all local resources,
	 * e.g., for the surrounding server code to register at an RD.
	 * 
	 * @return the root resource
	 */
	public Resource getRootResource() {
		return rootResource;
	}
}
