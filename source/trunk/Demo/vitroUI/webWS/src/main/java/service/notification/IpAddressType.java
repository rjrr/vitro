
package service.notification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * A generic IP address, including both v4 and v6
 * 				address formats
 * 
 * <p>Java class for IpAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IpAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="ipv4" type="{http://www.telefonica.com/schemas/UNICA/SOAP/common/v1}Ipv4AddressType"/>
 *         &lt;element name="ipv6" type="{http://www.telefonica.com/schemas/UNICA/SOAP/common/v1}Ipv6AddressType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IpAddressType", propOrder = {
    "ipv4",
    "ipv6"
})
public class IpAddressType {

    protected String ipv4;
    protected String ipv6;

    /**
     * Gets the value of the ipv4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpv4() {
        return ipv4;
    }

    /**
     * Sets the value of the ipv4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpv4(String value) {
        this.ipv4 = value;
    }

    /**
     * Gets the value of the ipv6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpv6() {
        return ipv6;
    }

    /**
     * Sets the value of the ipv6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpv6(String value) {
        this.ipv6 = value;
    }

}
