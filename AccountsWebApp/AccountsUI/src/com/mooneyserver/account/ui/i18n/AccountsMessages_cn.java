package com.mooneyserver.account.ui.i18n;

public class AccountsMessages_cn extends AccountsMessages {

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "会计"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "登录"},
		{USERNAME, "用户名"},
		{PASSWORD, "密码"}
	};
}