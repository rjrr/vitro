
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subscriptionLogicalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="eventKind" type="{http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types}EventKindType"/>
 *         &lt;element name="xmlRegister" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subscriptionLogicalName",
    "eventKind",
    "xmlRegister"
})
@XmlRootElement(name = "notify", namespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types")
public class Notify {

    @XmlElement(namespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types", required = true)
    protected String subscriptionLogicalName;
    @XmlElement(namespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types", required = true)
    protected EventKindType eventKind;
    @XmlElement(namespace = "http://www.telefonica.com/schemas/UNICA/SOAP/m2m/notification/v1/types", required = true)
    protected String xmlRegister;

    /**
     * Gets the value of the subscriptionLogicalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionLogicalName() {
        return subscriptionLogicalName;
    }

    /**
     * Sets the value of the subscriptionLogicalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionLogicalName(String value) {
        this.subscriptionLogicalName = value;
    }

    /**
     * Gets the value of the eventKind property.
     * 
     * @return
     *     possible object is
     *     {@link EventKindType }
     *     
     */
    public EventKindType getEventKind() {
        return eventKind;
    }

    /**
     * Sets the value of the eventKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventKindType }
     *     
     */
    public void setEventKind(EventKindType value) {
        this.eventKind = value;
    }

    /**
     * Gets the value of the xmlRegister property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlRegister() {
        return xmlRegister;
    }

    /**
     * Sets the value of the xmlRegister property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlRegister(String value) {
        this.xmlRegister = value;
    }

}
