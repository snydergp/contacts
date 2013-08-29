/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import java.util.Comparator;

import com.snyder.contacts.client.viewmodel.ContactSelection;
import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.state.BaseMutableState;
import com.snyder.state.MutableState;
import com.snyder.state.State;
import com.snyder.state.StateObserver;
import com.snyder.state.list.OrderedStore;
import com.snyder.state.nonnull.BaseMutableNonNullState;
import com.snyder.state.nonnull.MutableNonNullState;
import com.snyder.state.store.MappingStore;
import com.snyder.state.store.StoreMapping;
import com.snyder.state.store.StoreOrderer;
import com.snyder.state.store.Store;
import com.snyder.state.util.Mapping;

/**
 * 
 * @author greg
 */
public class ContactListingViewModelImpl implements ContactListingViewModel
{

    private final Store<ContactSummary> store;
	private final ContactSelection selection;
	private final MutableNonNullState<ContactSort> contactSort = 
		new BaseMutableNonNullState<ContactSort>(ContactSort.FIRST_NAME);
	private final MutableNonNullState<Boolean> ascending = 
		new BaseMutableNonNullState<Boolean>(Boolean.TRUE);
	private final Store<ContactSummaryViewModel> viewModelStore;
	private final StoreOrderer<ContactSummaryViewModel> orderedContacts;
	private final Mapping<Integer, ContactSummaryViewModel> contactSummaryMapping;
	
	public ContactListingViewModelImpl(Store<ContactSummary> store, ContactSelection selection)
	{
	 
	    this.store = store;
		this.selection = selection;
		
		viewModelStore = new MappingStore<ContactSummary, ContactSummaryViewModel>(store, 
		    new ViewModelMapping());
		orderedContacts = new StoreOrderer<ContactSummaryViewModel>(viewModelStore, 
		    new AscendingNameComparator());
		contactSummaryMapping = new StoreMapping<Integer, ContactSummaryViewModel>(viewModelStore, 
		    new IdMapping());
		
		selection.getSelectedContactId().addObserver(new SelectionObserver());
		
		SortChangeObserver sortObserver = new SortChangeObserver();
		contactSort.addObserver(sortObserver);
		ascending.addObserver(sortObserver);
	}
	
	@Override
	public MutableNonNullState<ContactSort> getSortState()
	{
	    return contactSort;
	}

	@Override
	public OrderedStore<ContactSummaryViewModel> getContactListing()
	{
	    return orderedContacts;
	}
	
	private class IdMapping implements Mapping<ContactSummaryViewModel, Integer>
	{

        @Override
        public Integer map(ContactSummaryViewModel t)
        {
            return t.getSummary().getId();
        }
	    
	}
	
	private class ViewModelMapping implements Mapping<ContactSummary, ContactSummaryViewModel>
	{

        @Override
        public ContactSummaryViewModel map(ContactSummary t)
        {
            return new ContactSummaryViewModelImpl(t);
        }
	    
	}
	
	/**
	 * Observes the ascending/descending sort state for changes, updating the selected comparator
	 * and reloading the store
	 */
	private class SortChangeObserver implements StateObserver<Object>
	{

		@Override
        public void stateChanged(State<? extends Object> state, Object oldValue, Object newValue)
        {
		    // Get the appropriate comparator
		    Comparator<ContactSummaryViewModel> comparator = ascending.get() ? 
		        new AscendingNameComparator() : new DescendingNameComparator();
		    
			// Switch the sort of displayed items
			orderedContacts.setComparator(comparator);
        }
		
	}
	
	/**
	 * Sorts {@link ContactSummary} instances in ascending alphabetical order by display name
	 */
	private class AscendingNameComparator implements Comparator<ContactSummaryViewModel>
	{

		@Override
        public int compare(ContactSummaryViewModel o1, ContactSummaryViewModel o2)
        {
		    ContactSort currentSort = contactSort.get();
	        return o1.getSummary().toDisplay(currentSort).compareTo(
	            o2.getSummary().toDisplay(currentSort));
        }
		
	}
	
	/**
	 * Sorts {@link ContactSummary} instances in descending alphabetical order by display name
	 */
	private class DescendingNameComparator implements Comparator<ContactSummaryViewModel>
	{

		@Override
        public int compare(ContactSummaryViewModel o1, ContactSummaryViewModel o2)
        {
            ContactSort currentSort = contactSort.get();
            return o2.getSummary().toDisplay(currentSort).compareTo(
                o1.getSummary().toDisplay(currentSort));
        }
		
	}
	
	private class SelectionObserver implements StateObserver<Integer>
	{

        @Override
        public void
            stateChanged(State<? extends Integer> state, Integer oldValue, Integer newValue)
        {
            if(oldValue != null)
            {
                ContactSummaryViewModel viewModel = contactSummaryMapping.map(oldValue);
                if(viewModel != null)
                {
                    ((ContactSummaryViewModelImpl) viewModel).selected.set(false);
                }
            }
            if(newValue != null)
            {
                ContactSummaryViewModel viewModel = contactSummaryMapping.map(newValue);
                if(viewModel != null)
                {
                    ((ContactSummaryViewModelImpl) viewModel).selected.set(true);
                }
            }
        }
	    
	}
	
	private class ContactSummaryViewModelImpl implements ContactSummaryViewModel
	{
	    
	    private final ContactSummary summary;
	    private final MutableState<Boolean> selected = new BaseMutableState<Boolean>(Boolean.FALSE);

        public ContactSummaryViewModelImpl(ContactSummary summary)
        {
            this.summary = summary;
            
            // If this view model represents the current selection, set the selected state
            if(Integer.valueOf(summary.getId()).equals(selection.getSelectedContactId()))
            {
                selected.set(Boolean.TRUE);
            }
        }

        @Override
        public ContactSummary getSummary()
        {
            return summary;
        }

        @Override
        public void view()
        {
            selection.displayContact(summary.getId());
        }

        @Override
        public State<Boolean> isSelected()
        {
            return selected;
        }
	    
	}
	
}
