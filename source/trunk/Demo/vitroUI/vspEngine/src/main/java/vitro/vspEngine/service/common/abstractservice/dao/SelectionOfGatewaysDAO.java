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
package vitro.vspEngine.service.common.abstractservice.dao;

import org.apache.log4j.Logger;
import vitro.vspEngine.service.persistence.DBSelectionOfGateways;

import javax.persistence.EntityManager;
import java.util.List;

/**
 */
public class SelectionOfGatewaysDAO  {

    private static SelectionOfGatewaysDAO instance = new SelectionOfGatewaysDAO();

    private Logger logger = Logger.getLogger(SelectionOfGatewaysDAO.class);

    private SelectionOfGatewaysDAO(){
        super();
    }

    public static SelectionOfGatewaysDAO getInstance(){
        return instance;
    }

    public List<DBSelectionOfGateways> getSelectionOfGatewaysList(EntityManager manager){
        logger.debug("getSelectionOfGatewaysList() - Start");
        List<DBSelectionOfGateways> result =  manager.createQuery("SELECT instance FROM DBSelectionOfGateways instance", DBSelectionOfGateways.class).getResultList();

        //Extract gateway data from db via JPA NOT COMMENT THIS LINES
        for (DBSelectionOfGateways dbSelectionOfGatewaysTmp : result) {
            dbSelectionOfGatewaysTmp.getDBRegisteredGatewayList().size();
        }

        return result;

    }

    public DBSelectionOfGateways getSelectionOfGateways(EntityManager manager, int selectionOfGatewaysListId){
        logger.debug("getSelectionOfGateways() - selectionOfGatewaysListId = " + selectionOfGatewaysListId);
        DBSelectionOfGateways dbSelectionOfGatewaysTmp = manager.find(DBSelectionOfGateways.class, selectionOfGatewaysListId);

        //Extract selection data JPA NOT COMMENT THIS LINES
        dbSelectionOfGatewaysTmp.getDBRegisteredGatewayList().size();

        return dbSelectionOfGatewaysTmp;

    }
}
