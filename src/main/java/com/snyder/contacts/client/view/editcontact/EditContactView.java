package com.snyder.contacts.client.view.editcontact;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.snyder.contacts.client.viewmodel.editcontact.EditContactViewModel;


public class EditContactView implements IsWidget
{
    
    private final EditContactViewModel viewModel;
    private final FlowLayoutContainer view = new FlowLayoutContainer();
    
    public EditContactView(EditContactViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @Override
    public Widget asWidget()
    {
        return view;
    }
    
}
