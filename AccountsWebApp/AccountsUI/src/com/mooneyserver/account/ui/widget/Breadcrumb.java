package com.mooneyserver.account.ui.widget;

import java.util.Iterator;

import com.vaadin.ui.Label;

/**
 * Peeling back this implication as it's more awkward and
 * I need to think through properly about how to link to window
 * states.
 * 
 * Settings up as a purely display object for the mo.
 */
final public class Breadcrumb {

	PeekableLinkedHashSet<String> breadcrumb;
	String currScreenInBreadcrumb;
	
	public Breadcrumb() {
		breadcrumb = new PeekableLinkedHashSet<>();
	}
	
	/**
	 * To be called when you load a new view using
	 * the Display Manager. If you are not at the 
	 * end of the trail, all pages after the trail 
	 * will be removed
	 * 
	 * TODO: Need to look at unreleased window handles 
	 * on the Display Manager side
	 * 
	 * @param screenName
	 * 	Name of the new screen
	 */
	public void addScreenToBreadcrumb(String screenName) {
		if (breadcrumb.size() > 0) {
			// Check if curr Screen is last
			if (breadcrumb.peek().equalsIgnoreCase(currScreenInBreadcrumb)) {
				breadcrumb.add(screenName);
				setCurrentScreenInBreadcrumb(screenName);
			} else {
				breadcrumb.truncate(currScreenInBreadcrumb);
				breadcrumb.add(screenName);
				setCurrentScreenInBreadcrumb(screenName);
			}
		} else {
			breadcrumb.add(screenName);
			setCurrentScreenInBreadcrumb(screenName);
		}
	}
	
	/**
	 * To be called when switching to a lower
	 * element in the breadcrumb. N.B. This should
	 * not mean removal of elements ahead of
	 * current in the breadcrumb as due to the stateful
	 * nature of the app, you should be able to go fwd
	 * to these pages again.
	 * 
	 * @param screenName
	 */
	public void setCurrentScreenInBreadcrumb(String screenName) {
		if (breadcrumb.contains(screenName))
			currScreenInBreadcrumb = screenName;
	}
	
	/**
	 * 
	 * 
	 * @param screenName
	 */
	public void removeScreenFromBreadcrumb(String screenName) {
		if (!breadcrumb.contains(screenName))
			return;

		if (screenName.equals(currScreenInBreadcrumb)) {
			String newScreenName = breadcrumb.previous(screenName);
			if (newScreenName == null)
				newScreenName = breadcrumb.next(screenName);
			
			setCurrentScreenInBreadcrumb(newScreenName);
		}

		breadcrumb.remove(screenName);
	}
	
	@Override
	public String toString() {
		Iterator<String> itr = breadcrumb.iterator();
		StringBuilder sb = new StringBuilder();
		while (itr.hasNext()) {
			sb.append(itr.next() + " -> ");
		}
		
		return sb.toString();
	}
	
	public Label getCurrentDisplayLabel() {
		Label breadcrumb = new Label();
        breadcrumb.setContentMode(Label.CONTENT_XHTML);
        breadcrumb.setValue("<font face=Georgia color=grey size=4>" 
        		+ this.currScreenInBreadcrumb
        		+ "</font>");
        
        return breadcrumb;
	}
}