package com.mooneyserver.account.ui.validate;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.vaadin.data.Validator;
import com.vaadin.ui.PasswordField;

public class ConfirmPasswordFieldValidator implements Validator {

	private static final long serialVersionUID = 1L;

	private PasswordField field;
	
	public ConfirmPasswordFieldValidator(PasswordField field) {
		this.field = field;
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
            throw new InvalidValueException(AccountsApplication
            		.getResourceBundle().getString(AccountsMessages
            				.VALIDATE_CONFIRM_PASSWORD));
        }
	}

	@Override
	public boolean isValid(Object value) {
		return value.equals(field.getValue());
	}
}