package com.mooneyserver.account.ui.view.subwindow.user;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class CreateNewUserDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	private final Form createNewUserFrm;
	
	public CreateNewUserDialog() {
		super(AccountsMessages.CREATE_NEW_USER);
		
		setWidth("380px");
		
		createNewUserFrm = new Form();
		
		TextField firstName = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.FIRST_NAME));
		TextField lastName = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.LAST_NAME));
		
		TextField email = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.EMAIL_ADDRESS));
		email.setRequired(true);
		email.addValidator(new EmailValidator(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.VALIDATE_EMAIL)));
		
		PasswordField pwd1 = new PasswordField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.PASSWORD));
		pwd1.setRequired(true);
		PasswordField pwd2 = new PasswordField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.CONFIRM_PASSWORD));
		pwd2.setRequired(true);
		
		createNewUserFrm.addField("a", firstName);
		createNewUserFrm.addField("b", lastName);
		createNewUserFrm.addField("c", email);
		createNewUserFrm.addField("d", pwd1);
		createNewUserFrm.addField("e", pwd2);
		
		addComponent(createNewUserFrm);
		
		Button create = new Button(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.CREATE_NEW_USER));
		create.addListener(this);
		
		addComponent(create);
		
		setIcon(new ThemeResource("img/user_add_large_.png"));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			createNewUserFrm.commit();
			if (createNewUserFrm.isValid()) {
				// TODO: Implement create user call
				close();
			}
		} catch (InvalidValueException e) {
			// Ignoring InvalidValueException as this is displayed in UI
			// TODO: Maybe add some logging
		}
	}
}