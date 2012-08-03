package com.mooneyserver.account.ui.widget;


import org.vaadin.addon.customfield.FieldWrapper;
import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.view.IconManager;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings({ "serial" })
public class FieldWithHelp<T> extends FieldWrapper<T> {

    ThemeResource helpIcon = IconManager.getIcon(IconManager.INFO);

    public FieldWithHelp(final Field wrappedField, Class<T> propertyType, String help) {
        super(wrappedField, null, propertyType, new HorizontalLayout());

        setCaption(wrappedField.getCaption());
        wrappedField.setCaption(null);
        wrappedField.addStyleName("fieldhelp wrappedfield");

        HorizontalLayout layout = (HorizontalLayout) getCompositionRoot();
        layout.setMargin(false);
        layout.setSpacing(true);
        layout.setSizeUndefined();

        layout.addComponent(wrappedField);

        final ContextHelp contextHelp = AccountsApplication.getHelpBubble();

        Button helpButton = new Button();
        helpButton.setStyleName(BaseTheme.BUTTON_LINK);
        helpButton.addStyleName("fieldhelp");
        helpButton.setIcon(helpIcon);
        helpButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                contextHelp.showHelpFor(event.getButton());
            }
        });

        contextHelp.addHelpForComponent(helpButton, help);

        layout.addComponent(helpButton);
        layout.setComponentAlignment(helpButton, Alignment.MIDDLE_LEFT);
        layout.setExpandRatio(helpButton, 0);
    }

    /**
     * FieldWrapper just delegates getErrorMessage to AbstractComponent. We need the logic from AbstractField.
     */
    @Override
    public ErrorMessage getErrorMessage() {
        if(getWrappedField() instanceof AbstractField) {
            return ((AbstractField)getWrappedField()).getErrorMessage();
        } else {
            return super.getErrorMessage();
        }
    }
}