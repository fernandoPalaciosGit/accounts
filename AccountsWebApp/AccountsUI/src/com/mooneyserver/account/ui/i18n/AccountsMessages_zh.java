package com.mooneyserver.account.ui.i18n;

public class AccountsMessages_zh extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "å®¶åº­è´¢åŠ¡"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "ç™»å½•"},
		{LOGIN_WELCOME, "å®¶åº­è´¢åŠ¡è½¯ä»¶æ¬¢è¿Žæ‚¨ï¼�"},
		{USERNAME, "ç”¨æˆ·å��"},
		{PASSWORD, "å¯†ç �"},
		{FORGOT_PWD, "å¿˜è®°å¯†ç �"},
		
		/* Language Settings sub window */
		{SELECT_LANGUAGE, "é€‰æ‹©æ‚¨çš„è¯­è¨€"},
		{NEXT_LANGUAGE_UP, "Next Language (Up)"},
		{NEXT_LANGUAGE_DOWN, "Next Language (Down)"}
	};
}