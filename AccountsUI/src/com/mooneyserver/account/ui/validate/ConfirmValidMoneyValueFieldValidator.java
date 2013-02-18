package com.mooneyserver.account.ui.validate;

import java.math.BigDecimal;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.vaadin.data.Validator;

public class ConfirmValidMoneyValueFieldValidator implements Validator {

	private static final long serialVersionUID = 1L;
	
	public ConfirmValidMoneyValueFieldValidator() {}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
            throw new InvalidValueException(AccountsApplication
            		.getResourceBundle().getString(AccountsMessages
            				.VALIDATE_NOT_AN_AMMOUNT));
        }
	}

	@Override
	public boolean isValid(Object value) {
		try {
			new BigDecimal((String) value);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}