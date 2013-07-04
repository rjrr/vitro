
package service.command.response;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ServerException", targetNamespace = "http://www.telefonica.com/wsdl/UNICA/SOAP/common/v1/faults")
public class ServerException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ServerExceptionType faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ServerException(String message, ServerExceptionType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ServerException(String message, ServerExceptionType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: service.command.response.ServerExceptionType
     */
    public ServerExceptionType getFaultInfo() {
        return faultInfo;
    }

}
