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

import vitro.vspEngine.service.common.abstractservice.model.ServiceInstance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Defines a set of DBRegisteredGateways participating in a serviceInstance
 */
@Entity
public class DBSelectionOfGateways {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    // todo: was: (cascade = { CascadeType.MERGE}) but removed because it caused errors (fire early warning at a VGW( TCS))
    @ManyToMany
    private List<DBRegisteredGateway> DBRegisteredGatewayList;

    public DBSelectionOfGateways(){
        setDBRegisteredGatewayList(new ArrayList<DBRegisteredGateway>());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DBRegisteredGateway> getDBRegisteredGatewayList() {
        return DBRegisteredGatewayList;
    }

    public void setDBRegisteredGatewayList(List<DBRegisteredGateway> DBRegisteredGatewayList) {
        this.DBRegisteredGatewayList = DBRegisteredGatewayList;
    }
}
