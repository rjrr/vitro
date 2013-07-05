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
package vitro.vgw.client;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.jaxrs.client.WebClient;

import vitro.vgw.communication.response.VgwActivationResponse;
import vitro.vgw.communication.response.VgwResponse;

/**
 * User: alessandro
 * Date: 14/05/12
 * Time: 11.56
 */
public class TestClientInvokeWSIService {
    
    //private static final String endpointUrl = "http://vgw.vitro.w-lab.it/vgw-1.0-SNAPSHOT";
	private static final String endpointUrl = "http://localhost:8080/vgw-1.0-SNAPSHOT";
    
	public static void main(String[] args) throws Exception {
		testInvokeWSIService();
	}
	
	
	private static void testVGW(String testFile, String endPoint) throws Exception {
		BufferedReader br = null;

		StringBuffer sb = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(TestClientInvokeWSIService.class.getResourceAsStream(testFile)));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}

		WebClient client = WebClient.create(endpointUrl + endPoint);
		Response r = client.accept("application/xml").type("application/xml")
				.post(sb.toString());

		if (r.getStatus() == (Response.Status.OK.getStatusCode())) {
			JAXBContext jc = JAXBContext.newInstance("vitro.vgw.communication.response");

			Unmarshaller unmarshaller = jc.createUnmarshaller();

			Object response = unmarshaller.unmarshal((InputStream) r.getEntity());

			if (response != null) {
				
				boolean success = true;
				
				if(response instanceof VgwResponse){
					success = ((VgwResponse)response).isSuccess();
				} else if(response instanceof VgwActivationResponse){
					success = ((VgwActivationResponse)response).isSuccess();
				}
				
				System.out.println("response = " + success);
			}
		} else {
			System.out.println("error = " + r.getStatus() + "; "
					+ r.getEntity());
		}

	}
	
	private static void testInvokeWSIService() throws Exception {
		testVGW("/tests/test-request-invoke-wsi-service.xml", "/vgw/invokeWSIService");
	}
	
	private static void testInvokeActivateSensor() throws Exception {
		testVGW("/tests/test-request-activate-sensor.xml", "/vgw/activateSensor");
	}
    
}
