package com.mooneyserver.account.ui.iface;

import com.mooneyserver.account.ui.manager.EMainView;

public interface INonClosableAccountsSubView extends IAccountsView {
	
	public EMainView getParentType();
	
}