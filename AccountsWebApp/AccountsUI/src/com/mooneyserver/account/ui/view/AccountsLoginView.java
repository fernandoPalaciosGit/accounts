package com.mooneyserver.account.ui.view;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import com.mooneyserver.account.businesslogic.user.UserBusinessService;
import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.i18n.AccountsMessages;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@Stateful
public class AccountsLoginView extends AbstractBaseView implements LoginListener {

	@EJB
	UserBusinessService userSvc;
	
	private Panel loginPanel;
	private LoginForm loginForm;
	private Button forgotPwd;
	
	public AccountsLoginView() {
		constructHeader();
		
		loginPanel = new Panel();
		loginPanel.setImmediate(true);
		loginPanel.setWidth("400px");
		
		loginForm = new LoginForm();
		
		loginForm.addListener(this);
		loginForm.setHeight("150px");
		loginPanel.addComponent(loginForm);
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		forgotPwd = new Button();
		forgotPwd.setStyleName(BaseTheme.BUTTON_LINK);
		hl.addComponent(forgotPwd);
		hl.setComponentAlignment(forgotPwd, Alignment.BOTTOM_RIGHT);
		loginPanel.addComponent(hl);
		
		
		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		
		constructFooter();
		
		buildStringsFromLocale();
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		System.out.println(event.getLoginParameter("username"));
		System.out.println(event.getLoginParameter("password"));
		
		// TODO: Implement a password verification
		//userSvc.verifyUserPassword(username, password)
	}

	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		loginPanel.setCaption(STRINGS.getString(AccountsMessages.LOGIN_WELCOME));
		
		loginForm.setUsernameCaption(STRINGS.
				getString(AccountsMessages.USERNAME));
		loginForm.setPasswordCaption(STRINGS.
				getString(AccountsMessages.PASSWORD));
		loginForm.setLoginButtonCaption(STRINGS.
				getString(AccountsMessages.LOGIN_BUTTON));
		
		forgotPwd.setCaption(STRINGS.getString(AccountsMessages.FORGOT_PWD));
		
		loginPanel.requestRepaintAll();
	}	
}