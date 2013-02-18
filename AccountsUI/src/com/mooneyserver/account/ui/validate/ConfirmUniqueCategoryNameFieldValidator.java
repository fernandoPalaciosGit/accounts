package com.mooneyserver.account.ui.validate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IPaymentTypeMgmt;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.ui.iface.IContainsCustomAnnotations;

import com.vaadin.data.Validator;

public class ConfirmUniqueCategoryNameFieldValidator implements Validator, IContainsCustomAnnotations {

	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_UI);
	
	@BusinessProcess
	private IPaymentTypeMgmt accSvc;
	private BalanceSheet sheet;
	
	public ConfirmUniqueCategoryNameFieldValidator(BalanceSheet sheet) {
		this.sheet = sheet;
		loadBackendServices();
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
            throw new InvalidValueException(AccountsApplication
            		.getResourceBundle().getString(AccountsMessages
            				.VALIDATE_DUPLICATE_CATG_NAME));
        }
	}

	@Override
	public boolean isValid(Object value) {
		try {
			List<CategoryType> alreadyTakenCategories 
				= accSvc.getCategoriesForSheet(sheet);
			
			for (CategoryType cat : alreadyTakenCategories) {
				if (cat.getName().equalsIgnoreCase(((String)value).trim()))
						return false;
			}
			
			return true;
		} catch (AccountsSheetException e) {
			log.log(Level.SEVERE, "Validation of Field Input failed due to service call", e);
			return false;
		}
	}

	@Override
	public void loadBackendServices() {
		BackendServiceLookup.injectBackendServices(this);
	}
}