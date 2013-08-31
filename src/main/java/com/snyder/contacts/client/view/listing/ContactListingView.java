package com.snyder.contacts.client.view.listing;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.snyder.contacts.client.viewmodel.listing.ContactListingViewModel;
import com.snyder.contacts.client.viewmodel.listing.ContactSummaryViewModel;
import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.gxtstate.ContainerSynchronizer;
import com.snyder.state.State;
import com.snyder.state.StateObserver;
import com.snyder.state.example.ShiftingViewWindow;
import com.snyder.state.util.Mapping;


/**
 * The view that displays the listing of stored contacts
 * 
 * @author SnyderGP
 */
public class ContactListingView implements IsWidget
{
    private static final int CONTACT_HEIGHT = 50;
    
    private final FlowLayoutContainer view = new FlowLayoutContainer();
    
    /**
     * Creates a {@link ContactListingView}.
     * 
     * @param viewModel the viewModel this view represents
     * @param containerHeight a state representing the height of the container of this view
     * @param scrollOffset a state representing the top scroll offset of the container of this view
     */
    public ContactListingView(ContactListingViewModel viewModel, State<Integer> containerHeight, 
        State<Integer> scrollOffset)
    {
        // Create the shifting view window object, which tracks which items should be rendered
        //  based on the scroll position of the content
        ShiftingViewWindow<ContactSummaryViewModel> window = 
            new ShiftingViewWindow<ContactSummaryViewModel>(viewModel.getContactListing(), 
                CONTACT_HEIGHT, containerHeight, scrollOffset);
        
        // Synchronize the view's top margin to the window's upperMargin state
        window.getUpperMargin().addObserver(new UpperMarginObserver(), true);
        
        // Synchronize the view's bottom margin to the window's lowerMargin state
        window.getLowerMargin().addObserver(new LowerMarginObserver(), true);
        
        // Synchronize the view's rendered items to the window's visibleItems store
        ContainerSynchronizer.sync(window.getVisibleItems(), new ViewMapper(), view);
    }
    
    @Override
    public Widget asWidget()
    {
        return view;
    }
    
    private class UpperMarginObserver implements StateObserver<Integer>
    {

        @Override
        public void
            stateChanged(State<? extends Integer> state, Integer oldValue, Integer newValue)
        {
            view.getElement().getStyle().setMarginTop(newValue.doubleValue(), Unit.PX);
        }
        
    }
    
    private class LowerMarginObserver implements StateObserver<Integer>
    {

        @Override
        public void
            stateChanged(State<? extends Integer> state, Integer oldValue, Integer newValue)
        {
            view.getElement().getStyle().setMarginBottom(newValue.doubleValue(), Unit.PX);
        }
        
    }
    
    private static class ViewMapper implements Mapping<ContactSummaryViewModel, ContactView>
    {

        @Override
        public ContactView map(ContactSummaryViewModel viewModel)
        {
            return new ContactView(viewModel);
        }
        
    }

    public static class ContactView implements IsWidget
    {
        
        private final FlowLayoutContainer view = new FlowLayoutContainer();

        public ContactView(ContactSummaryViewModel viewModel)
        {
            this.view.add(new Label(viewModel.getSummary().toDisplay(ContactSort.FIRST_NAME)));
            
            this.view.setHeight(45);
            this.view.getElement().getStyle().setMarginBottom(5, Unit.PX);
        }

        @Override
        public Widget asWidget()
        {
            return view;
        }
        
    }
    
}
