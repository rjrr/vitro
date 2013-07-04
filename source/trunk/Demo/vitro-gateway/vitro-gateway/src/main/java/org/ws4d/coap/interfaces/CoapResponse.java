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
package org.ws4d.coap.interfaces;

import org.ws4d.coap.messages.CoapResponseCode;
/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 */

public interface CoapResponse extends CoapMessage{
	
	/* TODO: Response Code is part of BasicCoapResponse */
	public CoapResponseCode getResponseCode();
	public void setMaxAge(int maxAge);
    public long getMaxAge();
    
    public void setETag(byte[] etag);
    public byte[] getETag();
    public void setResponseCode(CoapResponseCode responseCode);

		
}
