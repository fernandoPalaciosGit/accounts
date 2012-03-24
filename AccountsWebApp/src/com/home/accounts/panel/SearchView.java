package com.home.accounts.panel;

import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class SearchView extends Panel {
	
	private static SearchView me = null;
	
	private SearchView() {
        setCaption("Search contacts");
        setSizeFull();
    }
	
	public static SearchView getInstance() {
		if (me != null)
			return me;
		else
			return new SearchView();
	}

}
