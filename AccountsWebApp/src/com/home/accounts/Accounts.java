package com.home.accounts;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.home.accounts.component.*;
import com.home.accounts.panel.InsertSidePanel;
import com.home.accounts.panel.SearchView;
import com.home.accounts.subwindow.LoginPrompt;

public class Accounts extends Application implements Button.ClickListener {
	
	private static final long serialVersionUID = 3350210699526697396L;
	
	private enum ViewType {
		UPDATE_ACC, REPORTS, ADMIN
	}
	private ViewType currentView;
	
	private Button updateAcc;
    private Button report;
    private Button admin;
    
    private HorizontalSplitPanel horizontalSplit;
	
	@Override
	public void init() {
	    this.setTheme("accounts");
	    
	    // Initialise toolbar options
	    this.updateAcc = new Button("Update Accounts");
	    this.report = new Button("Reports");
	    this.admin = new Button("Admin");
	    
	    // Set side panel boundary
	    this.horizontalSplit = new HorizontalSplitPanel();
	    
	    buildMainLayout();
	    initSideBar();
	    
	    setMainComponent(ListView.getInstance());
	}
	
	private void initSideBar() {	    
	    // Still need to add switch for this to be regenerated
		// based off of the toolbar
		horizontalSplit.setFirstComponent(new InsertSidePanel());
	}

	private void buildMainLayout() {
        setMainWindow(new Window("Accounts"));
        
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
               
        layout.addComponent(createToolbar());
        layout.addComponent(horizontalSplit);

       /* Allocate all available extra space to the horizontal split panel */
       layout.setExpandRatio(horizontalSplit, 1);

       horizontalSplit.setSplitPosition(10, HorizontalSplitPanel.UNITS_PERCENTAGE);

       getMainWindow().setContent(layout);
       
       // prompt login on load
       getMainWindow().addWindow(new LoginPrompt());
    }
	
	/*
	 * Create control bar along top...
	 */
    private HorizontalLayout createToolbar() {
	   HorizontalLayout toolbar = new HorizontalLayout();
	   
	   // Add buttons to toolbar
	   toolbar.addComponent(this.updateAcc);
	   this.updateAcc.setIcon(new ThemeResource("icons/Paper-Money-icon.png"));
	
	   toolbar.addComponent(this.report);
	   toolbar.addComponent(this.admin);
	
	   toolbar.setMargin(true);
	   toolbar.setSpacing(true);
	
	   toolbar.setStyleName("toolbar");
	
	   // Move out of func, need control func to manage view flow
	   this.report.addListener((Button.ClickListener) this);
	
	   return toolbar;
    }
	
	private void setMainComponent(Component c) {
        horizontalSplit.setSecondComponent(c);
    }

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == this.report) {
			setMainComponent(SearchView.getInstance());
		}
	}
}
