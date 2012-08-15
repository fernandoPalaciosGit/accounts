package com.mooneyserver.account.persistence.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BaseServiceLayer {
	
	@PersistenceContext(unitName="AccountsPersistenceLayer")
    protected EntityManager em;
	
	protected BaseServiceLayer() {}
	
	protected BaseServiceLayer(EntityManager em) {
		this.em = em;
	}
	
	// Avoid Exceptions when searching for single results
	protected <E> E getSingleResultOrNull(Query query){      
		@SuppressWarnings("unchecked")
		List<E> results = query.getResultList();
		
		if (results.size() == 1) 
			return(results.get(0));
		else
			return null;
	}
}