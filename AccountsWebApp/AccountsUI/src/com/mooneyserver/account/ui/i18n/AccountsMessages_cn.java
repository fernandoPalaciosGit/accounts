package com.mooneyserver.account.ui.i18n;

public class AccountsMessages_cn extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "会计"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "登录"},
		{LOGIN_WELCOME, "会计软件欢迎您！"},
		{USERNAME, "用户名"},
		{PASSWORD, "密码"},
		{FORGOT_PWD, "忘记我的密码"},
		
		/* Language Settings sub window */
		{SELECT_LANGUAGE, "选择您的语言"}
	};
}