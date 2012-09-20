package com.mooneyserver.account.businesslogic.accounts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.PaymentType;

@Remote
public interface IPaymentTypeMgmt {
	
	/**
	 * List of required fields for the AccountsUser object
	 */
	@SuppressWarnings("serial")
	final Set<String> requiredCategoryFields = new HashSet<String>() {
		{ 
			add("name"); 
			add("owningSheet"); 
		}};
	
	/**
	 * List of required fields for the AccountsUser object
	 */
	@SuppressWarnings("serial")
	final Set<String> requiredTypeFields = new HashSet<String>() {
		{ 
			add("name"); 
			add("category"); 
		}};
		
	public void addNewPaymentCategory(CategoryType category) throws AccountsSheetException;
	
	public void addNewPaymentCategory(String name, boolean credit, BalanceSheet owningSheet) throws AccountsSheetException;
	
	public void addNewPaymentType(PaymentType type) throws AccountsSheetException;
	
	public void addNewPaymentType(String name, CategoryType owningCategory) throws AccountsSheetException;
	
	public List<CategoryType> getCategoriesForSheet(BalanceSheet sheet) throws AccountsSheetException;
	
	public List<PaymentType> getTypesForCategory(CategoryType category) throws AccountsSheetException;
	
	public void addNewDebitEntry(BigDecimal value, BalanceSheet sheet, PaymentType type, 
			boolean isMonthly, Date inertionTime, String description) throws AccountsSheetException;
	
	public void addNewCreditEntry(BigDecimal value, BalanceSheet sheet, PaymentType type, 
			boolean isMonthly, Date inertionTime, String description) throws AccountsSheetException;
}