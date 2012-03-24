package com.home.accounts.component;

import com.home.accounts.form.PersonForm;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
public class ListView extends VerticalSplitPanel {
	
	private static ListView me = null;
	
	private ListView() {
        this(new PersonList(), new PersonForm());
	}
	
	private ListView(PersonList personList, PersonForm personForm) {
         setFirstComponent(personList);
         setSecondComponent(personForm);
         setSplitPosition(40);
    }
	
	public static ListView getInstance() {
		if (me != null)
			return me;
		else
			return new ListView();
	}

}
