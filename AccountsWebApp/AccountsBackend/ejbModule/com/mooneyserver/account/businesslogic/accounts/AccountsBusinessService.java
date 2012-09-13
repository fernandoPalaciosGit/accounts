package com.mooneyserver.account.businesslogic.accounts;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetInvalidOwnerException;
import com.mooneyserver.account.businesslogic.validate.ClassFieldValidator;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.service.accounts.BalanceSheetService;
import com.mooneyserver.account.persistence.service.user.UserService;

/**
 * Session Bean implementation class AccountsBusinessService
 */
@Stateless
@EJB(name = "AccountsBusinessService", beanInterface = IBalanceSheetMgmt.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccountsBusinessService implements IBalanceSheetMgmt {

	@EJB
	private UserService userService;
	
	@EJB
	private BalanceSheetService accSvc;
	
	@Resource
	private SessionContext context;

	@Override
	public void createNewBalanceSheet(BalanceSheet entity)
			throws AccountsSheetException {
		// Check if requested owner is valid
		AccountsUser owner = entity.getOwner(); 
		if (owner == null)
			throw new AccountsSheetInvalidOwnerException(owner);
		
		// Are all required fields present?
		ClassFieldValidator cfv = new ClassFieldValidator(entity, IBalanceSheetMgmt.requiredUserFields);
		try {
			if (cfv.areAnyFieldsNull())
				throw new AccountsSheetException("Creation Failed: Some User Fields are null");
		} catch (AccountsBaseException e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
		
		try {
			accSvc.create(entity);
		} catch(Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public List<BalanceSheet> getMyBalanceSheets(AccountsUser owner)
			throws AccountsSheetException {
		owner = userService.findById(owner.getId()); 
		if (owner == null)
			throw new AccountsSheetInvalidOwnerException(owner);
		
		List<BalanceSheet> sheets;
		
		try {
			sheets = accSvc.findBalSheetByOwner(owner);
			return sheets;
		} catch(Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public void closeBalanceSheet(AccountsUser owner, String sheetName, String reason)
			throws AccountsSheetException {
		owner = userService.findById(owner.getId()); 
		if (owner == null)
			throw new AccountsSheetInvalidOwnerException(owner);
		
		try {
			BalanceSheet sheet= accSvc.findBalSheetByName(owner, sheetName);
			if (sheet != null) {
				sheet.setActive(false);
				sheet.setReasonForClosure(reason);
				accSvc.modify(sheet);
			}
		} catch(Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public void reopenBalanceSheet(AccountsUser owner, String sheetName)
			throws AccountsSheetException {
		owner = userService.findById(owner.getId()); 
		if (owner == null)
			throw new AccountsSheetInvalidOwnerException(owner);
		
		try {
			BalanceSheet sheet= accSvc.findBalSheetByName(owner, sheetName);
			if (sheet != null) {
				sheet.setActive(true);
				sheet.setReasonForClosure("");
				accSvc.modify(sheet);
			}
		} catch(Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}
}