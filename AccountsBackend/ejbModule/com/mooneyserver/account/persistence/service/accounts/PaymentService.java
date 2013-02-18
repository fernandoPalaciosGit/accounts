package com.mooneyserver.account.persistence.service.accounts;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.PaymentType;
import com.mooneyserver.account.persistence.service.BaseServiceLayer;


@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PaymentService extends BaseServiceLayer {
       
    public PaymentService() {
        super();
    }


	public void create(IPaymentCategorization entity) {
		em.persist(entity);	
	}
	
	public void modify(IPaymentCategorization entity) {		
		em.merge(entity);
	}


	public List<CategoryType> listPaymentCategories(BalanceSheet balSheet) {
		return em.createNamedQuery("accounts.schema.CategoryType.findByBalSheet", 
				CategoryType.class).setParameter("balanceSheet", balSheet)
				.getResultList();
	}
	
	public List<PaymentType> listPaymentTypes(CategoryType category) {		
		return em.createNamedQuery("accounts.schema.PaymentType.findByategory", 
				PaymentType.class).setParameter("category", category)
				.getResultList();
	}
}