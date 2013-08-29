package com.snyder.contacts.client.view.listing;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.snyder.contacts.client.view.displaycontact.DisplayContactView;
import com.snyder.contacts.client.view.editcontact.EditContactView;
import com.snyder.contacts.client.viewmodel.MainViewModel;
import com.snyder.contacts.client.viewmodel.MainViewModel.DisplayMode;
import com.snyder.gxtstate.HeightState;
import com.snyder.gxtstate.TopScrollState;
import com.snyder.state.State;
import com.snyder.state.StateObserver;


public class MainView implements IsWidget
{
    
    private final MainViewModel viewModel;
    private final FlowLayoutContainer view = new FlowLayoutContainer();
    private final FlowLayoutContainer content = new FlowLayoutContainer();
    
    public MainView(MainViewModel viewModel)
    {
        this.viewModel = viewModel;
        
        ContactListingView listingView = new ContactListingView(viewModel.getListingViewModel(),
            new HeightState(view), new TopScrollState(view));
        listingView.asWidget().setWidth("200px");
        view.add(listingView);
        
        Style contentStyle = content.getElement().getStyle();
        contentStyle.setPosition(Position.FIXED);
        contentStyle.setLeft(210, Unit.PX);
        contentStyle.setTop(0, Unit.PX);
        view.add(content);
        
        viewModel.getDisplayMode().addObserver(new DisplayModeObserver(), true);
    }

    @Override
    public Widget asWidget()
    {
        return view;
    }
    
    private class DisplayModeObserver implements StateObserver<DisplayMode>
    {

        @Override
        public void stateChanged(State<? extends DisplayMode> state, DisplayMode oldValue,
            DisplayMode newValue)
        {
            content.clear();
            switch(newValue)
            {
                case DISPLAY:
                    content.add(new DisplayContactView(viewModel.getDisplayViewModel()));
                    break;
                case EDIT:
                    content.add(new EditContactView(viewModel.getEditViewModel()));
                    break;
                case NONE:
                    // no content
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        
    }
    
}
