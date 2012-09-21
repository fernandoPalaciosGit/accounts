package com.mooneyserver.account.ui.view.main;

import java.util.ResourceBundle;

import org.vaadin.hene.popupbutton.PopupButton;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

class SettingsDropdown extends PopupButton {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	private Button langChange, genSettings, signOut;
	private BaseWindowClickListener btnListener = new BaseWindowClickListener();
	
	public SettingsDropdown() {
		super("");
		
		setStyleName(BaseTheme.BUTTON_LINK);
        setDescription(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        setIcon(IconManager.getIcon(IconManager.SETTINGS_LARGE));
        
        buildSettings();
	}
	
	private void buildSettings() {
		VerticalLayout popupLayout = new VerticalLayout();
        popupLayout.setSizeUndefined();

        setComponent(popupLayout); // Set popup content

        langChange = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_LANG));
        langChange.setStyleName(BaseTheme.BUTTON_LINK);
        langChange.setImmediate(true);
        langChange.setIcon(IconManager.getIcon(IconManager.SETTINGS_CHANGE_LANG));
        langChange.addListener(btnListener);
        langChange.setData(BaseWindowClickListener.LANG);
        popupLayout.addComponent(langChange);
        
        genSettings = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        genSettings.setStyleName(BaseTheme.BUTTON_LINK);
        genSettings.setImmediate(true);
        genSettings.setIcon(IconManager.getIcon(IconManager.SETTINGS_SMALL));
        genSettings.addListener(btnListener);
        genSettings.setData(BaseWindowClickListener.GEN_SETTINGS);
        popupLayout.addComponent(genSettings);
        
        signOut = new Button(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_SIGNOUT));
        signOut.setStyleName(BaseTheme.BUTTON_LINK);
        signOut.setImmediate(true);
        signOut.setIcon(IconManager.getIcon(IconManager.SETTINGS_LOGOUT));
        signOut.addListener(btnListener);
        signOut.setData(BaseWindowClickListener.SIGNOUT);
        if (AccountsApplication.getInstance().getUser() == null) {
        	signOut.setEnabled(false);
        }
        
        popupLayout.addComponent(signOut);
	}
	
	public void updateStrings() {
		langChange.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_LANG));
        genSettings.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS));
        signOut.setCaption(STRINGS.getString(AccountsMessages.HEADER_SETTINGS_SIGNOUT));
        
        if (AccountsApplication.getInstance().getUser() == null) {
        	signOut.setEnabled(false);
        } else {
        	signOut.setEnabled(true);
        }
	}
}
