package com.mooneyserver.account.ui.view.dashboard;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.view.AbstractBaseView;
import com.mooneyserver.account.ui.view.IconManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class Dashboard extends AbstractBaseView implements Button.ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	Button accounts, reports, settings, graphs;
	
	public Dashboard() {
		constructHeader();
		
		HorizontalLayout hl = new HorizontalLayout();
		VerticalLayout vLeft = new VerticalLayout();
		VerticalLayout vRight = new VerticalLayout();
		
		accounts = new Button();
		accounts.setStyleName(BaseTheme.BUTTON_LINK);
		accounts.setIcon(IconManager.getIcon(IconManager.DASHBOARD_ACCOUNTS));
		accounts.addListener(this);
		
		graphs = new Button();
		graphs.setStyleName(BaseTheme.BUTTON_LINK);
		graphs.setIcon(IconManager.getIcon(IconManager.DASHBOARD_GRAPHS));
		graphs.addListener(this);

		vLeft.setSpacing(true);
		vLeft.addComponent(accounts);
		vLeft.addComponent(graphs);
		
		reports = new Button();
		reports.setStyleName(BaseTheme.BUTTON_LINK);
		reports.setIcon(IconManager.getIcon(IconManager.DASHBOARD_REPORTS));
		reports.addListener(this);
		
		settings = new Button();
		settings.setStyleName(BaseTheme.BUTTON_LINK);
		settings.setIcon(IconManager.getIcon(IconManager.DASHBOARD_SETTINGS));
		settings.addListener(this);
		
		vRight.setSpacing(true);
		vRight.addComponent(reports);
		vRight.addComponent(settings);
		
		hl.setSpacing(true);
		hl.addComponent(vLeft);
		hl.addComponent(vRight);
		
		addComponent(hl);
		setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		setExpandRatio(hl, 1);
		
		constructFooter();
		
		buildStringsFromLocale();
	}
	
	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		// TODO: Localize
		accounts.setDescription("Balance Sheet Mgmt");
		reports.setDescription("Balance Sheet Reporting");
		graphs.setDescription("Graphical View of Accounts Status");
		settings.setDescription("Global Settings Mgmt");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Messenger.notYetImplemented();
	}
}