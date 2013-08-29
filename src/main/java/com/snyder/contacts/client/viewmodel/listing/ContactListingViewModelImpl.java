/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import java.util.Comparator;
import java.util.List;

import com.google.common.base.Function;
import com.snyder.contacts.client.serverinterface.Callback;
import com.snyder.contacts.client.serverinterface.ContactServer;
import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.state.State;
import com.snyder.state.StateObserver;
import com.snyder.state.list.OrderedStoreView;
import com.snyder.state.nonnull.BaseMutableNonNullState;
import com.snyder.state.nonnull.MutableNonNullState;
import com.snyder.state.store.BaseStore;
import com.snyder.state.store.StoreOrderer;

/**
 * 
 * @author greg
 */
public class ContactListingViewModelImpl implements ContactListingViewModel
{

	private final ContactServer server;
	private final MutableNonNullState<ContactSort> contactSort = 
		new BaseMutableNonNullState<ContactSort>(ContactSort.FIRST_NAME);
	private final MutableNonNullState<Boolean> ascending = 
		new BaseMutableNonNullState<Boolean>(Boolean.TRUE);
	private final BaseStore<ContactSummaryViewModel, Integer> contactStore = 
		new BaseStore<ContactSummaryViewModel, Integer>(new ContactIdFunction());
	private final StoreOrderer<ContactSummaryViewModel> orderedContacts = 
		new StoreOrderer<ContactSummaryViewModel>(contactStore, new AscendingNameComparator());
	
	public ContactListingViewModelImpl(ContactServer server)
	{
		this.server = server;
		
		SortChangeObserver sortObserver = new SortChangeObserver();
		contactSort.addObserver(sortObserver);
		ascending.addObserver(sortObserver);
		
		// Do initial load
        server.getContacts(new StoreLoadCB(), null);
	}
	
	@Override
	public MutableNonNullState<ContactSort> getSortState()
	{
	    return contactSort;
	}

	@Override
	public OrderedStoreView<ContactSummaryViewModel> getContactListing()
	{
	    return orderedContacts;
	}

	@Override
	public void loadMore()
	{
		server.getContacts(new StoreLoadCB(), null);
	}

	/**
	 * Callback used to load data into a store
	 */
	private class StoreLoadCB implements Callback<List<ContactSummary>>
	{

		@Override
        public void onSuccess(List<ContactSummary> value)
        {
	        // TODO contactStore.add(new Covalue);
        }
		
	}
	
	/**
	 * Maps a ContactSummary to its id
	 */
	private class ContactIdFunction implements Function<ContactSummaryViewModel, Integer>
	{

		@Override
        public Integer apply(ContactSummaryViewModel input)
        {
	        return input.getSummary().getId();
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
	
}
