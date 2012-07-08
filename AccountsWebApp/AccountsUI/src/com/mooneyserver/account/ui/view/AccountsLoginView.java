package com.mooneyserver.account.ui.view;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IAccountsView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;

import static com.mooneyserver.account.ui.i18n.SupportedLanguages.SUPPORTED_LANGS;

public class AccountsLoginView extends VerticalLayout implements IAccountsView, LoginListener {

	private static final long serialVersionUID 
		= -1L;

	@SuppressWarnings("serial")
	public AccountsLoginView() {
		setSizeFull();
		
		HorizontalLayout langBar = new HorizontalLayout();
		langBar.setHeight("50px");
		addComponent(langBar);
		setComponentAlignment(langBar, Alignment.TOP_RIGHT);
		
		
		for (String langName : SUPPORTED_LANGS.keySet()) {
			Button lang = new Button(langName);
			lang.setIcon(new ThemeResource(SUPPORTED_LANGS.get(langName)));
			lang.addListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					System.out.println("Click event for lang change");
				}
			});
			langBar.addComponent(lang);
		}

		
		LoginForm loginForm = new LoginForm();
		loginForm.setUsernameCaption(AccountsApplication
				.getResourceBundle().getString(AccountsMessages
						.USERNAME));
		loginForm.setPasswordCaption(AccountsApplication
				.getResourceBundle().getString(AccountsMessages
						.PASSWORD));		
		
		loginForm.addListener(this);
		
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_RIGHT);
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		// TODO Auto-generated method stub
	}	
}