package com.mooneyserver.account.ui.view.subwindow;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.AccountsApplication;
import com.vaadin.ui.Window;

public class BaseSubwindow extends Window {
	
	private static final long serialVersionUID = -1L;

	enum SubWindowLevel {
		LEVEL_1, LEVEL_2;
	}
	
	public BaseSubwindow(String title) {
		super(AccountsApplication.getResourceBundle().getString(AccountsMessages.SELECT_LANGUAGE));
		
		setModal(true);
		setBorder(Window.BORDER_DEFAULT);
		setResizable(false);
		setWidth("250px");
	}
}