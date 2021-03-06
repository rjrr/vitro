package ch.ethz.inf.vs.californium.examples.resources;

import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.endpoint.LocalResource;


/*
 * This class implements a 'separate' resource for demonstration purposes.
 * 
 * Defines a resource that returns a response in a separate CoAP Message
 *  
 * @author Dominique Im Obersteg & Daniel Pauli
 * @version 0.1
 * 
 */
public class CarelessResource extends LocalResource {

	public CarelessResource() {
		super("careless");
		setTitle("This resource will ACK anything, but never send a separate response");
		setResourceType("SepararateResponseTester");
	}

	@Override
	public void performGET(GETRequest request) {

		// promise the client that this request will be acted upon
		// by sending an Acknowledgement...
		request.accept();

		// ... and then do nothing. Pretty mean.
	}
}
