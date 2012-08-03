package com.mooneyserver.account.ui.view.subwindow.user;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.view.IconManager;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ResetPasswordDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	private final Form resetPwdForm;
	
	
	/**
	 * Dialog to reset the password in the DB
	 * if the email matches a current user. 
	 * Mail new password to user and force
	 * them to reset it first time
	 */
	public ResetPasswordDialog() {
		super(AccountsMessages.FORGOT_PWD);
		
		setWidth("250px");
		
		resetPwdForm = new Form(new VerticalLayout());
		
		TextField email = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.EMAIL_ADDRESS));
		email.addValidator(new EmailValidator(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.VALIDATE_EMAIL)));
		resetPwdForm.addField("a", email);
		
		Button sendNewPassword = new Button(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.SEND_NEW_PASSWORD));
		sendNewPassword.addListener(this);
		
		addComponent(resetPwdForm);
		addComponent(sendNewPassword);
		
		setIcon(IconManager.getIcon(IconManager.FORGOT_PASSWORD_LARGE));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			resetPwdForm.commit();
			if (resetPwdForm.isValid()) {
				// TODO: Implement password reset
				close();
			}
		} catch (InvalidValueException e) {
			// Ignoring InvalidValueException as this is displayed in UI
			// TODO: Maybe add some logging
		}
	}
}