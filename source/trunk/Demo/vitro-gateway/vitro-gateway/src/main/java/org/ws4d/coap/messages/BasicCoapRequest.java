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
package org.ws4d.coap.messages;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.ws4d.coap.interfaces.CoapRequest;

/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 */

public class BasicCoapRequest extends AbstractCoapMessage implements CoapRequest {
	CoapRequestCode requestCode;

	public BasicCoapRequest(byte[] bytes, int length) {
		/* length ought to be provided by UDP header */
		this(bytes, length, 0);
	}

	public BasicCoapRequest(byte[] bytes, int length, int offset) {
		serialize(bytes, length, offset);
		/* check if request code is valid, this function throws an error in case of an invalid argument */
		requestCode = CoapRequestCode.parseRequestCode(this.messageCodeValue);
		
		//TODO: check integrity of header options 
	}

	public BasicCoapRequest(CoapPacketType packetType, CoapRequestCode requestCode, int messageId) {
		this.version = 1;

		this.packetType = packetType;
		this.requestCode = requestCode;
		this.messageCodeValue = requestCode.getValue();
		this.messageId = messageId;
	}
	
    @Override
    public void setToken(byte[] token){
    	/* this function is only public for a request*/
    	super.setToken(token);
    }

	
	public CoapRequestCode getRequestCode() {
		return requestCode;
	}

	
	public void setUriHost(String host) {
		if (host == null) return;
		if (options.optionExists(CoapHeaderOptionType.Uri_Host)){
			throw new IllegalArgumentException("Uri-Host option already exists");
		}
		if (host.length() < 1 || host.length() > CoapHeaderOption.MAX_LENGTH){
			throw new IllegalArgumentException("Invalid Uri-Host option length");
		}
		/*TODO: check if host is a valid address */
		options.addOption(CoapHeaderOptionType.Uri_Host, host.getBytes());
	}

	
	public void setUriPort(int port) {
		if (port < 0) return;
		if (options.optionExists(CoapHeaderOptionType.Uri_Port)){
			throw new IllegalArgumentException("Uri-Port option already exists");
		}
		byte[] value = long2CoapUint(port);
    	if(value.length < 0 || value.length > 2){
    		throw new IllegalStateException("Illegal Uri-Port length");
    	}
		options.addOption(new CoapHeaderOption(CoapHeaderOptionType.Uri_Port, value));
	}

	
	public void setUriPath(String path) {
		if (path == null) return;
		
		if (path.length() > CoapHeaderOption.MAX_LENGTH ){
			throw new IllegalArgumentException("Uri-Path option too long");
		}
		
		/* delete old options if present */
		options.removeOption(CoapHeaderOptionType.Uri_Path);
		
		/*create substrings */
		String[] pathElements = path.split("/"); 
		/* add a Uri Path option for each part */
		for (String element : pathElements) {
			/* check length */
			if(element.length() < 0 || element.length() > CoapHeaderOption.MAX_LENGTH){
				throw new IllegalArgumentException("Invalid Uri-Path");
			} else if (element.length() > 0){
				/* ignore empty substrings */
				options.addOption(CoapHeaderOptionType.Uri_Path, element.getBytes());
			}
		}
	}

	
	public void setUriQuery(String query) {
		if (query == null) return;
		
		if (query.length() > CoapHeaderOption.MAX_LENGTH ){
			throw new IllegalArgumentException("Uri-Query option too long");
		}
		
		/* delete old options if present */
		options.removeOption(CoapHeaderOptionType.Uri_Query);
		
		/*create substrings */
		String[] pathElements = query.split("&"); 
		/* add a Uri Path option for each part */
		for (String element : pathElements) {
			/* check length */
			if(element.length() < 0 || element.length() > CoapHeaderOption.MAX_LENGTH){
				throw new IllegalArgumentException("Invalid Uri-Path");
			} else if (element.length() > 0){
				/* ignore empty substrings */
				options.addOption(CoapHeaderOptionType.Uri_Query, element.getBytes());
			}
		}
		
	}
	
	
	public void setProxyUri(String proxyUri) {
		if (proxyUri == null) return;

		if (options.optionExists(CoapHeaderOptionType.Proxy_Uri)){
			throw new IllegalArgumentException("Proxy Uri already exists");
		}
		
		if (proxyUri.length() < 1){
			throw new IllegalArgumentException("Proxy Uri must be at least one byte long");
		}
		
		if (proxyUri.length() > CoapHeaderOption.MAX_LENGTH ){
			throw new IllegalArgumentException("Proxy Uri longer then 270 bytes are not supported yet (to be implemented)");
		}
		
		options.addOption(CoapHeaderOptionType.Proxy_Uri, proxyUri.getBytes());
	}
	
    
    public Vector<String> getUriQuery(){
    	Vector<String> queryList = new Vector<String>();

    	for (CoapHeaderOption option : options) {
			if(option.getOptionType() == CoapHeaderOptionType.Uri_Query){
				queryList.add(new String(option.getOptionData()));
			}
		}
    	return queryList;
    }
    
   
    public String getUriHost(){
    	return new String(options.getOption(CoapHeaderOptionType.Uri_Host).getOptionData());
    }
   
