package com.mooneyserver.account.ui.view.main.accounts;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.ui.manager.EMainView;
import com.mooneyserver.account.ui.view.main.AbstractBaseSubView;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class SingleBalanceSheet extends AbstractBaseSubView {
	
	private static final long serialVersionUID = 1L;
	private BalanceSheet sheet;
	
	public SingleBalanceSheet(BalanceSheet sheet) {
		this.sheet = sheet;
		
		HorizontalLayout hl = new HorizontalLayout();
		
		Button closeMe = new Button("Close Me");
		closeMe.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				closeSubView();
			}
		});
		hl.addComponent(closeMe);
		
		addComponent(hl);
		setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		setExpandRatio(hl, 1);

		buildStringsFromLocale();
	}
	
	@Override 
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
	}

	@Override
	public String getDisplayName() {
		return STRINGS.getString(AccountsMessages
				.BAL_SHEET_SUB_MENU_NAME).replace("{0}", 
						sheet.getSheetName());
	}

	@Override
	public EMainView getParentType() {	return EMainView.BAL_SHEET; }
}