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
package ch.ethz.inf.vs.californium.coap;

import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * The TokenManager stores all tokens currently used in transfers. New transfers
 * can acquire unique tokens from the manager.
 * 
 * @author Matthias Kovatsch
 */
public class TokenManager {

// Logging /////////////////////////////////////////////////////////////////////
	
	private static final Logger LOG = Logger.getLogger(TokenManager.class.getName());
	
// Static Attributes ///////////////////////////////////////////////////////////
	
	// the empty token, used as default value
	public static final byte[] emptyToken = new byte[0];
	
	private static TokenManager singleton = new TokenManager();

// Members /////////////////////////////////////////////////////////////////////
	
	private Set<byte[]> acquiredTokens = new HashSet<byte[]>();

	private long currentToken;
	
// Constructors ////////////////////////////////////////////////////////////////
	
	/**
	 * Default singleton constructor.
	 */
	private TokenManager() {
		this.currentToken = (long) (Math.random() * 0x100l);
	}
	
	public static TokenManager getInstance() {
		if (singleton==null) {
			synchronized (Communicator.class) {
				if (singleton==null) {
					singleton = new TokenManager();
				}
			}
		}
		return singleton;
	}
	
// Methods /////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the next message ID to use out of the consecutive 16-bit range.
	 * 
	 * @return the current message ID
	 */
	private byte[] nextToken() {

		this.currentToken = ++this.currentToken;
		
		LOG.info("Token value: "+currentToken);
		
		long temp = this.currentToken;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(OptionNumberRegistry.TOKEN_LEN);  
		
		while (temp>0 && byteStream.size()<OptionNumberRegistry.TOKEN_LEN) {
			byteStream.write((int)(temp & 0xff));
			temp >>>= 8;
		}
		
		return byteStream.toByteArray();
	}
	
	/*
	 * Returns an unique token.
	 * 
	 * @param preferEmptyToken If set to true, the caller will receive
	 * the empty token if it is available. This is useful for reducing
	 * datagram sizes in transactions that are expected to complete
	 * in short time. On the other hand, empty tokens are not preferred
	 * in block-wise transfers, as the empty token is then not available
	 * for concurrent transactions.
	 * 
	 */
	public synchronized byte[] acquireToken(boolean preferEmptyToken) {
		
		byte[] token = null;
		if (preferEmptyToken && acquiredTokens.add(emptyToken)) {
			token = emptyToken;
		} else {
			do {
				token = nextToken();
			} while (!acquiredTokens.add(token));
		}
		
		return token;
	}
	
	public byte[] acquireToken() {
		return acquireToken(false);
	}
	
	/*
	 * Releases an acquired token and makes it available for reuse.
	 * 
	 * @param token The token to release
	 */
	public synchronized void releaseToken(byte[] token) {
		
		if (!acquiredTokens.remove(token)) {
			LOG.warning(String.format("Token to release is not acquired: %s\n", Option.hex(token)));
		}
	}
	
	/*
	 * Checks if a token is acquired by this manager.
	 * 
	 * @param token The token to check
	 * @return True iff the token is currently in use
	 */
	public synchronized boolean isAcquired(byte[] token) {
		return acquiredTokens.contains(token);
	}
	
	
}
