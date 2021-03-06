
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

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "M2MNotificationService", targetNamespace = "http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/notification/v1/services", wsdlLocation = "file:/C:/workspace/allProjects/VitroMiddlewareNew/vitroUI/webWS/src/main/resources/UNICA_API_SOAP_m2m_notification_services_v1_1.wsdl")
public class M2MNotificationService
    extends Service
{

    private final static URL M2MNOTIFICATIONSERVICE_WSDL_LOCATION;
    private final static WebServiceException M2MNOTIFICATIONSERVICE_EXCEPTION;
    private final static QName M2MNOTIFICATIONSERVICE_QNAME = new QName("http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/notification/v1/services", "M2MNotificationService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/workspace/allProjects/VitroMiddlewareNew/vitroUI/webWS/src/main/resources/UNICA_API_SOAP_m2m_notification_services_v1_1.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        M2MNOTIFICATIONSERVICE_WSDL_LOCATION = url;
        M2MNOTIFICATIONSERVICE_EXCEPTION = e;
    }

    public M2MNotificationService() {
        super(__getWsdlLocation(), M2MNOTIFICATIONSERVICE_QNAME);
    }

    public M2MNotificationService(WebServiceFeature... features) {
        super(__getWsdlLocation(), M2MNOTIFICATIONSERVICE_QNAME, features);
    }

    public M2MNotificationService(URL wsdlLocation) {
        super(wsdlLocation, M2MNOTIFICATIONSERVICE_QNAME);
    }

    public M2MNotificationService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, M2MNOTIFICATIONSERVICE_QNAME, features);
    }

    public M2MNotificationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public M2MNotificationService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns NotificationPort
     */
    @WebEndpoint(name = "Notification")
    public NotificationPort getNotification() {
        return super.getPort(new QName("http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/notification/v1/services", "Notification"), NotificationPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NotificationPort
     */
    @WebEndpoint(name = "Notification")
    public NotificationPort getNotification(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/notification/v1/services", "Notification"), NotificationPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (M2MNOTIFICATIONSERVICE_EXCEPTION!= null) {
            throw M2MNOTIFICATIONSERVICE_EXCEPTION;
        }
        return M2MNOTIFICATIONSERVICE_WSDL_LOCATION;
    }

}
