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
import vitro.vspEngine.service.persistence.DBSelectionOfSmartNodes;

import javax.persistence.EntityManager;
import java.util.List;

/**
 */
public class SelectionOfSmartNodesDAO {

    private static SelectionOfSmartNodesDAO instance = new SelectionOfSmartNodesDAO();

    private Logger logger = Logger.getLogger(SelectionOfSmartNodesDAO.class);

    private SelectionOfSmartNodesDAO(){
        super();
    }

    public static SelectionOfSmartNodesDAO getInstance(){
        return instance;
    }

    public List<DBSelectionOfSmartNodes> getSelectionOfSmartNodesList(EntityManager manager){
        logger.debug("getSelectionOfSmartNodesList() - Start");
        List<DBSelectionOfSmartNodes> result =  manager.createQuery("SELECT instance FROM DBSelectionOfSmartNodes instance", DBSelectionOfSmartNodes.class).getResultList();

        //Extract gateway data from db via JPA NOT COMMENT THIS LINES
        for (DBSelectionOfSmartNodes dbSelectionOfSmartNodesTmp : result) {
            dbSelectionOfSmartNodesTmp.getDBSmartNodeOfGatewayList().size();
        }

        return result;

    }

    public DBSelectionOfSmartNodes getSelectionOfSmartNodes(EntityManager manager, int selectionOfSmartNodesListId){
        logger.debug("getSelectionOfSmartNodes() - selectionOfSmartNodesListId = " + selectionOfSmartNodesListId);
        DBSelectionOfSmartNodes dbSelectionOfSmartNodesTmp = manager.find(DBSelectionOfSmartNodes.class, selectionOfSmartNodesListId);

        //Extract selection data JPA NOT COMMENT THIS LINES
        dbSelectionOfSmartNodesTmp.getDBSmartNodeOfGatewayList().size();

        return dbSelectionOfSmartNodesTmp;

    }

}
