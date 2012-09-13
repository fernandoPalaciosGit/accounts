package com.mooneyserver.account.businesslogic.accounts;

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
import com.mooneyserver.account.persistence.entity.PaymentType;
import com.mooneyserver.account.persistence.service.accounts.PaymentService;

@Stateless
@EJB(name = "PaymentTypeBusinessService", beanInterface = IPaymentTypeMgmt.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PaymentTypeBusinessService implements IPaymentTypeMgmt {

	@EJB
	private PaymentService paymentService;
	
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
	public void addNewPaymentCategory(String name, BalanceSheet owningSheet)
			throws AccountsSheetException {
		CategoryType category = new CategoryType();
		category.setName(name.trim());
		category.setOwningSheet(owningSheet);
		
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
}