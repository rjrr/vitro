
package service.command.response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * For UNICA APIs access with NO Login
 * 
 * <p>Java class for SimpleOAuthHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimpleOAuthHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="xoauth_requestor_id" type="{http://www.telefonica.com/schemas/UNICA/SOAP/common/v1}UserIdType" minOccurs="0"/>
 *           &lt;element name="oauth_token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleOAuthHeaderType", propOrder = {
    "xoauthRequestorId",
    "oauthToken"
})
public class SimpleOAuthHeaderType {

    @XmlElementRef(name = "xoauth_requestor_id", namespace = "http://www.telefonica.com/schemas/UNICA/SOAP/common/v1", type = JAXBElement.class, required = false)
    protected JAXBElement<UserIdType> xoauthRequestorId;
    @XmlElement(name = "oauth_token")
    protected String oauthToken;

    /**
     * Gets the value of the xoauthRequestorId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UserIdType }{@code >}
     *     
     */
    public JAXBElement<UserIdType> getXoauthRequestorId() {
        return xoauthRequestorId;
    }

    /**
     * Sets the value of the xoauthRequestorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UserIdType }{@code >}
     *     
     */
    public void setXoauthRequestorId(JAXBElement<UserIdType> value) {
        this.xoauthRequestorId = value;
    }

    /**
     * Gets the value of the oauthToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOauthToken() {
        return oauthToken;
    }

    /**
     * Sets the value of the oauthToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOauthToken(String value) {
        this.oauthToken = value;
    }

}
