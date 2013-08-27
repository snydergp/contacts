/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.state.list.HasListEvents;
import com.snyder.state.nonnull.MutableNonNullState;

/**
 * 
 * @author greg
 */
public interface ContactListingViewModel
{
	
	MutableNonNullState<ContactSort> getSortState();

	HasListEvents<ContactSummary> getContactListing();
	
	void loadMore();
	
}
