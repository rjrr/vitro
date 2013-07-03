/*******************************************************************************
 * Copyright (c) 2013 VITRO FP7 Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 *     Antoniou Thanasis
 *     Paolo Medagliani
 *     D. Davide Lamanna
 *     Panos Trakadas
 *     Andrea Kropp
 *     Kiriakos Georgouleas
 *     Panagiotis Karkazis
 *     David Ferrer Figueroa
 *     Francesco Ficarola
 *     Stefano Puglia
 ******************************************************************************/
package ch.ethz.inf.vs.californium.layers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.ethz.inf.vs.californium.coap.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.Message;
import ch.ethz.inf.vs.californium.coap.OptionNumberRegistry;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.TokenManager;
import ch.ethz.inf.vs.californium.util.Properties;

/**
 * This class takes care of unique tokens for each sequence of request/response
 * exchanges.
 * Additionally, the TokenLayer takes care of an overall timeout for each
 * request/response exchange.
 * 
 * @author Matthias Kovatsch
 */
public class TokenLayer extends UpperLayer {

// Members /////////////////////////////////////////////////////////////////////
	
	private Map<String, RequestResponseSequence> exchanges = new HashMap<String, RequestResponseSequence>();

	/** A timer for scheduling overall request timeouts. */
	private Timer timer = new Timer(true);
	
	/** The time to wait for requests to complete, in milliseconds. */
	private int sequenceTimeout;
	
// Nested Classes //////////////////////////////////////////////////////////////
	
	/*
	 * Entity class to keep state of transfers
	 */
	private static class RequestResponseSequence {
		public String key;
		public Request request;
		public TimerTask timeoutTask;
	}
	
	/*
	 * Utility class to provide transaction timeouts
	 */
	private class TimeoutTask extends TimerTask {
		
		private RequestResponseSequence sequence;

		public TimeoutTask(RequestResponseSequence sequence) {
			this.sequence = sequence;
		}
		
		@Override
		public void run() {
			transferTimedOut(sequence);
		}
	}
	
	// Constructors ////////////////////////////////////////////////////////////
	
	public TokenLayer(int sequenceTimeout) {
		// member initialization
		this.sequenceTimeout = sequenceTimeout;
	}
	
	public TokenLayer() {
		this(Properties.std.getInt("DEFAULT_OVERALL_TIMEOUT"));
	}

	// I/O implementation //////////////////////////////////////////////////////
	
	@Override
	protected void doSendMessage(Message msg) throws IOException { 
		
		// set token option if required
		if (msg.requiresToken()) {
			msg.setToken( TokenManager.getInstance().acquireToken(true) );
		}
		
		// use overall timeout for clients (e.g., server crash after separate response ACK)
		if (msg instanceof Request) {
			LOG.info(String.format("Requesting response for %s: %s",  ((Request) msg).getUriPath(), msg.sequenceKey()));
			addExchange((Request) msg);
		} else if (msg.getCode()==CodeRegistry.EMPTY_MESSAGE) {
			LOG.info(String.format("Accepting request: %s", msg.key()));
		} else {
			LOG.info(String.format("Responding request: %s", msg.sequenceKey()));
		}
		
		sendMessageOverLowerLayer(msg);
	}	
	
	@Override
	protected void doReceiveMessage(Message msg) {

		if (msg instanceof Response) {

			Response response = (Response) msg;
			
			RequestResponseSequence sequence = getExchange(msg.sequenceKey());

			// check for missing token
			if (sequence == null && response.getToken().length==0) {
				
				LOG.warning(String.format("Remote endpoint failed to echo token: %s", msg.key()));
				
				// TODO try to recover from peerAddress
				
				// let timeout handle the problem
				return;
			}
			
			if (sequence != null) {
				
				// cancel timeout
				sequence.timeoutTask.cancel();
				
				// TODO separate observe registry
				if (msg.getFirstOption(OptionNumberRegistry.OBSERVE)==null) {
					removeExchange(msg.sequenceKey());
				}

				LOG.info(String.format("Incoming response from %s: %s // RTT: %fms", ((Response) msg).getRequest().getUriPath(), msg.sequenceKey(), ((Response) msg).getRTT()));
				
				deliverMessage(msg);
				
			} else {
			
				LOG.warning(String.format("Dropping unexpected response: %s", response.sequenceKey()));
			}
			
		} else if (msg instanceof Request) {
			
			LOG.info(String.format("Incoming request: %s", msg.sequenceKey()));
			
			deliverMessage(msg);
		}
	}
	
	private RequestResponseSequence addExchange(Request request) {
		
		// create new Transaction
		RequestResponseSequence sequence = new RequestResponseSequence();
		sequence.key = request.sequenceKey();
		sequence.request = request;
		sequence.timeoutTask = new TimeoutTask(sequence);
		
		// associate token with Transaction
		exchanges.put(sequence.key, sequence);
		
		timer.schedule(sequence.timeoutTask, sequenceTimeout);

		LOG.fine(String.format("Stored new exchange: %s", sequence.key));
		
		return sequence;
	}
	
	private RequestResponseSequence getExchange(String key) {
		return exchanges.get(key);
	}
	
	private void removeExchange(String key) {
		
		RequestResponseSequence exchange = exchanges.remove(key);
		
		exchange.timeoutTask.cancel();
		exchange.timeoutTask = null;
		
		TokenManager.getInstance().releaseToken(exchange.request.getToken());

		LOG.finer(String.format("Cleared exchange: %s", exchange.key));
	}
	
	private void transferTimedOut(RequestResponseSequence exchange) {
		
		// cancel transaction
		removeExchange(exchange.key);
		
		LOG.warning(String.format("Request/Response exchange timed out: %s", exchange.request.sequenceKey()));
		
		// call event handler
		exchange.request.handleTimeout();
	}
	
	public String getStats() {
		StringBuilder stats = new StringBuilder();
		
		stats.append("Request-Response exchanges: ");
		stats.append(exchanges.size());
		stats.append('\n');
		stats.append("Messages sent:     ");
		stats.append(numMessagesSent);
		stats.append('\n');
		stats.append("Messages received: ");
		stats.append(numMessagesReceived);
		
		return stats.toString();
	}
}
