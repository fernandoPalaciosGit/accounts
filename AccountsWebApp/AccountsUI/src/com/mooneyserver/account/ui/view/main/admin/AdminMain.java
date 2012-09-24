package com.mooneyserver.account.ui.view.main.admin;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IMainView;
import com.mooneyserver.account.ui.view.main.AbstractBaseView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

// TODO: Stub
public class AdminMain extends AbstractBaseView implements IMainView {

	private static final long serialVersionUID = 1L;

	public AdminMain() {
		HorizontalLayout hl = new HorizontalLayout();
		
		hl.addComponent(new Label("W.I.P."));
		
		addComponent(hl);
		setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		setExpandRatio(hl, 1);
		
		buildStringsFromLocale();
	}
	
	@Override
	public String getDisplayName() {return STRINGS.getString(AccountsMessages.ADMIN_WIN_HEADER);}

	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}