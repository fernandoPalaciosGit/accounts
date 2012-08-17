package com.mooneyserver.account.ui.view.subwindow.user;

import com.mooneyserver.account.businesslogic.exception.user.AccountsUserDoesNotExistException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserException;
import com.mooneyserver.account.businesslogic.user.IUserService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
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
	
	@BusinessProcess
	private static IUserService userSvc;
	
	private final Form resetPwdForm;
	
	private String FIELD_EMAIL = "emailAddress";
	
	
	/**
	 * Dialog to reset the password in the DB
	 * if the email matches a current user. 
	 * Mail new password to user and force
	 * them to reset it first time
	 */
	public ResetPasswordDialog() {
		super(AccountsMessages.FORGOT_PWD);
		
		setWidth("250px");
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		
		resetPwdForm = new Form(new VerticalLayout());
		
		TextField email = new TextField(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.EMAIL_ADDRESS));
		email.addValidator(new EmailValidator(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.VALIDATE_EMAIL)));
		resetPwdForm.addField(FIELD_EMAIL, email);
		
		Button sendNewPassword = new Button(AccountsApplication.getResourceBundle().
				getString(AccountsMessages.SEND_NEW_PASSWORD));
		sendNewPassword.addListener(this);
		
		vl.addComponent(resetPwdForm);
		vl.addComponent(sendNewPassword);
		
		addComponent(vl);
		
		setIcon(IconManager.getIcon(IconManager.FORGOT_PASSWORD_LARGE));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			resetPwdForm.commit();
			if (resetPwdForm.isValid()) {
				userSvc.passwordReset(((String) resetPwdForm.getField(FIELD_EMAIL).getValue()).trim());
				close();
			}
		} catch (InvalidValueException e) {
			// Ignoring InvalidValueException as 
			// an appropriate msg is displayed in UI
		} catch (AccountsUserException e) {
			
			if (e instanceof AccountsUserDoesNotExistException) {
				// TODO: Localise
				Messenger.genericMessage(MessageSeverity.WARNING, "The requested user does not exist");
			}
		} catch (Exception e) {
			// TODO: Localize
			Messenger.genericMessage(MessageSeverity.ERROR, "Internal Error has occured."
					+ " Admins have been notified");
		}
	}
}