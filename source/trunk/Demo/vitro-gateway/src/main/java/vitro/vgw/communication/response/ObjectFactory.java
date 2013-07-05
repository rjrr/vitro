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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.11 at 10:57:28 AM CEST 
//


package vitro.vgw.communication.response;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the vitro.vgw.communication.response package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: vitro.vgw.communication.response
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VgwActivationResponseType }
     * 
     */
    public VgwActivationResponseType createVgwActivationResponseType() {
        return new VgwActivationResponseType();
    }

    /**
     * Create an instance of {@link NodeRegistrationResultType }
     * 
     */
    public NodeRegistrationResultType createNodeRegistrationResultType() {
        return new NodeRegistrationResultType();
    }

    /**
     * Create an instance of {@link VgwActivationResponse }
     * 
     */
    public VgwActivationResponse createVgwActivationResponse() {
        return new VgwActivationResponse();
    }

    /**
     * Create an instance of {@link VgwResponse }
     * 
     */
    public VgwResponse createVgwResponse() {
        return new VgwResponse();
    }

    /**
     * Create an instance of {@link VgwResponseType }
     * 
     */
    public VgwResponseType createVgwResponseType() {
        return new VgwResponseType();
    }

}
