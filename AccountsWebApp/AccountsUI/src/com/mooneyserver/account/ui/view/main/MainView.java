package com.mooneyserver.account.ui.view.main;

import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class MainView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;

	private ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	private SettingsDropdown settingsDropdown = new SettingsDropdown();
	private Label pageName = new Label();
	private Layout mainContent = new VerticalLayout(); // dynamic content holder
	
	public MainView() {
		constructHeader();
		
		addComponent(AccountsApplication.getInstance().nav.getMenuBar());
		
		
		addComponent(mainContent);
		setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);

		
		constructFooter();
	}
	
	private void constructHeader() {
		setSpacing(false);
		setSizeFull();
		
		HorizontalLayout langBar = new HorizontalLayout();
		
        addComponent(langBar);
        langBar.setHeight("40px");
        langBar.setWidth("100%");
        langBar.setStyleName("v-header");
        langBar.setSpacing(true);
        langBar.setMargin(false, true, false, true);
        
        langBar.addComponent(pageName);
        langBar.setComponentAlignment(pageName, 
        		Alignment.MIDDLE_CENTER);
        
        langBar.addComponent(settingsDropdown);
        langBar.setComponentAlignment(settingsDropdown, 
        		Alignment.MIDDLE_RIGHT);
	}
	
	private void constructFooter() {
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

			private static final long serialVersionUID = 1L;

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
		
		footerBar.addComponent(AccountsApplication.getHelpBubble());
		
		addComponent(footerBar);
		setComponentAlignment(footerBar, Alignment.BOTTOM_CENTER);
	}
	
	public void reloadMainFrameComponentStrings() {
		STRINGS = AccountsApplication.getResourceBundle();
        
        settingsDropdown.setDescription(STRINGS.getString(
        		AccountsMessages.HEADER_SETTINGS));
        
        settingsDropdown.updateStrings();
	}
	
	private void setPageName(String pageName) {
		this.pageName.setContentMode(Label.CONTENT_XHTML);
		this.pageName.setValue("<font face=\"Chicago\" color=\"grey\" size=\"5\">" 
				+ pageName + "</font>" );
	}
	
	public void setDynamicContent(IAccountsView view) {
		// Set visibility of MenuBar...
		// TODO: Revisit from a security point of view
		// TODO: Hidden menu bar is hackable on client side
		if (AccountsApplication.getInstance().getUser() != null) {
			AccountsApplication.getInstance().nav
				.getMenuBar().setVisible(true);
		} else {
			AccountsApplication.getInstance().nav
				.getMenuBar().setVisible(false);
		}
		
		setPageName(view.getDisplayName() );		
		
		// Update the Dynamic content section
		removeComponent(mainContent);
		mainContent = view;
		mainContent.setSizeFull();
		addComponent(mainContent, 2);
		setExpandRatio(mainContent, 1);
		
		requestRepaintAll();
		
		settingsDropdown.requestRepaint();
	}

	public void refreshView() { 
		settingsDropdown.updateStrings();
	}
}