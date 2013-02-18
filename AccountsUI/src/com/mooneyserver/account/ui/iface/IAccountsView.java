package com.mooneyserver.account.ui.iface;

import com.vaadin.ui.Layout;

public interface IAccountsView extends Layout, ILocaleSpecificStrings, IContainsCustomAnnotations {
	public String getDisplayName();
	
	public void refreshView();
}
