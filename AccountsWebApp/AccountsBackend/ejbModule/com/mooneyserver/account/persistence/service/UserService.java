package com.mooneyserver.account.persistence.service;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mooneyserver.account.persistence.entity.AccountsUser;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserService extends BaseServiceLayer implements IServiceLayer<AccountsUser, Integer> {
       
    public UserService() {
        super();
    }
       

    public UserService(EntityManager em) {
        super(em);
    }


	@Override
	public void create(AccountsUser entity) {
		em.persist(entity);
		
	}


	@Override
	public void modify(AccountsUser entity) {
		em.merge(entity);
	}


	@Override
	public void delete(AccountsUser entity) {
		entity.setActive(false);
		modify(entity);
	}


	@Override
	public AccountsUser findById(Integer id) {
		Query query = em.createNamedQuery("accounts.schema.AccountsUser.findById", 
				AccountsUser.class).setParameter("uid", id);
		
		return getSingleResultOrNull(query);
	}


	@Override
	public List<AccountsUser> findAll() {
		return em.createNamedQuery("accounts.schema.AccountsUser.findAllActiveUsers", 
				AccountsUser.class).getResultList();
	}
	
	
	public AccountsUser findByUsername(String username) {
		Query query = em.createNamedQuery("accounts.schema.AccountsUser.findByUsername", 
				AccountsUser.class).setParameter("username", username);
		
		return getSingleResultOrNull(query);
	}
}