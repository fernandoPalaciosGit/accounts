package com.mooneyserver.account.ui.view.main;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsSubView;

@SuppressWarnings("serial")
public abstract class AbstractBaseSubView extends AbstractBaseView 
	implements IAccountsSubView {
	
	@Override
	public void closeSubView() {
		AccountsApplication.getInstance()
			.nav.removeView(this);
	}
}