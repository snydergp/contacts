/**
 * 
 */
package com.snyder.contacts.client.viewmodel.listing;

import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.state.State;

/**
 * 
 * @author greg
 */
public interface ContactSummaryViewModel
{

	ContactSummary getSummary();
	
	State<Boolean> isSelected();
	
	void view();
	
}
