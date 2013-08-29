package com.snyder.contacts.client.view.displaycontact;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.snyder.contacts.client.viewmodel.displaycontact.DisplayContactViewModel;
import com.snyder.contacts.shared.model.Contact;


public class DisplayContactView implements IsWidget
{
    
    private final DisplayContactViewModel viewModel;
    private final FlowLayoutContainer view = new FlowLayoutContainer();

    public DisplayContactView(DisplayContactViewModel viewModel)
    {
        this.viewModel = viewModel;
        
        // TODO Set up the containers, labels, etc into which data will be inserted
        
        this.renderContact(viewModel.getDisplayedContact());
    }

    @Override
    public Widget asWidget()
    {
        return view;
    }
    
    private void renderContact(Contact contact)
    {
        // TODO Update the containers, labels, etc with the new contact data
    }
    
    private class EditSelectHandler implements SelectHandler
    {

        @Override
        public void onSelect(SelectEvent event)
        {
            viewModel.edit();
        }
        
    }
    
}
