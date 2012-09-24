package com.mooneyserver.account.ui.view.main.dashboard;

import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IMainView;
import com.mooneyserver.account.ui.manager.EMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.view.main.AbstractBaseView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class Dashboard extends AbstractBaseView implements Button.ClickListener, IMainView {
	
	private static final long serialVersionUID = 1L;
	
	private Button accounts, reports, settings, graphs;
	private ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	private final String ACCOUNTS = "ACCOUNTS";
	private final String REPORTS = "REPORS";
	private final String GRAPHS = "GRAPHS";
	private final String SETTINGS = "SETTINGS";
	
	public Dashboard() {		
		HorizontalLayout hl = new HorizontalLayout();
		VerticalLayout vLeft = new VerticalLayout();
		VerticalLayout vRight = new VerticalLayout();
		
		accounts = new Button();
		accounts.setStyleName(BaseTheme.BUTTON_LINK);
		accounts.setIcon(IconManager.getIcon(IconManager.DASHBOARD_ACCOUNTS));
		accounts.addListener(this);
		accounts.setData(ACCOUNTS);
		
		graphs = new Button();
		graphs.setStyleName(BaseTheme.BUTTON_LINK);
		graphs.setIcon(IconManager.getIcon(IconManager.DASHBOARD_GRAPHS));
		graphs.addListener(this);
		graphs.setData(GRAPHS);

		vLeft.setSpacing(true);
		vLeft.addComponent(accounts);
		vLeft.addComponent(graphs);
		
		reports = new Button();
		reports.setStyleName(BaseTheme.BUTTON_LINK);
		reports.setIcon(IconManager.getIcon(IconManager.DASHBOARD_REPORTS));
		reports.addListener(this);
		reports.setData(REPORTS);
		
		settings = new Button();
		settings.setStyleName(BaseTheme.BUTTON_LINK);
		settings.setIcon(IconManager.getIcon(IconManager.DASHBOARD_SETTINGS));
		settings.addListener(this);
		settings.setData(SETTINGS);
		
		vRight.setSpacing(true);
		vRight.addComponent(reports);
		vRight.addComponent(settings);
		
		hl.setSpacing(true);
		hl.addComponent(vLeft);
		hl.addComponent(vRight);
		
		addComponent(hl);
		setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		setExpandRatio(hl, 1);
		
		buildStringsFromLocale();
	}
	
	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		accounts.setDescription(STRINGS.getString(AccountsMessages.DASHBRD_BAL_SHEET_TOOLTIP));
		reports.setDescription(STRINGS.getString(AccountsMessages.DASHBRD_REP_TOOLTIP));
		graphs.setDescription(STRINGS.getString(AccountsMessages.DASHBRD_GRAPH_TOOLTIP));
		settings.setDescription(STRINGS.getString(AccountsMessages.DASHBRD_SETTINGS_TOOLTIP));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		switch ((String) event.getButton().getData()) {
			case ACCOUNTS:
				AccountsApplication.getInstance().nav
					.openMainView(EMainView.BAL_SHEET);
				break;
			default:
				Messenger.notYetImplemented();
		}
	}

	@Override
	public String getDisplayName() {return STRINGS.getString(AccountsMessages.DASHBRD_WIN_HEADER);}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}