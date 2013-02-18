package com.mooneyserver.account.persistence.service.accounts;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CreditEntry;
import com.mooneyserver.account.persistence.entity.DebitEntry;
import com.mooneyserver.account.persistence.service.BaseServiceLayer;


@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DebitCreditService extends BaseServiceLayer {
    
	@EJB
	private BalanceSheetService accSvc;
	
    public DebitCreditService() {
        super();
    }

	public void create(IDebitCredit entity) {
		em.persist(entity);	
	}
	
	public void modify(IDebitCredit entity) {		
		em.merge(entity);
	}
	
	public List<DebitEntry> getDebitEntriesForSheet(BalanceSheet sheet) {
		sheet = accSvc.findBalSheetByName(sheet.getOwner(), sheet.getSheetName());
		
		List<DebitEntry> entries = new ArrayList<>();
		entries.addAll(sheet.getDebitMasters());
		
		return entries;
	}
	
	public List<CreditEntry> getCreditEntriesForSheet(BalanceSheet sheet) {
		BalanceSheet dbSheet = accSvc.findBalSheetByName(sheet.getOwner(), sheet.getSheetName());
		
		List<CreditEntry> entries = new ArrayList<>();
		entries.addAll(dbSheet.getCreditMasters());
		
		return entries;
	}
}