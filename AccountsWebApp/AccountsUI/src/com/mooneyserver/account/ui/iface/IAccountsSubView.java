package com.mooneyserver.account.ui.iface;

import com.mooneyserver.account.ui.manager.EMainView;

public interface IAccountsSubView extends IAccountsView {
	
	public void closeSubView();

	public EMainView getParentType();
	
}