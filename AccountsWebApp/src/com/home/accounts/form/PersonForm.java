package com.home.accounts.form;

import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class PersonForm extends Form {
	
	private Button insert = new Button("Insert");
    private Button cancel = new Button("Cancel");
    
    public PersonForm() {
        addField("Debit Id", new TextField("Debit Id"));
        addField("Debit Amount", new TextField("Debit Amount"));
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addComponent(this.insert);
        footer.addComponent(this.cancel);
        setFooter(footer);
    }

}
