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
package ch.ethz.inf.vs.californium.examples.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ch.ethz.inf.vs.californium.coap.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.coap.MediaTypeRegistry;
import ch.ethz.inf.vs.californium.coap.OptionNumberRegistry;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.endpoint.LocalResource;

/**
 * Defines a resource that returns the current weather on a GET request.
 * 
 * @author Dominique Im Obersteg, Daniel Pauli, and Matthias Kovatsch
 */
public class ZurichWeatherResource extends LocalResource {
	
	public static final int WEATHER_MAX_AGE = 300000; // 5min

	// The current weather information represented as string
	private String weatherXML;
	private String weatherPLAIN;
	
	private List<Integer> supported = new ArrayList<Integer>();

	/*
	 * Constructor for a new ZurichWeatherResource
	 */
	public ZurichWeatherResource() {
		super("weatherResource");
		setTitle("GET the current weather in zurich");
		setResourceType("ZurichWeather");
		isObservable(true);
		
		supported.add(MediaTypeRegistry.TEXT_PLAIN);
		supported.add(MediaTypeRegistry.APPLICATION_XML);

		for (int ct : supported) {
			setContentTypeCode(ct);
		}
		
		getZurichWeather();
		
		// Set timer task scheduling
		Timer timer = new Timer();
		timer.schedule(new ZurichWeatherTask(), 0, WEATHER_MAX_AGE);
	}

	private class ZurichWeatherTask extends TimerTask {
		@Override
		public void run() {
			String weatherOLD = new String(weatherXML);
			
			getZurichWeather();
			
			if (!weatherOLD.equals(weatherXML)) {
				changed();
			}
		}
	}
	
	private void getZurichWeather() {
		URL url;
		String rawWeather = "";

		try {
			url = new URL("http://weather.yahooapis.com/forecastrss?w=12893366");
			
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	rawWeather += inputLine + "\n";
	        }
	        in.close();
			
		} catch (IOException e) {
			System.err.println("getZurichWeather IOException");
			e.printStackTrace();
		}
		
		weatherPLAIN = parseWeatherXML(rawWeather);
		weatherXML = rawWeather;

	}

	/*
	 * Parses a given string describing an XML document
	 */
	public String parseWeatherXML(String input) {
		String result = "";
		try {
			Document xmlDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(new InputSource(new StringReader(input)));
			result = traverseXMLTree(xmlDocument);
		} catch (SAXException e) {
			System.err.println("parseWeatherXML SAXException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("parseWeatherXML IOException");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.println("parseWeatherXML ParserConfigurationException");
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Traverses the XML structure and parses relevant data
	 * 
	 * @param node The root node of the document
	 * 
	 * @return A formatted string containing the parsed data
	 */
	private String traverseXMLTree(Node node) {

		// Use stringbuilder to build result string more efficiently
		StringBuilder weatherResult = new StringBuilder();

		// Get location information
		if (node.getNodeName().equals("yweather:location")) {
			weatherResult.append("------------------------");
			weatherResult.append("\n  Location Information\n");
			weatherResult.append("------------------------");
			weatherResult.append("\n");
			// Get all location information attributes and append the
			// information to the string
			for (int j = 0; j < node.getAttributes().getLength(); j++) {
				weatherResult.append("   ");
				weatherResult
						.append(node.getAttributes().item(j).getNodeName());
				weatherResult.append(": ");
				weatherResult.append(node.getAttributes().item(j)
						.getNodeValue());
				weatherResult.append("\n");
			}
			weatherResult.append("------------------------\n\n");

			// Get unit information
		} else if (node.getNodeName().equals("yweather:units")) {
			weatherResult.append("------------------------");
			weatherResult.append("\n   Unit Information\n");
			weatherResult.append("------------------------");
			weatherResult.append("\n");
			// Get all unit information attributes and append the information
			// to the string
			for (int j = 0; j < node.getAttributes().getLength(); j++) {
				weatherResult.append("   ");
				weatherResult
						.append(node.getAttributes().item(j).getNodeName());
				weatherResult.append(": ");
				weatherResult.append(node.getAttributes().item(j)
						.getNodeValue());
				weatherResult.append("\n");
			}
			weatherResult.append("------------------------\n\n");

			// Get weather forecast information
		} else if (node.getNodeName().equals("yweather:forecast")) {
			weatherResult.append("-----------------------------");
			weatherResult.append("\n Weather Forecast: ");
			weatherResult.append(node.getAttributes().item(1).getNodeValue());
			weatherResult.append("\n");
			weatherResult.append("-----------------------------");
			weatherResult.append("\n");
			// Get all forecast information attributes and append the
			// information to the string. Start at attribute nr. 2 because
			// attribute 0 is the date of the forecast (already read for
			// constructing the header of the string) and attribute 1 is
			// not of interest
			for (int j = 2; j < node.getAttributes().getLength(); j++) {
				weatherResult.append("   ");
				weatherResult
						.append(node.getAttributes().item(j).getNodeName());
				weatherResult.append(": ");
				weatherResult.append(node.getAttributes().item(j)
						.getNodeValue());
				weatherResult.append("\n");
			}
			weatherResult.append("-----------------------------\n\n");
		}

		// Further traverse tree if children are available
		if (node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				weatherResult.append(traverseXMLTree(children.item(i)));
			}
		}
		return weatherResult.toString();
	}

	@Override
	public void performGET(GETRequest request) {

		int ct = MediaTypeRegistry.TEXT_PLAIN;
		// content negotiation
		if ((ct = MediaTypeRegistry.contentNegotiation(ct,  supported, request.getOptions(OptionNumberRegistry.ACCEPT)))==MediaTypeRegistry.UNDEFINED) {
			request.respond(CodeRegistry.RESP_NOT_ACCEPTABLE, "Supports text/plain and application/xml");
			return;
		}
		
		Response response = new Response(CodeRegistry.RESP_CONTENT);
		response.setMaxAge(WEATHER_MAX_AGE/1000);
		
		// Get Weather, either in plain text or as xml, depending on how it
		// has been requested
		if (ct==MediaTypeRegistry.APPLICATION_XML) {
			response.setPayload(weatherXML, MediaTypeRegistry.APPLICATION_XML);
		} else {
			response.setPayload(weatherPLAIN, MediaTypeRegistry.TEXT_PLAIN);
		}
		
		request.respond(response);
	}
}
