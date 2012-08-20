package com.mooneyserver.account.persistence.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.UserActivation;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserActivationService extends BaseServiceLayer {
       
    public UserActivationService() {
        super();
    }


	public void create(UserActivation entity) {
		em.persist(entity);	
	}


	public AccountsUser findUserByActivationId(String activationId) {
		Query query = em.createNamedQuery("accounts.schema.UserActivation.findUserByActivationId", 
				AccountsUser.class).setParameter("activationId", activationId);
		
		return getSingleResultOrNull(query);
	}
}