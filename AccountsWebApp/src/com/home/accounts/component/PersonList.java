package com.home.accounts.component;

import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class PersonList extends Table {
	
	public PersonList() {
        // create some dummy data
        addContainerProperty("Debit Id", String.class, "Groceries");
        addContainerProperty("Debit Amount", Double.class, new Double(203.50));
        addItem();
        addItem();
        setSizeFull();
    }

}
