/*******************************************************************************
 * Copyright (c) 2013 VITRO FP7 Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 *     Antoniou Thanasis
 *     Paolo Medagliani
 *     D. Davide Lamanna
 *     Panos Trakadas
 *     Andrea Kropp
 *     Kiriakos Georgouleas
 *     Panagiotis Karkazis
 *     David Ferrer Figueroa
 *     Francesco Ficarola
 *     Stefano Puglia
 ******************************************************************************/
package vitro.vspEngine.service.persistence;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Similar to SmartNode class. But used in persistence and includes a link to the DBRegisteredGateway
 */
@Entity
public class DBSmartNodeOfGateway {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id; //globally unique. as primary key

    @ManyToOne
    private DBRegisteredGateway parentGateWay;

    private String idWithinGateway;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DBRegisteredGateway getParentGateWay() {
        return parentGateWay;
    }

    public void setParentGateWay(DBRegisteredGateway parentGateWay) {
        this.parentGateWay = parentGateWay;
    }

    public String getIdWithinGateway() {
        return idWithinGateway;
    }

    public void setIdWithinGateway(String idWithinGateway) {
        this.idWithinGateway = idWithinGateway;
    }
}
