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
import com.snyder.state.list.HasListEvents;
import com.snyder.state.nonnull.BaseMutableNonNullState;
import com.snyder.state.nonnull.MutableNonNullState;
import com.snyder.state.store.BaseStore;
import com.snyder.state.store.OrderedStoreView;

/**
 * 
 * @author greg
 */
public class ContactListingViewModelImpl implements ContactListingViewModel
{
	
	private static final int LOAD_LIMIT = 100;

	private final ContactServer server;
	private final MutableNonNullState<ContactSort> contactSort = 
		new BaseMutableNonNullState<ContactSort>(ContactSort.FIRST_NAME);
	private final MutableNonNullState<Boolean> ascending = 
		new BaseMutableNonNullState<Boolean>(Boolean.TRUE);
	private final BaseStore<ContactSummary, Integer> contactStore = 
		new BaseStore<ContactSummary, Integer>(new ContactIdFunction());
	private final OrderedStoreView<ContactSummary> orderedContacts = 
		new OrderedStoreView<ContactSummary>(contactStore, new AscendingNameComparator());
	
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
	public HasListEvents<ContactSummary> getContactListing()
	{
	    return orderedContacts;
	}

	@Override
	public void loadMore()
	{
		server.getContacts(contactStore.getView().size(), contactSort.get(), ascending.get(), 
			LOAD_LIMIT, new StoreLoadCB(), null);
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
	        return o1.getDisplayName().compareTo(o2.getDisplayName());
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
	        return o2.getDisplayName().compareTo(o1.getDisplayName());
        }
		
	}
	
}
