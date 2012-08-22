package com.mooneyserver.account.ui.view.main;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.vaadin.aboutbox.AboutBox;
import org.vaadin.hene.popupbutton.PopupButton;

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
	
	private AboutBox about;
	private PopupButton settingsDropdown;
	private Button langChange, genSettings, signOut;
	
	private BaseWindowClickListener btnListener = new BaseWindowClickListener();
	
	// Instance Initialisation block
	// This will be called before the constructor block
	// for all view subclasses and will enable the injected
	// @BusinessProcess resources to be access from the
	// subclass constructor
	{
		loadBackendServices();
	}
	
	protected void constructHeader() {
		setSpacing(true);
		setSizeFull();
		
		HorizontalLayout langBar = new HorizontalLayout();
		
		langBar.addComponent(AccountsApplication.getHelpBubble());
		
		about = new AboutBox();
		
		about.setCaption(STRINGS.getString(AccountsMessages.APP_TITLE));
        about.setTitle(STRINGS.getString(AccountsMessages.APP_TITLE) 
        		+ "<br/>" + AccountsApplication.APP_VERSION);
        about.setDescription(STRINGS.getString(AccountsMessages.ABOUT_MAIN));
        about.setLinkCaption(STRINGS.getString(AccountsMessages.ABOUT_LINK));
        about.setAlignment(Alignment.TOP_LEFT);
        about.setListener(btnListener);
        
        langBar.addComponent(about);
		
        addComponent(langBar);
        langBar.setHeight("40px");
        langBar.setWidth("100%");
        langBar.setStyleName("v-header");
        langBar.setSpacing(true);
        langBar.setMargin(false, true, false, false);
        
        settingsDropdown = new PopupButton("");
        settingsDropdown.setStyleName(BaseTheme.BUTTON_LINK);
        settingsDropdown.setDescription(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        settingsDropdown.setIcon(IconManager.getIcon(IconManager.SETTINGS_LARGE));

        VerticalLayout popupLayout = new VerticalLayout();
        popupLayout.setSizeUndefined();

        settingsDropdown.setComponent(popupLayout); // Set popup content

        langChange = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_LANG));
        langChange.setStyleName(BaseTheme.BUTTON_LINK);
        langChange.setIcon(IconManager.getIcon(IconManager.SETTINGS_CHANGE_LANG));
        langChange.addListener(btnListener);
        langChange.setData(BaseWindowClickListener.LANG);
        popupLayout.addComponent(langChange);
        
        genSettings = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        genSettings.setStyleName(BaseTheme.BUTTON_LINK);
        genSettings.setIcon(IconManager.getIcon(IconManager.SETTINGS_SMALL));
        genSettings.addListener(btnListener);
        genSettings.setData(BaseWindowClickListener.GEN_SETTINGS);
        popupLayout.addComponent(genSettings);
        
        signOut = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_SIGNOUT));
        signOut.setStyleName(BaseTheme.BUTTON_LINK);
        signOut.setIcon(IconManager.getIcon(IconManager.SETTINGS_LOGOUT));
        signOut.addListener(btnListener);
        signOut.setData(BaseWindowClickListener.SIGNOUT);
        if (AccountsApplication.getInstance().getUser() == null) {
        	signOut.setEnabled(false);
        }
        popupLayout.addComponent(signOut);
        
        langBar.addComponent(settingsDropdown);
        langBar.setComponentAlignment(settingsDropdown, Alignment.MIDDLE_RIGHT);
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
		
		Button hostedByLnk = new Button();
		hostedByLnk.setStyleName(BaseTheme.BUTTON_LINK);
		hostedByLnk.setIcon(IconManager.getIcon(IconManager.OPEN_SHIFT_IMG));
		hostedByLnk.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().getMainWindow().executeJavaScript(
			            "window.open('https://openshift.redhat.com/app/', 'linkToOpenShift')");
			}
		});
		
		vl.addComponent(hostedLbl);
		vl.addComponent(hostedByLnk);
		
		footerBar.addComponent(vl);
		footerBar.setComponentAlignment(vl, Alignment.MIDDLE_LEFT);
		
		addComponent(footerBar);
		setComponentAlignment(footerBar, Alignment.BOTTOM_CENTER);
	}
	
	public void reloadMainFrameComponentStrings() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		about.setCaption(STRINGS.getString(AccountsMessages.APP_TITLE));
        about.setTitle(STRINGS.getString(AccountsMessages.APP_TITLE) 
        		+ "<br/>" + AccountsApplication.APP_VERSION);
        about.setDescription(STRINGS.getString(AccountsMessages.ABOUT_MAIN));
        about.setLinkCaption(STRINGS.getString(AccountsMessages.ABOUT_LINK));
        
        settingsDropdown.setDescription(STRINGS.getString(
        		AccountsMessages.HEADER_SETTINGS));
        
        langChange.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_LANG));
        genSettings.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        signOut.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_SIGNOUT));
	}
	
	public void loadBackendServices() {
        BackendServiceLookup.injectBackendServices(this);
    }
}