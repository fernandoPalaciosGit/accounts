package com.home.accounts.panel;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public abstract class SidePanelGeneric extends VerticalLayout {
	
	protected Panel panel;
	
	public SidePanelGeneric() {
        setSpacing(true);

        // Panel 1 - with caption
        panel = new Panel("");
        panel.setCaption(null);
        panel.setStyleName(Reindeer.PANEL_LIGHT);

        // let's adjust the panels default layout (a VerticalLayout)
        VerticalLayout layout = (VerticalLayout) panel.getContent();
        layout.setMargin(true); // we want a margin
        layout.setSpacing(true); // and spacing between components
        addComponent(panel);
    }
	
	public abstract void setContent();

}
