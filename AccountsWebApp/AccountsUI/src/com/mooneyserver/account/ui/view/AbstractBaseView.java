package com.mooneyserver.account.ui.view;

import java.util.ResourceBundle;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.mooneyserver.account.ui.view.subwindow.LanguageSelectDialog;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public abstract class AbstractBaseView extends VerticalLayout implements
		IAccountsView {
	
	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	protected void constructHeader() {
		setSpacing(true);
		
		HorizontalLayout langBar = new HorizontalLayout();
        addComponent(langBar);
        langBar.setHeight("44px");
        langBar.setWidth("100%");
        langBar.setStyleName("header");
        langBar.setSpacing(true);
        langBar.setMargin(false, true, false, false);
		
        Button langSelectDlg = new Button("", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().addWindow(new LanguageSelectDialog());
			}
		});
        langSelectDlg.setDescription("Change Langage");
        langSelectDlg.setIcon(new ThemeResource("../runo/icons/16/globe.png"));
        
        langBar.addComponent(langSelectDlg);
        
        
        langBar.setComponentAlignment(langSelectDlg, Alignment.MIDDLE_RIGHT);
	}
	
	protected void constructFooter() {
		
	}
}
