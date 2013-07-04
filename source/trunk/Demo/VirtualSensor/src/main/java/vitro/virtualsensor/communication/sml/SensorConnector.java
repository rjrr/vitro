/*
 * #--------------------------------------------------------------------------
 * # Copyright (c) 2013 VITRO FP7 Consortium.
 * # All rights reserved. This program and the accompanying materials
 * # are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * # http://www.gnu.org/licenses/lgpl-3.0.html
 * #
 * # Contributors:
 * #     Antoniou Thanasis
 * #     Paolo Medagliani
 * #     D. Davide Lamanna
 * #     Panos Trakadas
 * #     Andrea Kropp
 * #     Kiriakos Georgouleas
 * #     Panagiotis Karkazis
 * #     David Ferrer Figueroa
 * #     Francesco Ficarola
 * #     Stefano Puglia
 * #--------------------------------------------------------------------------
 */

package vitro.virtualsensor.communication.sml;

import vitro.virtualsensor.VirtualSensor;
import vitro.virtualsensor.communication.HttpForwarder;

/**
 * This class deals with the connection between the Virtual Sensors and DCA.
 * It is just a cover of an http connection
 * @author David
 *
 */

public class SensorConnector {

	//TODO establish the connection parameters
	private HttpForwarder http;
	private final String endpoint;
	

	public SensorConnector(){
		http = new HttpForwarder();
		endpoint = "";
	}
	public boolean sendRegistrationMessage(VirtualSensor vs) {
		String xml = getRegistrationMessage(vs);

		return sendMessage(xml);
	}

	public String getRegistrationMessage(VirtualSensor vs) {
		return RegisterSensorMessage.getRegisterSensorMessage(vs);
	}

	public boolean sendObservationMessage(VirtualSensor vs) {
		String xml = getInsertObservationMessage(vs);

		return sendMessage(xml);
	}

	public String getInsertObservationMessage(VirtualSensor vs) {
		return InsertObservationMessage.getInsertObservationMessage(vs);
	}

	public boolean sendMessage(String xml) {
		http.post(endpoint, xml);
		if (http.getResponseCode() == 200) {
            System.out.println("Received code: 200");
            System.out.println("Response: " + http.getResponse());
        } else {
        	System.out.println("Received code " + http.getResponseCode());
        	System.out.println("Response: " + http.getErrorResponse());
        }
		return true;
	}
}