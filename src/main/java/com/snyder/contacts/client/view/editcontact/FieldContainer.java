package com.snyder.contacts.client.view.editcontact;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

public class FieldContainer implements IsWidget
{
    
    private static final FieldContainer.Resources RESOURCES = GWT.create(FieldContainer.Resources.class);
    
    public static interface Resources extends ClientBundle
    {
        
        @Source("FieldContainer.css")
        Css css();
        
    }
    
    public static interface Css extends CssResource
    {
        
        String row();
        
        String label();
        
        String field();
        
    }
    
    private final FlowLayoutContainer view = new FlowLayoutContainer();

    @Override
    public Widget asWidget()
    {
        return view;
    }
    
    public void addField(String labelText, Widget field)
    {
        FlowLayoutContainer row = new FlowLayoutContainer();
        row.addStyleName(RESOURCES.css().field());
        
        field.addStyleName(RESOURCES.css().field());
        row.add(field);
        
        Label label = new Label(labelText);
        label.addStyleName(RESOURCES.css().label());
        row.add(label);
    }
    
}