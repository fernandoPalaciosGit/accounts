package com.mooneyserver.account.persistence.service.accounts;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.persistence.service.BaseServiceLayer;


@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DebitCreditService extends BaseServiceLayer {
       
    public DebitCreditService() {
        super();
    }


	public void create(IDebitCredit entity) {
		em.persist(entity);	
	}
	
	public void modify(IDebitCredit entity) {		
		em.merge(entity);
	}
}