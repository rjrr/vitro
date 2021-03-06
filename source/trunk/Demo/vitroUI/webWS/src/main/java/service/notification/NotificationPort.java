
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

package service.notification;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NotificationPort", targetNamespace = "http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/notification/v1/services")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NotificationPort {


    /**
     * 
     * @param subscriptionLogicalName
     * @param eventKind
     * @param xmlRegister
     * @return
     *     returns int
     * @throws ClientException
     * @throws ServerException
     */
    @WebMethod(action = "urn:notify")
    @WebResult(name = "result", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types")
    @RequestWrapper(localName = "notify", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types", className = "service.notification.Notify")
    @ResponseWrapper(localName = "notifyResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types", className = "service.notification.NotifyResponse")
    public int notify(
        @WebParam(name = "subscriptionLogicalName", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types")
        String subscriptionLogicalName,
        @WebParam(name = "eventKind", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types")
        EventKindType eventKind,
        @WebParam(name = "xmlRegister", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types")
        String xmlRegister)
        throws ClientException, ServerException
    ;

}
