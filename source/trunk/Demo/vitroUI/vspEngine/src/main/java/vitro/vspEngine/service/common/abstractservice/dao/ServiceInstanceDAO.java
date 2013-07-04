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
package vitro.vspEngine.service.common.abstractservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import vitro.vspEngine.service.common.abstractservice.model.ServiceInstance;

public class ServiceInstanceDAO {

private static ServiceInstanceDAO instance = new ServiceInstanceDAO();
	
	private Logger logger = Logger.getLogger(ServiceInstanceDAO.class);

	private ServiceInstanceDAO(){
		super();
	}

	public static ServiceInstanceDAO getInstance(){
		return instance;
	}
	
	public List<ServiceInstance> getInstanceList(EntityManager manager){
		logger.debug("getInstanceList() - Start");
		List<ServiceInstance> result =  manager.createQuery("SELECT instance FROM ServiceInstance instance", ServiceInstance.class).getResultList();
		
		//Extract gateway data from db via JPA NOT COMMENT THIS LINES
		for (ServiceInstance serviceInstance : result) {
			//serviceInstance.getGatewayList().size();
			serviceInstance.getObservedCapabilities().size();
			serviceInstance.getSearchTagList().size();
		}
		
		return result;
		
	}
	
	public ServiceInstance getServiceInstance(EntityManager manager, int instanceId){
		logger.debug("getServiceInstance() - instanceId = " + instanceId);
		ServiceInstance serviceInstance = manager.find(ServiceInstance.class, instanceId);
		
		//Extract gateway data from db via JPA NOT COMMENT THIS LINES
		//serviceInstance.getGatewayList().size();
		serviceInstance.getObservedCapabilities().size();
		serviceInstance.getSearchTagList().size();
		
		
		return serviceInstance;
		
	}
	
	
}
