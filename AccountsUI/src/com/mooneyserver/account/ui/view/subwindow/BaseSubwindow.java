package com.mooneyserver.account.ui.view.subwindow;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.ui.iface.IContainsCustomAnnotations;
import com.vaadin.ui.Window;

public class BaseSubwindow extends Window implements IContainsCustomAnnotations {
	
	private static final long serialVersionUID = -1L;

	enum SubWindowLevel {
		LEVEL_1, LEVEL_2;
	}
	
	public BaseSubwindow(String localizedTitle) {
		super(AccountsApplication.getResourceBundle().
				getString(localizedTitle));
		
		loadBackendServices();
		
		setModal(true);
		setResizable(false);
		setWidth("250px");
		
		/*
		 * To change window style use below
		 */
		// setStyleName(Reindeer.WINDOW_LIGHT)
		// setStyleName(Reindeer.WINDOW_DARK)
	}
	
	public void loadBackendServices() {
        BackendServiceLookup.injectBackendServices(this);
    }
	
	@Override
	public void close() {
		super.close();
		AccountsApplication.getInstance().nav.peek().refreshView();
	}
}