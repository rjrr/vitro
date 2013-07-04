
package service.subscription.response;

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
@WebService(name = "M2MSubscriptionResponseService", targetNamespace = "http://www.telefonica.com/wsdl/UNICA/SOAP/m2m/subscriptionresponse/v1/services")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SubscriptionResponsePort {

//    @WebMethod
//    public String sayHelloWorldFrom(String from);

    /**
     * 
     * @param errorText
     * @param subscriptionLogicalName
     * @param errorCode
     * @param outgoingConnectionId
     * @return
     *     returns int
     * @throws ClientException
     * @throws ServerException
     */
    @WebMethod(action = "urn:subscribeResponse")
    @WebResult(name = "result", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
    @RequestWrapper(localName = "subscribeResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.SubscribeResponse")
    @ResponseWrapper(localName = "subscribeResponseResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.SubscribeResponseResponse")
    public int subscribeResponse(
        @WebParam(name = "subscriptionLogicalName", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        String subscriptionLogicalName,
        @WebParam(name = "outgoingConnectionId", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        int outgoingConnectionId,
        @WebParam(name = "errorCode", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        int errorCode,
        @WebParam(name = "errorText", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        String errorText)
        throws ClientException, ServerException
    ;

    /**
     * 
     * @param errorText
     * @param subscriptionLogicalName
     * @param errorCode
     * @return
     *     returns int
     * @throws ClientException
     * @throws ServerException
     */
    @WebMethod(action = "urn:unsubscribeResponse")
    @WebResult(name = "result", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
    @RequestWrapper(localName = "unsubscribeResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.UnsubscribeResponse")
    @ResponseWrapper(localName = "unsubscribeResponseResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.UnsubscribeResponseResponse")
    public int unsubscribeResponse(
        @WebParam(name = "subscriptionLogicalName", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        String subscriptionLogicalName,
        @WebParam(name = "errorCode", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        int errorCode,
        @WebParam(name = "errorText", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        String errorText)
        throws ClientException, ServerException
    ;

    /**
     * 
     * @param errorText
     * @param errorCode
     * @return
     *     returns int
     * @throws ClientException
     * @throws ServerException
     */
    @WebMethod(action = "urn:disconnectResponse")
    @WebResult(name = "result", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
    @RequestWrapper(localName = "disconnectResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.DisconnectResponse")
    @ResponseWrapper(localName = "disconnectResponseResponse", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types", className = "service.subscription.response.DisconnectResponseResponse")
    public int disconnectResponse(
        @WebParam(name = "errorCode", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        int errorCode,
        @WebParam(name = "errorText", targetNamespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/subscriptionresponse/v1/types")
        String errorText)
        throws ClientException, ServerException
    ;

}