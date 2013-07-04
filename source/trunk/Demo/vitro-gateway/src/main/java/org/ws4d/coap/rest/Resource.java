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
package org.ws4d.coap.rest;

import java.util.Vector;

/**
 * A resource known from the REST architecture style. A resource has a type,
 * name and data associated with it.
 * 
 * @author Nico Laum <nico.laum@uni-rostock.de>
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 * 
 */
public interface Resource {
    /**
     * Get the MIME Type of the resource (e.g., "application/xml")
     * @return The MIME Type of this resource as String.
     */
    public String getMimeType();

    /**
     * Get the unique name of this resource
     * @return The unique name of the resource.
     */
    public String getPath();

    public String getShortName();

    public byte[] getValue();
    
    public byte[] getValue(Vector<String> query);
    
    //TODO: bad api: no return value
    public void post(byte[] data);
    
    public String getResourceType();
    
	public void registerServerListener(ResourceServer server);
	
	public void unregisterServerListener(ResourceServer server);
    
}
