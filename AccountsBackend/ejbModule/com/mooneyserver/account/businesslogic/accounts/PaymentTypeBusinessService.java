package com.mooneyserver.account.businesslogic.accounts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.businesslogic.validate.ClassFieldValidator;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.CreditEntry;
import com.mooneyserver.account.persistence.entity.DebitEntry;
import com.mooneyserver.account.persistence.entity.PaymentType;
import com.mooneyserver.account.persistence.service.accounts.DebitCreditService;
import com.mooneyserver.account.persistence.service.accounts.IDebitCredit;
import com.mooneyserver.account.persistence.service.accounts.PaymentService;

@Stateless
@EJB(name = "PaymentTypeBusinessService", beanInterface = IPaymentTypeMgmt.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PaymentTypeBusinessService implements IPaymentTypeMgmt {

	@EJB
	private PaymentService paymentService;
	
	@EJB
	private DebitCreditService debCredService;
	
	@Override
	public void addNewPaymentCategory(CategoryType entity)
			throws AccountsSheetException {
		// Are all required fields present?
		ClassFieldValidator cfv = new ClassFieldValidator(entity, IPaymentTypeMgmt.requiredCategoryFields);
		try {
			if (cfv.areAnyFieldsNull())
				throw new AccountsSheetException("Creation Failed: Some Category Fields are null");
		} catch (AccountsBaseException e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
		
		try {
			paymentService.create(entity);		
		} catch (Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public void addNewPaymentCategory(String name, boolean credit, BalanceSheet owningSheet)
			throws AccountsSheetException {
		CategoryType category = new CategoryType();
		category.setName(name.trim());
		category.setOwningSheet(owningSheet);
		category.setCredit(credit);
		
		try {
			paymentService.create(category);		
		} catch (Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public void addNewPaymentType(PaymentType entity)
			throws AccountsSheetException {
		// Are all required fields present?
		ClassFieldValidator cfv = new ClassFieldValidator(entity, IPaymentTypeMgmt.requiredTypeFields);
		try {
			if (cfv.areAnyFieldsNull())
				throw new AccountsSheetException("Creation Failed: Some Type Fields are null");
		} catch (AccountsBaseException e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
		
		try {
			paymentService.create(entity);		
		} catch (Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public void addNewPaymentType(String name, CategoryType owningCategory)
			throws AccountsSheetException {
		PaymentType payment = new PaymentType();
		payment.setName(name.trim());
		payment.setCategory(owningCategory);
		
		try {
			paymentService.create(payment);		
		} catch (Exception e) {
			throw new AccountsSheetException("Rethrowing wrapped base exception", e);
		}
	}

	@Override
	public List<CategoryType> getCategoriesForSheet(BalanceSheet sheet)
			throws AccountsSheetException {
		return paymentService.listPaymentCategories(sheet);
	}

	@Override
	public List<PaymentType> getTypesForCategory(CategoryType category)
			throws AccountsSheetException {
		return paymentService.listPaymentTypes(category);
	}

	@Override
	public void addNewDebitEntry(BigDecimal value, BalanceSheet sheet,
			PaymentType type, boolean isMonthly, Date insertionTime,
			String description) throws AccountsSheetException {
		
		// CHeck if this is a debit or credit
		IDebitCredit entry;
		if (value.compareTo(BigDecimal.ZERO) > 0) {
			entry = new DebitEntry();
		} else {
			entry = new CreditEntry();
		}
		
		entry.setPaymentAmmount(value.abs());
		entry.setBalanceSheet(sheet);
		entry.setPaymentType(type);
		entry.setMonthly(isMonthly);
		entry.setInsertTime(insertionTime);
		
		if (description != null)
			entry.setDescription(description);
		
		debCredService.create(entry);
	}

	@Override
	public void addNewCreditEntry(BigDecimal value, BalanceSheet sheet,
			PaymentType type, boolean isMonthly, Date inertionTime,
			String description) throws AccountsSheetException {
		addNewDebitEntry(value.negate(), sheet, type, isMonthly, inertionTime, description);
	}	
}