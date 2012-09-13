package com.mooneyserver.account.persistence.service.accounts;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.service.BaseServiceLayer;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BalanceSheetService extends BaseServiceLayer {
       
    public BalanceSheetService() {
        super();
    }


	public void create(BalanceSheet entity) {
		em.persist(entity);	
	}
	
	public void modify(BalanceSheet entity) {		
		em.merge(entity);
	}


	public List<BalanceSheet> findBalSheetByOwner(AccountsUser owner) {
		return em.createNamedQuery("accounts.schema.BalanceSheet.findByOwner", 
				BalanceSheet.class).setParameter("owner", owner).getResultList();
	}
	
	public BalanceSheet findBalSheetByName(AccountsUser owner, String sheetName) {		
		Query query = em.createNamedQuery("accounts.schema.BalanceSheet.findByName", 
				BalanceSheet.class).setParameter("owner", owner)
				.setParameter("name", sheetName);
		
		return getSingleResultOrNull(query);
	}
}