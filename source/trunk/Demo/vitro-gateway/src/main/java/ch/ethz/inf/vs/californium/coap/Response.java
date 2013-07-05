package ch.ethz.inf.vs.californium.coap;

/**
 * The Class Response describes the functionality of a CoAP Response as
 * a subclass of a CoAP {@link Message}. It is usually linked to a {@link Request} and
 * supports the handling of Request/Response pairs.
 * 
 * @author Dominique Im Obersteg, Daniel Pauli, and Matthias Kovatsch
 */
public class Response extends Message {

// Constructors ////////////////////////////////////////////////////////////////

	// TODO get rid off
	public Response() {
		this(CodeRegistry.RESP_VALID);
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param method the status code of the message
	 */
	public Response(int status) {
		setCode(status);
	}

// Methods /////////////////////////////////////////////////////////////////////
	
	public void setRequest(Request request) {
		this.request = request;
	}

	public Request getRequest() {
		return request;
	}

	/**
	 * Returns the round trip time in milliseconds (nano precision).
	 * @return RTT in ms
	 */
	public double getRTT() {
		if (request != null) {
			return (double)(getTimestamp() - request.getTimestamp())/1000000d;
		} else {
			return -1d;
		}
	}

	public void handle() {
		if (request != null) {
			request.handleResponse(this);
		}
	}

	@Override
	protected void payloadAppended(byte[] block) {
		if (request != null) {
			request.responsePayloadAppended(this, block);
		}
	}

	@Override
	public void handleBy(MessageHandler handler) {
		handler.handleResponse(this);
	}

	public boolean isPiggyBacked() {
		return isAcknowledgement() && getCode() != CodeRegistry.EMPTY_MESSAGE;
	}

	private Request request;
}
