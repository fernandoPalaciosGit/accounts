package com.home.accounts.panel;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class InsertSidePanel extends SidePanelGeneric {
	
	public InsertSidePanel() {
		super();
		setContent();
	}

	@Override
	public void setContent() {
		Button creditBtn = new Button("Money In");
		creditBtn.setStyleName(BaseTheme.BUTTON_LINK);
	    
	    Button debitBtn = new Button("Money Out");
	    debitBtn.setStyleName(BaseTheme.BUTTON_LINK);	
	    
	    super.panel.addComponent(creditBtn);
	    super.panel.addComponent(debitBtn);

	}

}
