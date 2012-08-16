package com.mooneyserver.account.ui.view.subwindow.user;

import java.util.logging.Level;

import com.mooneyserver.account.businesslogic.exception.user.AccountsUserAlreadyExistsException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserInvalidPasswordException;
import com.mooneyserver.account.businesslogic.user.IUserService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.validate.ConfirmPasswordFieldValidator;
import com.mooneyserver.account.ui.view.AbstractBaseView;
import com.mooneyserver.account.ui.view.IconManager;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.mooneyserver.account.ui.widget.FieldWithHelp;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class CreateNewUserDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private static IUserService userSvc;
	
	private final Form createNewUserFrm;
	
	// Form Fields
	private final String FRM_FIRST_NAME = "firstName";
	private final String FRM_LAST_NAME = "lastName";
	private final String FRM_EMAIL = "email";
	private final String FRM_PASSWORD = "password";
	private final String FRM_CONFIRM_PASSWORD = "confirmPassword";
	
	
	public CreateNewUserDialog() {
		super(AccountsMessages.CREATE_NEW_USER);
		
		setWidth("380px");
		
		createNewUserFrm = new Form();
		
		TextField firstName = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.FIRST_NAME));
		firstName.setRequired(true);
		firstName.setTabIndex(1);
		
		TextField lastName = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.LAST_NAME));
		lastName.setRequired(true);
		lastName.setTabIndex(2);
		
		TextField email = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.EMAIL_ADDRESS));
		email.setRequired(true);
		email.addValidator(new EmailValidator(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.VALIDATE_EMAIL)));
		email.setTabIndex(3);
		
		PasswordField pwd1 = new PasswordField(AccountsApplication.getResourceBundle().
                getString(AccountsMessages.PASSWORD));
		pwd1.setRequired(true);
		pwd1.setTabIndex(4);
		FieldWithHelp<PasswordField> pwd1Field = new FieldWithHelp<>
			(pwd1, PasswordField.class, AccountsApplication.getResourceBundle().
					getString(AccountsMessages.PASSWORD_REQUIREMENTS));
		
		PasswordField pwd2 = new PasswordField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.CONFIRM_PASSWORD));
		pwd2.setRequired(true);
		pwd2.addValidator(new ConfirmPasswordFieldValidator(pwd1));
		pwd2.setTabIndex(5);
		
		createNewUserFrm.addField(FRM_FIRST_NAME, firstName);
		createNewUserFrm.addField(FRM_LAST_NAME, lastName);
		createNewUserFrm.addField(FRM_EMAIL, email);
		createNewUserFrm.addField(FRM_PASSWORD, pwd1Field);
		createNewUserFrm.addField(FRM_CONFIRM_PASSWORD, pwd2);
		
		addComponent(createNewUserFrm);
		
		Button create = new Button(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.CREATE_NEW_USER));
		create.addListener(this);
		
		addComponent(create);
		
		setIcon(IconManager.getIcon(IconManager.CREATE_USER_LARGE));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			createNewUserFrm.commit();
			if (createNewUserFrm.isValid()) {
				AccountsUser user = new AccountsUser();
				
				user.setFirstname(((String) createNewUserFrm
						.getField(FRM_FIRST_NAME).getValue()).trim());
				user.setLastname(((String) createNewUserFrm
						.getField(FRM_LAST_NAME).getValue()).trim());
				user.setUsername(((String) createNewUserFrm
						.getField(FRM_EMAIL).getValue()).trim());
				user.setPassword(((String) createNewUserFrm
						.getField(FRM_PASSWORD).getValue()).trim());
				
				userSvc.createNewUser(user); 
				
				close();
				
				// TODO: Localize
				Messenger.genericMessage(MessageSeverity.INFO, "Your user has been created,"
						+ " please check your email for activation details");
			}
		} catch (InvalidValueException e) {
			// Handled by UI framework
		} catch (AccountsUserException e) {
			AbstractBaseView.log.log(Level.WARNING, "Error trying to create user", e);
			
			if (e instanceof AccountsUserAlreadyExistsException) {
				Messenger.genericMessage(MessageSeverity.WARNING, AccountsApplication
						.getResourceBundle().getString(AccountsMessages.DUPLICATE_USERNAME));
			} else if (e instanceof AccountsUserInvalidPasswordException) {
				Messenger.genericMessage(MessageSeverity.WARNING, AccountsApplication
						.getResourceBundle().getString(AccountsMessages.INVALID_PASSWORD));
			}
		} catch (Exception e) {
			Messenger.genericMessage(MessageSeverity.ERROR, "Internal Error has occured."
					+ " Admins have been notified");
		}
	}
}