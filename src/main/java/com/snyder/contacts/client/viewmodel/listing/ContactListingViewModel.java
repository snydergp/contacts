/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.state.list.OrderedStore;
import com.snyder.state.nonnull.MutableNonNullState;

/**
 * 
 * @author greg
 */
public interface ContactListingViewModel
{
	
	MutableNonNullState<ContactSort> getSortState();

	OrderedStore<ContactSummaryViewModel> getContactListing();
	
}
