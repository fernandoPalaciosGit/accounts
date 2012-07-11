package com.mooneyserver.account.ui.i18n;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class AccountsMessages extends ListResourceBundle implements Serializable {
	
	private static final long serialVersionUID = 1L;


	/*
	 * Main Windows Strings
	 */
	public static final String APP_TITLE = "Accounts.MainWindow.AppTitle";
	public static final String TOOLBAR_LANG_BTN_TOOLTIP = "Accounts.MainWindow.LangChange.Tooltip";
	
	
	/*
	 * Login Window String
	 */
	public static final String LOGIN_BUTTON = "Accounts.LoginView.LoginBtn";
	public static final String LOGIN_WELCOME = "Accounts.LoginView.Welcome";
	public static final String USERNAME = "Accounts.LoginView.Username";
	public static final String PASSWORD = "Accounts.LoginView.Password";
	public static final String FORGOT_PWD = "Accounts.LoginView.ForgotPassword";
	
	/*
	 * Language Settings sub window
	 */
	public static final String SELECT_LANGUAGE = "Accounts.Settings.Language.ChooseLanguage";
	public static final String NEXT_LANGUAGE_UP = "Accounts.Settings.Language.NextUp";
	public static final String NEXT_LANGUAGE_DOWN = "Accounts.Settings.Language.NextDown";

	@Override
	protected Object[][] getContents() {
		return null;
	}
}