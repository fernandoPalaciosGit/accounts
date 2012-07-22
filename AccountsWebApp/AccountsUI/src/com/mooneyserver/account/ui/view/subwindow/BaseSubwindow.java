package com.mooneyserver.account.ui.view.subwindow;

import com.mooneyserver.account.AccountsApplication;
import com.vaadin.ui.Window;

public class BaseSubwindow extends Window {
	
	private static final long serialVersionUID = -1L;

	enum SubWindowLevel {
		LEVEL_1, LEVEL_2;
	}
	
	public BaseSubwindow(String localizedTitle) {
		super(AccountsApplication.getResourceBundle().
				getString(localizedTitle));
		
		setModal(true);
		setResizable(false);
		setWidth("250px");
		
		/*
		 * To change window style use below
		 */
		// setStyleName(Reindeer.WINDOW_LIGHT)
		// setStyleName(Reindeer.WINDOW_DARK)
	}
}