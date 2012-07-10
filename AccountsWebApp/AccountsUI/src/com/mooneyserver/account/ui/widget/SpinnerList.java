package com.mooneyserver.account.ui.widget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.BaseTheme;


public class SpinnerList extends CustomComponent implements Button.ClickListener {

	private static final long serialVersionUID = -5523834701106113291L;
	
	private AbsoluteLayout mainLayout;
    private Button downButton;
    private Button upButton;
    private TextField nextOptionBelow;
    private TextField nextOptionAbove;
    private Label currSelection;
    
    private ThemeResource ARROW_UP = new ThemeResource("../runo/icons/16/arrow-up.png");
    private ThemeResource ARROW_DOWN = new ThemeResource("../runo/icons/16/arrow-down.png");
    
    private final String BUTTON_UP = "button_up";
    private final String BUTTON_DOWN = "button_down";
    
    private Map<Integer, SpinnerListItem> items;
    private List<SpinnerListItem> viewableTriplet = new ArrayList<>();
    
    private class SpinnerListItem {
    	String itemValue;
    	ThemeResource itemIcon;
		
    	public SpinnerListItem(String value, ThemeResource icon) {
    		this.itemValue = value;
    		this.itemIcon = icon;
    	}

    	public String getItemValue() {
			return itemValue;
		}
		
		public ThemeResource getItemIcon() {
			return itemIcon;
		}
		
		@Override
		public String toString() {
			return "[" + itemValue + "::" + itemIcon.toString() + "]";
		}
    }

    /*
     * Constructor
     */
    public SpinnerList() {
        buildMainLayout();
        setCompositionRoot(mainLayout);
        setImmediate(true);
        
        items = new LinkedHashMap<>();
    }

    private AbsoluteLayout buildMainLayout() {
        // common part: create layout
        mainLayout = new AbsoluteLayout();
        mainLayout.setImmediate(false);
        mainLayout.setWidth("160px");
        mainLayout.setHeight("69px");
        mainLayout.setMargin(false);
        
        // top-level component properties
        setWidth("160px");
        setHeight("69px");
        
        // currSelection
        currSelection = new Label();
        currSelection.setImmediate(false);
        currSelection.setWidth("2px");
        currSelection.setHeight("2px");
        currSelection.setValue("");
        currSelection.setContentMode(3);
        mainLayout.addComponent(currSelection, "top:40.0px;left:6.0px;");
        
        // nextOptionAbove
        nextOptionAbove = new TextField();
        nextOptionAbove.addStyleName("borderless");
        nextOptionAbove.setEnabled(false);
        nextOptionAbove.setImmediate(false);
        nextOptionAbove.setWidth("-1px");
        nextOptionAbove.setHeight("-1px");
        mainLayout.addComponent(nextOptionAbove, "top:0.0px;left:3.0px;");
        
        // nextOptionBelow
        nextOptionBelow = new TextField();
        nextOptionBelow.addStyleName("borderless");
        nextOptionBelow.setEnabled(false);
        nextOptionBelow.setImmediate(false);
        nextOptionBelow.setWidth("-1px");
        nextOptionBelow.setHeight("-1px");
        mainLayout.addComponent(nextOptionBelow, "top:40.0px;left:3.0px;");
        
        // upButton
        upButton = new Button();
        upButton.setCaption("");
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setImmediate(true);
        upButton.setDescription("Next Language (Up)");
        upButton.setWidth("16px");
        upButton.setHeight("16px");
        upButton.setIcon(ARROW_UP);
        upButton.addListener(this);
        upButton.setData(BUTTON_UP);
        mainLayout.addComponent(upButton, "top:11.0px;left:139.0px;");
        
        // downButton
        downButton = new Button();
        downButton.setCaption("");
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setImmediate(true);
        downButton.setDescription("Next Language (Down)");
        downButton.setWidth("16px");
        downButton.setHeight("16px");
        downButton.setIcon(ARROW_DOWN);
        downButton.addListener(this);
        downButton.setData(BUTTON_DOWN);
        mainLayout.addComponent(downButton, "top:39.0px;left:139.0px;");
        
        return mainLayout;
    }
    
    public void addItem(String value) {
    	addItem(value, null);
    }
    
    public void addItem(String value, ThemeResource icon) {
    	items.put(items.size() + 1, new SpinnerListItem(value, icon));
    	
    	viewableTriplet = new ArrayList<>();
    	setSelectedItem(value);
    }
    
    public void setSelectedItem(String value) {
    	SpinnerListItem item = getItemByValue(value);
    	if (item == null)
    		throw new IllegalArgumentException(value 
    				+ " is not currently a SpinnerList item");
    	
    	updateItemsTriplet(value);
    	
    	currSelection.setCaption(viewableTriplet.get(1).getItemValue());
    	if (viewableTriplet.get(1).getItemIcon() != null)
    		currSelection.setIcon(viewableTriplet.get(1).getItemIcon());
    		
    	nextOptionAbove.setInputPrompt(viewableTriplet.get(0).getItemValue());
        nextOptionBelow.setInputPrompt(viewableTriplet.get(2).getItemValue());
        
        this.requestRepaint();
    }
    
    private SpinnerListItem getItemByValue(String value) {
    	for (SpinnerListItem item : items.values()) {
    		if (item.getItemValue().equalsIgnoreCase(value))
    			return item;
    	}
    	
    	return null;
    }
    
    private void updateItemsTriplet(String value) {
    	int pos = -1;
    	for (int keyPos : items.keySet()) {
    		if (items.get(keyPos).getItemValue().equalsIgnoreCase(value))
    			pos = keyPos;
    	}
    	
    	int current = pos;
    	int above = ((current - 1) > 0) ? current - 1 : items.size();
    	int below = ((current + 1) <= items.size()) ? current + 1 : 1;
    	
    	viewableTriplet.add(items.get(above));
    	viewableTriplet.add(items.get(current));
    	viewableTriplet.add(items.get(below));
    	
    	System.out.println("viewableTriplet updated: [" + viewableTriplet + "]");
    }

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton().getData().equals(BUTTON_UP)) {
			System.out.println("Call setSelect ["+viewableTriplet.get(0).getItemValue()+"]");
			setSelectedItem(viewableTriplet.get(0).getItemValue());
		} else if (event.getButton().getData().equals(BUTTON_DOWN)) {
			System.out.println("Call setSelect ["+viewableTriplet.get(2).getItemValue()+"]");
			setSelectedItem(viewableTriplet.get(2).getItemValue());
		}
	}
}