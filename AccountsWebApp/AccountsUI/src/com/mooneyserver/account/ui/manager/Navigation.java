package com.mooneyserver.account.ui.manager;

import java.util.UUID;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsSubView;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.mooneyserver.account.ui.iface.IMainView;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Navigation controller which will
 * act as the display manager and link 
 * manager for the application. One 
 * instance of this class should exist
 * per session. 
 *
 */
public final class Navigation {
	
	MenuBar menu;
	NonNullHashMap<UUID, IAccountsView> displayMgr;
	NonNullHashMap<MenuItem, UUID> menuItemLinkMgr;
	
	MenuItem dashboardMenuItem, balSheetMenuItem, reportsMenuItem, 
		graphsMenuItem, adminMenuItem;
	
	/**
	 * Constructor for Navigation controller
	 * Should only be instantiated once per session
	 * Defines the main areas of the application
	 * to be refreneced from the menu bar.
	 * ie. the static links in the menu bar
	 */
	public Navigation() {
		// Init the Cached View Manager
		displayMgr = new NonNullHashMap<>();
		menuItemLinkMgr = new NonNullHashMap<>();
		
		// Init the Menu Bar
		menu = new MenuBar();
		menu.setWidth("100%");
		
		// Init Static Menu Items
		dashboardMenuItem = menu.addItem("Dashboard", new TopLevelWindowCommand(EMainView.DASHBOARD));
		menuItemLinkMgr.put(dashboardMenuItem, UUID.randomUUID());
		
		balSheetMenuItem = menu.addItem("My Balance Sheet(s)", new TopLevelWindowCommand(EMainView.BAL_SHEET));
		menuItemLinkMgr.put(balSheetMenuItem, UUID.randomUUID());
		
		reportsMenuItem = menu.addItem("Reporting", new TopLevelWindowCommand(EMainView.REPORTS));
		menuItemLinkMgr.put(reportsMenuItem, UUID.randomUUID());
		
		graphsMenuItem = menu.addItem("Graphs", new TopLevelWindowCommand(EMainView.GRAPHS));
		menuItemLinkMgr.put(graphsMenuItem, UUID.randomUUID());
		
		adminMenuItem = menu.addItem("Administration", new TopLevelWindowCommand(EMainView.ADMIN));
		menuItemLinkMgr.put(adminMenuItem, UUID.randomUUID());
	}
	
	
	/**
	 * Return the application Menu Bar
	 * with static links set
	 * 
	 * @return
	 * 	MenuBar
	 */
	public MenuBar getMenuBar() {	
		return menu;
	}
	
	/**
	 * Number of currently cached windows
	 * by the display manager
	 * 
	 * @return
	 * 	int
	 */
	public int getViewCount() {
		return displayMgr.size();
	}
	
	/**
	 * To be called to add a NON-STATIC view
	 * If this is a sub view (ie not a login view)
	 * then a dynamic menu item will be added to link 
	 * to the view.
	 * 
	 * The view will be added to the dynamic content
	 * area of MainView
	 * 
	 * @param view
	 * 	View to be displayed on screen
	 */
	public void loadNewView(IAccountsView view) {
		UUID uuid = UUID.randomUUID();
		displayMgr.put(uuid, view);
		
		if (view instanceof IAccountsSubView) {
			addSubMenu((IAccountsSubView) view, uuid);
		}
		
		setDynamicContent(view);
		
		refreshContent();
	}
	
	/**
	 * Remove any sub views from the display manager and
	 * also remove their sub menu. This will also load the
	 * parent view of the removed subview
	 * 
	 * @param view
	 * 	IAccountsSubView the view to be removed
	 */
	public void removeView(IAccountsSubView view) {
		MenuItem menuItem = menuItemLinkMgr
				.removeValue(displayMgr.removeValue(view));
		
		removeSubMenu(view, menuItem);
		
		openMainView(view.getParentType());
	}
	
	/**
	 * Any context dependent content (i.e. Localisation)
	 * of the cached views should be updated by calling
	 * his method
	 */
	public void refreshContent() {
		for (IAccountsView view : displayMgr.values()) {
			view.buildStringsFromLocale();
		}
		
		AccountsApplication.refreshMainView();
		
		// TODO: Refresh Menu Bar Text
	}
	
