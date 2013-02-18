package com.mooneyserver.account.ui.view.main;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class AbstractBaseView extends VerticalLayout implements
		IAccountsView {
	
	public static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_UI);
	
	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	// Instance Initialisation block
	// This will be called before the constructor block
	// for all view subclasses and will enable the injected
	// @BusinessProcess resources to be access from the
	// subclass constructor
	{
		loadBackendServices();
	}
	
	
	public void loadBackendServices() {
        BackendServiceLookup.injectBackendServices(this);
    }	
}