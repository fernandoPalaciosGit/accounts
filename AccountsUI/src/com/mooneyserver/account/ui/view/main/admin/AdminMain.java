package com.mooneyserver.account.ui.view.main.admin;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.main.AbstractBaseView;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class AdminMain extends AbstractBaseView implements IMainView {

	private static final long serialVersionUID = 1L;

	private Button userSettings;
	private Tab userTab1, userTab2, userTab3, userTab4, userTab5;
	
	public AdminMain() {
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(12, Sizeable.UNITS_PERCENTAGE);
		hsp.setSizeFull();
		hsp.setLocked(true);
        addComponent(hsp);

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setStyleName("side-panel");
        
        userSettings = new Button();
        userSettings.setStyleName(BaseTheme.BUTTON_LINK);
        userSettings.setIcon(IconManager.getIcon(IconManager.USER_SETTINGS));
        userSettings.setEnabled(false);

        vl.addComponent(userSettings);
        vl.setComponentAlignment(userSettings, Alignment.MIDDLE_CENTER);
        
        hsp.addComponent(vl);
        
        hsp.addComponent(buildUserSettingsAccordion());

        hsp.setSizeFull();
        addComponent(hsp);
        setExpandRatio(hsp, 1);

		buildStringsFromLocale();
	}
	
	
	private Accordion buildUserSettingsAccordion() {
		Accordion acc = new Accordion();
		acc.setSizeFull();
				 
		// Add the components as tabs in the Accordion.
		userTab1 = acc.addTab(new UserSessionTab());
		userTab2 = acc.addTab(genUserDetailsTab());
		userTab3 = acc.addTab(genSchedulesTab());
		userTab4 = acc.addTab(genLocaleTab());
		userTab5 = acc.addTab(genLocaleTab());
		
		return acc;
	}
	
	// TODO: Implement Change Password, assign new email address
	private VerticalLayout genUserDetailsTab() {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		
		return vl;
	}
	
	// TODO: Allow scheduling of report runs
	private VerticalLayout genSchedulesTab() {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);		
		
		return vl;
	}
	
	// TODO: Set locale settings, lang, date format, money format etc...
	private VerticalLayout genLocaleTab() {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);		
		
		return vl;
	}
	
	
	@Override
	public String getDisplayName() {return STRINGS.getString(AccountsMessages.ADMIN_WIN_HEADER);}

	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		userSettings.setCaption("User Settings");
		
		userTab1.setCaption("User Session");
		userTab2.setCaption("User Details");
		userTab3.setCaption("Schedules");
		userTab4.setCaption("Alerts");
		userTab5.setCaption("Locale");
	}

	@Override
	public void refreshView() {}
}