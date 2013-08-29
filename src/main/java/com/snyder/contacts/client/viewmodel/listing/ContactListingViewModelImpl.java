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
	private final BaseStore<ContactSummary, Integer> contactStore = 
		new BaseStore<ContactSummary, Integer>(new ContactIdFunction());
	private final StoreOrderer<ContactSummary> orderedContacts = 
		new StoreOrderer<ContactSummary>(contactStore, new AscendingNameComparator());
	
	public ContactListingViewModelImpl(ContactServer server)
	{
		this.server = server;
		
		contactSort.addObserver(new ContactSortObserver());
		ascending.addObserver(new SortDirectionObserver());
		
		// Do initial load
		loadMore();
	}
	
	@Override
	public MutableNonNullState<ContactSort> getSortState()
	{
	    return contactSort;
	}

	@Override
	public OrderedStoreView<ContactSummary> getContactListing()
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
	        contactStore.add(value);
        }
		
	}
	
	/**
	 * Maps a ContactSummary to its id
	 */
	private class ContactIdFunction implements Function<ContactSummary, Integer>
	{

		@Override
        public Integer apply(ContactSummary input)
        {
	        return input.getId();
        }
		
	}
	
	/**
	 * Observes the ascending/descending sort state for changes, updating the selected comparator
	 * and reloading the store
	 */
	private class SortDirectionObserver implements StateObserver<Boolean>
	{

		@Override
        public void stateChanged(State<? extends Boolean> state, Boolean oldValue, Boolean newValue)
        {
			// Clear the store
			contactStore.clear();
			
			// Switch the sort of displayed items
			orderedContacts.setComparator(newValue ? new AscendingNameComparator() : 
				new DescendingNameComparator());
			
	        // Reload
			loadMore();
        }
		
	}
	
	/**
	 * Observes the {@link ContactSort} state for changes and reloads the store
	 */
	private class ContactSortObserver implements StateObserver<ContactSort>
	{

		@Override
        public void stateChanged(State<? extends ContactSort> state, ContactSort oldValue,
            ContactSort newValue)
        {
			// Clear the store
			contactStore.clear();

			// Reload
			loadMore();
        }
		
	}
	
	/**
	 * Sorts {@link ContactSummary} instances in ascending alphabetical order by display name
	 */
	private class AscendingNameComparator implements Comparator<ContactSummary>
	{

		@Override
        public int compare(ContactSummary o1, ContactSummary o2)
        {
		    ContactSort currentSort = contactSort.get();
	        return o1.toDisplay(currentSort).compareTo(o2.toDisplay(currentSort));
        }
		
	}
	
	/**
	 * Sorts {@link ContactSummary} instances in descending alphabetical order by display name
	 */
	private class DescendingNameComparator implements Comparator<ContactSummary>
	{

		@Override
        public int compare(ContactSummary o1, ContactSummary o2)
        {
            ContactSort currentSort = contactSort.get();
            return o2.toDisplay(currentSort).compareTo(o1.toDisplay(currentSort));
        }
		
	}
	
}
