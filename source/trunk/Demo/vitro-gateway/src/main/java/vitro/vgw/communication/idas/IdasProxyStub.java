/*
 * #--------------------------------------------------------------------------
 * # Copyright (c) 2013 VITRO FP7 Consortium.
 * # All rights reserved. This program and the accompanying materials
 * # are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * # http://www.gnu.org/licenses/lgpl-3.0.html
 * #
 * # Contributors:
 * #     Antoniou Thanasis (Research Academic Computer Technology Institute)
 * #     Paolo Medagliani (Thales Communications & Security)
 * #     D. Davide Lamanna (WLAB SRL)
 * #     Alessandro Leoni (WLAB SRL)
 * #     Francesco Ficarola (WLAB SRL)
 * #     Stefano Puglia (WLAB SRL)
 * #     Panos Trakadas (Technological Educational Institute of Chalkida)
 * #     Panagiotis Karkazis (Technological Educational Institute of Chalkida)
 * #     Andrea Kropp (Selex ES)
 * #     Kiriakos Georgouleas (Hellenic Aerospace Industry)
 * #     David Ferrer Figueroa (Telefonica Investigación y Desarrollo S.A.)
 * #
 * #--------------------------------------------------------------------------
 */
package vitro.vgw.communication.idas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.opengis.sos.v_1_0_0.InsertObservation;
import net.opengis.sos.v_1_0_0.InsertObservationResponse;
import net.opengis.sos.v_1_0_0.RegisterSensor;
import net.opengis.sos.v_1_0_0.RegisterSensorResponse;
import vitro.vgw.exception.VitroGatewayException;
import vitro.vgw.utils.Utils;

public class IdasProxyStub implements IdasProxy {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public InsertObservationResponse insertObservation(InsertObservation request) throws VitroGatewayException {
		logger.debug("sending the following message:");
		Utils.showElement(request);
		
		InsertObservationResponse response = new InsertObservationResponse();
		response.setAssignedObservationId(String.valueOf(System.currentTimeMillis()));
		
		return response;
	}

	public RegisterSensorResponse registerSensor(RegisterSensor request) throws VitroGatewayException {
		logger.debug("sending the following message:");
		Utils.showElement(request);
		
		RegisterSensorResponse response = new RegisterSensorResponse();
		response.setAssignedSensorId(String.valueOf(System.currentTimeMillis()));
		return response;
	}

}
