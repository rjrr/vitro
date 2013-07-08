package ch.ethz.inf.vs.californium.examples;

import java.net.SocketException;

import ch.ethz.inf.vs.californium.coap.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.endpoint.LocalEndpoint;
import ch.ethz.inf.vs.californium.endpoint.LocalResource;



public class HelloWorldServer extends LocalEndpoint {
	
	/*
	 * Definition of the Hello-World Resource
	 * 
	 */
	class HelloWorldResource extends LocalResource {

		public HelloWorldResource() {

			// set resource identifier
			super("helloWorld"); 
			
			// set display name
			setTitle("Hello-World Resource");
		}

		@Override
		public void performGET(GETRequest request) {

			// respond to the request
			request.respond(CodeRegistry.RESP_CONTENT, "Hello World!");
		}
	}
	
	/*
	 * Constructor for a new Hello-World server. Here, the resources
	 * of the server are initialized.
	 * 
	 */
	public HelloWorldServer() throws SocketException {
		
		// provide an instance of a Hello-World resource
		addResource(new HelloWorldResource());
	}

	/*
	 * Application entry point.
	 * 
	 */
	public static void main(String[] args) {
		
		try {
			
			// create server
			HelloWorldServer server = new HelloWorldServer();
			
			System.out.println("Server listening on port " + server.port());
			
		} catch (SocketException e) {
			
			System.err.println("Failed to initialize server: " + e.getMessage());
		}
	}
}
