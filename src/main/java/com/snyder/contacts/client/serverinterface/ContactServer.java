/**
 * 
 */
package com.snyder.contacts.client.serverinterface;

import java.util.List;

import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSummary;

/**
 * 
 * @author greg
 */
public interface ContactServer
{
	
	void getContacts(ContactSort sortField, boolean ascending, int limit, 
		Callback<List<ContactSummary>> callback, ErrorHandler errorHandler);
	
	void getContacts(long startId, ContactSort sortField, boolean ascending, int limit, 
		Callback<List<ContactSummary>> callback, ErrorHandler errorHandler);
	
	void loadContact(long id, Callback<Contact> callback, ErrorHandler errorHandler);
	
}
