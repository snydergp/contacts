/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.state.list.OrderedStoreView;
import com.snyder.state.nonnull.MutableNonNullState;

/**
 * 
 * @author greg
 */
public interface ContactListingViewModel
{
	
	MutableNonNullState<ContactSort> getSortState();

	OrderedStoreView<ContactSummaryViewModel> getContactListing();
	
	void loadMore();
	
}
