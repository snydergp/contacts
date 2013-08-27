/**
 * 
 */
package com.snyder.contacts.client.serverinterface;

import java.util.List;

import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.contacts.shared.model.ContactSummary;

/**
 * 
 * @author greg
 */
public interface ContactServer
{
	
	void getContacts(ContactSort sortField, boolean ascending, int limit, 
		Callback<List<ContactSummary>> callback, ErrorHandler errorHandler);
	
	void getContacts(int startId, ContactSort sortField, boolean ascending, int limit, 
		Callback<List<ContactSummary>> callback, ErrorHandler errorHandler);
    
    void createContact(Contact contact, Callback<Integer> callback, ErrorHandler errorHandler);
    
    void getContact(int id, Callback<Contact> callback, ErrorHandler errorHandler);
    
    void updateContact(Contact contact, Callback<Void> callback, ErrorHandler errorHandler);
    
    void deleteContact(int id, Callback<Void> callback, ErrorHandler errorHandler);
	
}