    public int getUriPort(){
    	CoapHeaderOption option = options.getOption(CoapHeaderOptionType.Uri_Port);
    	if (option == null){
    		return -1; //TODO: return default Coap Port?
    	}
    	byte[] value = option.getOptionData(); 
   	
    	if(value.length < 0 || value.length > 2){
    		/* should never happen because this is an internal variable and should be checked during serialization */
    		throw new IllegalStateException("Illegal Uri-Port Option length");
    	}
    	/* checked length -> cast is safe*/
      	return (int)coapUint2Long(options.getOption(CoapHeaderOptionType.Uri_Port).getOptionData());
    }
    
   
	public String getUriPath() {
    	if (options.getOption(CoapHeaderOptionType.Uri_Path) == null){
    		return null;
    	}
    	
		StringBuilder uriPathBuilder = new StringBuilder();
		for (CoapHeaderOption option : options) {
			if (option.getOptionType() == CoapHeaderOptionType.Uri_Path) {
				String uriPathElement;
				try {
					uriPathElement = new String(option.getOptionData(), "UTF-8");
					uriPathBuilder.append("/");
					uriPathBuilder.append(uriPathElement);
				} catch (UnsupportedEncodingException e) {
					throw new IllegalArgumentException("Invalid Encoding");
				}
			}
		}
		return uriPathBuilder.toString();
	}
    
   
    public void addAccept(CoapMediaType mediaType){
    	options.addOption(CoapHeaderOptionType.Accept, long2CoapUint(mediaType.getValue()));
    }
   
    public Vector<CoapMediaType> getAccept(CoapMediaType mediaType){
    	if (options.getOption(CoapHeaderOptionType.Accept) == null){
    		return null;
    	}
    	Vector<CoapMediaType> acceptList = new Vector<CoapMediaType>();
		for (CoapHeaderOption option : options) {
			if (option.getOptionType() == CoapHeaderOptionType.Accept) {
				CoapMediaType accept = CoapMediaType.parse((int)coapUint2Long(option.optionData));
//				if (accept != CoapMediaType.UNKNOWN){
				/* add also UNKNOWN types to list */	
				acceptList.add(accept);
//				}
			}
		}
    	return acceptList;
    }
	
	
	public String getProxyUri(){
		CoapHeaderOption option = options.getOption(CoapHeaderOptionType.Proxy_Uri);
		if (option == null)
			return null;
		return new String(option.getOptionData());
	}

	
	public void addETag(byte[] etag) {
    	if (etag == null){
    		throw new IllegalArgumentException("etag MUST NOT be null");
    	}
    	if (etag.length < 1 || etag.length > 8){
    		throw new IllegalArgumentException("Invalid etag length");
    	}
    	options.addOption(CoapHeaderOptionType.Etag, etag);
	}

	
	public Vector<byte[]> getETag() {
		if (options.getOption(CoapHeaderOptionType.Etag) == null){
			return null;
		}
		
    	Vector<byte[]> etagList = new Vector<byte[]>();
		for (CoapHeaderOption option : options) {
			if (option.getOptionType() == CoapHeaderOptionType.Etag) {
				byte[] data = option.getOptionData();
				if (data.length >= 1 && data.length <= 8){
					etagList.add(option.getOptionData());
				}
			}
		}
		return etagList;
	}

	
	
	public boolean isRequest() {
		return true;
	}

	public boolean isResponse() {
		return false;
	}

	
	public boolean isEmpty() {
		return false;
	}
	
    @Override
	public String toString() {
    	return packetType.toString() + ", " + requestCode.toString() + ", MsgId: " + getMessageID() +", #Options: " + options.getOptionCount(); 
	}

    
	public void setRequestCode(CoapRequestCode requestCode) {
		this.requestCode = requestCode;
	}
}
