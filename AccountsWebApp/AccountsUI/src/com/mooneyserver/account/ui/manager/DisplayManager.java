package com.mooneyserver.account.ui.manager;

import java.util.Stack;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.iface.IAccountsView;

public class DisplayManager {
	
	private static ThreadLocal<DisplayManager> sessionInstance = 
			new ThreadLocal<>();
	
	private Stack<IAccountsView> windowStack;
			
	public static DisplayManager getDisplayManager() {
		if (sessionInstance.get() == null)
			sessionInstance.set(new DisplayManager());
		
		return sessionInstance.get();
	}
	
	// Display Manager should be session static
	private DisplayManager() {	
		windowStack = new Stack<>();
	}
	
	public void loadNewView(IAccountsView view) {
		windowStack.push(view);
		setCurrentView(windowStack.peek());
	}
	
	public void destroyCurrentView() {
		windowStack.pop();
		setCurrentView(windowStack.peek());
	}
	
	private void setCurrentView(IAccountsView view) {
		AccountsApplication.setCurrentView(view);
	}
}