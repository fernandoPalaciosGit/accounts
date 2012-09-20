package com.mooneyserver.account.businesslogic.accounts;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.service.accounts.IDebitCredit;

@Remote
public interface IBalanceSheetMgmt {
	
	/**
	 * List of required fields for the AccountsUser object
	 */
	@SuppressWarnings("serial")
	final Set<String> requiredUserFields = new HashSet<String>() {
		{ 
			add("sheetName"); 
			add("owner"); 
		}};
		
	public void createNewBalanceSheet(BalanceSheet entity) throws AccountsSheetException;
	
	public List<BalanceSheet> getMyBalanceSheets(AccountsUser user) throws AccountsSheetException;
	
	public void closeBalanceSheet(AccountsUser user, String sheetName, String reason) throws AccountsSheetException;
	
	public void reopenBalanceSheet(AccountsUser user, String sheetName) throws AccountsSheetException;
	
	public List<IDebitCredit> getEntriesWithinRange(BalanceSheet sheet, Calendar from, Calendar to) throws AccountsSheetException;
}