	/**
	 * To be called to open a STATIC view
	 * 
	 * The view will be added to the dynamic content
	 * area of MainView
	 * 
	 * @param view
	 * 	EMainView the view to be loaded
	 */
	public void openMainView(EMainView view) {
		switch (view) {
			case DASHBOARD:
				checkViewCacheAndLoad(dashboardMenuItem, view.getClassType());
				break;
			case ADMIN:
				checkViewCacheAndLoad(adminMenuItem, view.getClassType());
				break;
			case BAL_SHEET:
				checkViewCacheAndLoad(balSheetMenuItem, view.getClassType());
				break;
			case GRAPHS:
				checkViewCacheAndLoad(graphsMenuItem, view.getClassType());
				break;
			case REPORTS:
				checkViewCacheAndLoad(reportsMenuItem, view.getClassType());
				break;
		}
	}
	
	// See if the main view is already cached and grab it,
	// If not create a new instance of the main view
	void checkViewCacheAndLoad(MenuItem menu, Class<? extends IMainView> clazz) {
		UUID uuid = menuItemLinkMgr.get(menu);
		if (displayMgr.containsKey(uuid)) {
			setDynamicContent(displayMgr.get(uuid));
		} else {
			try {
				IMainView view = clazz.newInstance();
				displayMgr.put(uuid, view);
				setDynamicContent(view);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Add view to dynamic content are of MainView
	void setDynamicContent(IAccountsView view) {
		AccountsApplication.setDynamicContent(view);
	}
	
	// Add the dynamic sub view
	void addSubMenu(IAccountsSubView view, UUID uuid) {
		MenuItem parentMenu;
		EMainView mainView = view.getParentType();
		switch (mainView) {
			case ADMIN:
				parentMenu = adminMenuItem;
				break;
			case BAL_SHEET:
				parentMenu = balSheetMenuItem;
				break;
			case REPORTS:
				parentMenu = reportsMenuItem;
				break;
			case GRAPHS:
				parentMenu = graphsMenuItem;
				break;
			default:
				parentMenu = dashboardMenuItem;
		}	
		
		// Check if this is the first sub menu and proceed
		if (parentMenu.getChildren() == null) {
			parentMenu.setCommand(null);
			parentMenu.addItem("RTB", new TopLevelWindowCommand(mainView)); // TODO: Update text setting
			parentMenu.addSeparator();
		}
		
		menuItemLinkMgr.put(parentMenu.addItem(view.getDisplayName(), 
				new OpenSubViewCommand(uuid)), uuid);
	}
	
	// Remove the dynamic sub view
	void removeSubMenu(IAccountsSubView view, MenuItem menu) {
		MenuItem parentMenu;
		EMainView mainView = view.getParentType();
		switch (mainView) {
			case ADMIN:
				parentMenu = adminMenuItem;
				break;
			case BAL_SHEET:
				parentMenu = balSheetMenuItem;
				break;
			case REPORTS:
				parentMenu = reportsMenuItem;
				break;
			case GRAPHS:
				parentMenu = graphsMenuItem;
				break;
			default:
				parentMenu = dashboardMenuItem;
		}	
		
		
		// Check if this is the first sub menu and proceed
		boolean returnToRootMenu = false;
		if (parentMenu.getChildren().size() >= 3) {
			returnToRootMenu = true;
		}
		
		parentMenu.removeChild(menu);
		
		if (returnToRootMenu) {
			parentMenu.removeChildren();
			parentMenu.setCommand(new TopLevelWindowCommand(mainView));
		}
	}
	
	//******************************
	// Navigation Menu click commands
	//******************************		
	class TopLevelWindowCommand implements Command {
		private static final long serialVersionUID = 1L;

		EMainView mainView;
		
		public TopLevelWindowCommand(EMainView mainView) {
			this.mainView = mainView;
		}
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			openMainView(mainView);
		}
	}
	
	class OpenSubViewCommand implements Command {
		private static final long serialVersionUID = 1L;
		
		UUID uuid;
		
		public OpenSubViewCommand(UUID uuid) {
			this.uuid = uuid;
		}
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			setDynamicContent(displayMgr.get(menuItemLinkMgr
					.get(selectedItem)));
		}
	}
}