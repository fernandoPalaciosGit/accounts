package com.mooneyserver.account.ui.view;

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
public class AccountsLoginView extends AbstractBaseView 
	implements LoginListener {

	public AccountsLoginView() {
		constructHeader();
		
		Panel loginPanel = new Panel(STRINGS.getString(AccountsMessages.LOGIN_WELCOME));
		loginPanel.setWidth("400px");
		
		LoginForm loginForm = new LoginForm();
		loginForm.setUsernameCaption(STRINGS.
				getString(AccountsMessages.USERNAME));
		loginForm.setPasswordCaption(STRINGS.
				getString(AccountsMessages.PASSWORD));		
		
		loginForm.addListener(this);
		loginForm.setHeight("150px");
		loginPanel.addComponent(loginForm);
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		Button forgotPwd = new Button(STRINGS.getString(AccountsMessages.FORGOT_PWD));
		forgotPwd.setStyleName(BaseTheme.BUTTON_LINK);
		hl.addComponent(forgotPwd);
		hl.setComponentAlignment(forgotPwd, Alignment.BOTTOM_RIGHT);
		loginPanel.addComponent(hl);
		
		
		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		
		constructFooter();
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		// TODO Auto-generated method stub
	}	
}