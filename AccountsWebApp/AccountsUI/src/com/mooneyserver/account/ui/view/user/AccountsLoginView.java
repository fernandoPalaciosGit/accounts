package com.mooneyserver.account.ui.view.user;

import java.util.logging.Level;

import com.mooneyserver.account.businesslogic.exception.user.AccountsUserNotActiveException;
import com.mooneyserver.account.businesslogic.user.IUserService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.view.AbstractBaseView;
import com.mooneyserver.account.ui.view.IconManager;
import com.mooneyserver.account.ui.view.subwindow.user.CreateNewUserDialog;
import com.mooneyserver.account.ui.view.subwindow.user.ResetPasswordDialog;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;


public class AccountsLoginView extends AbstractBaseView 
	implements LoginListener, ClickListener {
	
	private static final long serialVersionUID = 1L;

	@BusinessProcess
	private static IUserService userSvc;
	
	private Panel loginPanel;
	private LoginForm loginForm;
	private Button forgotPwd, regNewUser;
	
	private final String RESET_PWD = "RESET_PWD";
	private final String NEW_USER = "NEW_USER";
	
	public AccountsLoginView() {
		constructHeader();
		
		loginPanel = new Panel();
		loginPanel.setImmediate(true);
		loginPanel.setWidth("400px");
		
		loginForm = new LoginForm();
		
		loginForm.setImmediate(true);
		loginForm.addListener(this);
		loginForm.setHeight("150px");
		loginPanel.addComponent(loginForm);
		
		VerticalLayout vl = new VerticalLayout();
		
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setSizeFull();
		forgotPwd = new Button();
		forgotPwd.setStyleName(BaseTheme.BUTTON_LINK);
		forgotPwd.setIcon(IconManager.getIcon(IconManager.FORGOT_PASSWORD));
		forgotPwd.setData(RESET_PWD);
		forgotPwd.addListener(this);
		hl1.addComponent(forgotPwd);
		hl1.setComponentAlignment(forgotPwd, Alignment.BOTTOM_RIGHT);
		vl.addComponent(hl1);
		
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setSizeFull();
		regNewUser = new Button();
		regNewUser.setStyleName(BaseTheme.BUTTON_LINK);
		regNewUser.setIcon(IconManager.getIcon(IconManager.CREATE_USER));
		regNewUser.setData(NEW_USER);
		regNewUser.addListener(this);
		hl2.addComponent(regNewUser);
		hl2.setComponentAlignment(regNewUser, Alignment.BOTTOM_RIGHT);
		vl.addComponent(hl2);
		
		loginPanel.addComponent(vl);
		
		
		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		setExpandRatio(loginPanel, 1);
		
		constructFooter();
		
		buildStringsFromLocale();
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		boolean userLogin = false;
		try {
			userLogin = userSvc.validateUserPassword(
					event.getLoginParameter("username"), 
					event.getLoginParameter("password"));
		} catch (AccountsUserNotActiveException e) {
			log.log(Level.INFO, "Inactive User ["+event.getLoginParameter("username")
					+"] attempting to login", e);
			Messenger.genericMessage(MessageSeverity.WARNING, 
					"Your User is not active");
			return;
		} catch(Exception e) {
			log.log(Level.SEVERE, "Exception Thrown For UI when trying to login", e);
			Messenger.genericMessage(MessageSeverity.ERROR, 
					"An error has occurred with the login.");
			return;
		}
		
		log.info("User Login Requested for ["
				+event.getLoginParameter("username")+"]. Status ["
				+userLogin+"]");
		
		if (userLogin) {
			// Load new Windows
		} else {
			Messenger.genericMessage(MessageSeverity.WARNING, 
					"Incorrect Username or Password");
		}
	}

	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		loginPanel.setCaption(STRINGS.getString(AccountsMessages.LOGIN_WELCOME));
		
		loginForm.setUsernameCaption(STRINGS.
				getString(AccountsMessages.EMAIL_ADDRESS));
		loginForm.setPasswordCaption(STRINGS.
				getString(AccountsMessages.PASSWORD));
		loginForm.setLoginButtonCaption(STRINGS.
				getString(AccountsMessages.LOGIN_BUTTON));
		
		forgotPwd.setCaption(STRINGS.getString(AccountsMessages.FORGOT_PWD));
		regNewUser.setCaption(STRINGS.getString(AccountsMessages.CREATE_NEW_USER));
		
		loginPanel.requestRepaintAll();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		switch((String) event.getButton().getData()) {
			case RESET_PWD:
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new ResetPasswordDialog());
				break;
			case NEW_USER:
				AccountsApplication.getInstance().
				getMainWindow().
					addWindow(new CreateNewUserDialog());
				break;
		}
	}
}