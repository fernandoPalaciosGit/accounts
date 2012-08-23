package com.mooneyserver.account.ui.view.main;

import com.mooneyserver.account.ui.notification.Messenger;
import com.vaadin.ui.MenuBar;

public class MainMenuBar extends MenuBar {

	private static final long serialVersionUID = 1L;
	
	private MenuItem dashboard, balSheet, reporting, graph, admin, about;
	
	public MainMenuBar() {
		super();
		
		setWidth("100%");
		
		dashboard = this.addItem("Dashboard", topLevelMenuCommand);
		balSheet = this.addItem("My Balance Sheet(s)", topLevelMenuCommand);
		reporting = this.addItem("Reporting", topLevelMenuCommand);
		graph = this.addItem("Graphs", topLevelMenuCommand);
		admin = this.addItem("Administration", topLevelMenuCommand);
		about = this.addItem("About", topLevelMenuCommand);
	}
	
	private Command topLevelMenuCommand = new Command() {

		private static final long serialVersionUID = 1L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			switch(selectedItem.getText()) {
				case "Dashboard":
					Messenger.notYetImplemented();
					break;
				default:
					Messenger.notYetImplemented();
			}
		}		
	};
}