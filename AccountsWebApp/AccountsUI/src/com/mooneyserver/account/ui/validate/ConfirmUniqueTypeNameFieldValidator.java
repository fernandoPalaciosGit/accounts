package com.mooneyserver.account.ui.validate;

import java.util.LinkedHashMap;
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
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.PaymentType;
import com.mooneyserver.account.ui.iface.IContainsCustomAnnotations;

import com.vaadin.data.Validator;
import com.vaadin.ui.Select;

public class ConfirmUniqueTypeNameFieldValidator implements Validator, IContainsCustomAnnotations {

	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_UI);
	
	@BusinessProcess
	private IPaymentTypeMgmt accSvc;
	
	private Select catgSelect;
	private LinkedHashMap<String, CategoryType> categories;
	
	
	public ConfirmUniqueTypeNameFieldValidator(Select catgSelect, 
			LinkedHashMap<String, CategoryType> categories) {
		this.catgSelect = catgSelect;
		this.categories = categories;
		
		loadBackendServices();
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
            throw new InvalidValueException(AccountsApplication
            		.getResourceBundle().getString(AccountsMessages
            				.VALIDATE_DUPLICATE_TYPE_NAME));
        }
	}

	@Override
	public boolean isValid(Object value) {
		try {
			List<PaymentType> alreadyTakenCategories 
				= accSvc.getTypesForCategory(categories.get(catgSelect.getValue()));
			
			for (PaymentType paymt : alreadyTakenCategories) {
				if (paymt.getName().equalsIgnoreCase(((String)value).trim()))
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