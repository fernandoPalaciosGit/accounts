package com.mooneyserver.account.ui.view;

import java.util.ResourceBundle;

import org.vaadin.aboutbox.AboutBox;
import org.vaadin.hene.popupbutton.PopupButton;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public abstract class AbstractBaseView extends VerticalLayout implements
		IAccountsView {
	
	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	private AboutBox about;
	private PopupButton settingsDropdown;
	private Button langChange, genSettings, signOut;
	
	private BaseWindowClickListener btnListener = new BaseWindowClickListener();
	
	protected void constructHeader() {
		setSpacing(true);
		setSizeFull();
		
		HorizontalLayout langBar = new HorizontalLayout();
		
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
        langBar.setHeight("38px");
        langBar.setWidth("100%");
        langBar.setStyleName("header");
        langBar.setSpacing(true);
        langBar.setMargin(false, true, false, false);
        
        settingsDropdown = new PopupButton("");
        settingsDropdown.setStyleName(BaseTheme.BUTTON_LINK);
        settingsDropdown.setDescription(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        settingsDropdown.setIcon(new ThemeResource("img/header-toolbar-settings.png"));

        VerticalLayout popupLayout = new VerticalLayout();
        popupLayout.setSizeUndefined();

        settingsDropdown.setComponent(popupLayout); // Set popup content

        langChange = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_LANG));
        langChange.setStyleName(BaseTheme.BUTTON_LINK);
        langChange.setIcon(new ThemeResource("img/header-toolbar-change-locale.png"));
        langChange.addListener(btnListener);
        langChange.setData(BaseWindowClickListener.LANG);
        popupLayout.addComponent(langChange);
        
        genSettings = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        genSettings.setStyleName(BaseTheme.BUTTON_LINK);
        genSettings.setIcon(new ThemeResource("img/header-toolbar-settings-small.png"));
        genSettings.addListener(btnListener);
        genSettings.setData(BaseWindowClickListener.GEN_SETTINGS);
        popupLayout.addComponent(genSettings);
        
        signOut = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_SIGNOUT));
        signOut.setStyleName(BaseTheme.BUTTON_LINK);
        signOut.setIcon(new ThemeResource("img/header-toolbar-logout.png"));
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
}