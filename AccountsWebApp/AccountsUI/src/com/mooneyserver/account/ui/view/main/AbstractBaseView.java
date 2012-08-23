package com.mooneyserver.account.ui.view.main;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public abstract class AbstractBaseView extends VerticalLayout implements
		IAccountsView {
	
	public static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_UI);
	
	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	private SettingsDropdown settingsDropdown;
	private Label pageName;
	
	// Instance Initialisation block
	// This will be called before the constructor block
	// for all view subclasses and will enable the injected
	// @BusinessProcess resources to be access from the
	// subclass constructor
	{
		loadBackendServices();
		AccountsApplication.getInstance().breadcrumb
			.addScreenToBreadcrumb(this.getDisplayName());
	}
	
	protected void constructHeader() {
		setSpacing(false);
		setSizeFull();
		
		HorizontalLayout langBar = new HorizontalLayout();
		
        addComponent(langBar);
        langBar.setHeight("40px");
        langBar.setWidth("100%");
        langBar.setStyleName("v-header");
        langBar.setSpacing(true);
        langBar.setMargin(false, true, false, true);
        
        pageName = AccountsApplication.getInstance()
        		.breadcrumb.getCurrentDisplayLabel();
        langBar.addComponent(pageName);
        langBar.setComponentAlignment(pageName, 
        		Alignment.MIDDLE_CENTER);
        
        settingsDropdown = new SettingsDropdown();
        langBar.addComponent(settingsDropdown);
        langBar.setComponentAlignment(settingsDropdown, 
        		Alignment.MIDDLE_RIGHT);
        
        if (AccountsApplication.getInstance().getUser() != null)
        	addComponent(AccountsApplication.getInstance().menu);
	}
	
	protected void constructFooter() {
		HorizontalLayout footerBar = new HorizontalLayout();
		
		footerBar.setHeight("125px");
		footerBar.setWidth("100%");
		footerBar.setStyleName("v-footer");
		footerBar.setSpacing(true);
		footerBar.setMargin(true, false, true, true);
		
		VerticalLayout vl = new VerticalLayout();
		
		Label hostedLbl = new Label("Hosted By:");
		hostedLbl.setSizeFull();
		
		Button hostedByLnk = new Button();
		hostedByLnk.setStyleName(BaseTheme.BUTTON_LINK);
		hostedByLnk.setIcon(IconManager.getIcon(IconManager.OPEN_SHIFT_IMG));
		hostedByLnk.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance()
					.getMainWindow().executeJavaScript(
			            "window.open('https://openshift.redhat.com/app/',"
					   +" 'linkToOpenShift')");
			}
		});
		
		vl.addComponent(hostedLbl);
		vl.addComponent(hostedByLnk);
		
		footerBar.addComponent(vl);
		footerBar.setComponentAlignment(vl, Alignment.MIDDLE_LEFT);
		
		footerBar.addComponent(AccountsApplication.getHelpBubble());
		
		addComponent(footerBar);
		setComponentAlignment(footerBar, Alignment.BOTTOM_CENTER);
	}
	
	public void reloadMainFrameComponentStrings() {
		STRINGS = AccountsApplication.getResourceBundle();
        
        settingsDropdown.setDescription(STRINGS.getString(
        		AccountsMessages.HEADER_SETTINGS));
        
        settingsDropdown.updateStrings();
        pageName.setCaption(AccountsApplication.getInstance()
        		.breadcrumb.getCurrentDisplayLabel().getCaption());
	}
	
	public void loadBackendServices() {
        BackendServiceLookup.injectBackendServices(this);
    }
}