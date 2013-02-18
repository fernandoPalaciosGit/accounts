package com.mooneyserver.account.persistence.service.user;

import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.UserActivity;
import com.mooneyserver.account.persistence.service.BaseServiceLayer;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserService extends BaseServiceLayer {
       
    public UserService() {
        super();
    }


	public void create(AccountsUser entity) {
		em.persist(entity);
		createUserActivityEntry(findByUsername(entity.getUsername()));
	}


	public void modify(AccountsUser entity) {
		em.merge(entity);
	}


	public void delete(AccountsUser entity) {
		entity.setActive(false);
		modify(entity);
	}


	public AccountsUser findById(Integer id) {
		Query query = em.createNamedQuery("accounts.schema.AccountsUser.findById", 
				AccountsUser.class).setParameter("uid", id);
		
		return getSingleResultOrNull(query);
	}


	public List<AccountsUser> findAll() {
		return em.createNamedQuery("accounts.schema.AccountsUser.findAllActiveUsers", 
				AccountsUser.class).getResultList();
	}
	
	
	public AccountsUser findByUsername(String username) {
		Query query = em.createNamedQuery("accounts.schema.AccountsUser.findByUsername", 
				AccountsUser.class).setParameter("username", username);
		
		return getSingleResultOrNull(query);
	}
	
	public void createUserActivityEntry(AccountsUser user) {
		UserActivity usrActivity = new UserActivity();
		usrActivity.setUser(user);
		usrActivity.setUserLoggedIn(false);
		
		em.persist(usrActivity);
	}
	
	public void markUserLoggedIn(AccountsUser user) {
		UserActivity usrActivity = getUserActivityByUser(user);
		usrActivity.setUserLoggedIn(true);
		usrActivity.setLastLogin(new Date());
		
		em.merge(usrActivity);
	}
	
	public void markUserLoggedOut(AccountsUser user) {
		UserActivity usrActivity = getUserActivityByUser(user);
		usrActivity.setUserLoggedIn(false);
		usrActivity.setLastLogout(new Date());
		
		em.merge(usrActivity);
	}
	
	private UserActivity getUserActivityByUser(AccountsUser user) {
		Query query = em.createNamedQuery("accounts.schema.UserActivity.findByUser", 
				UserActivity.class).setParameter("user", user);
		
		return getSingleResultOrNull(query);
	}
}