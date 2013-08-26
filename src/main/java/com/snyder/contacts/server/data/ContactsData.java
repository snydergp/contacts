/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import com.snyder.contacts.client.serverinterface.ContactSort;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSummary;

/**
 * 
 * @author greg
 */
public interface ContactsData
{
	
	int createContact(Contact contact);
	
	void updateContact(Contact contact);
	
	void deleteContact(int id);

	Contact getContactById(int id);
	
	List<ContactSummary> getContacts(ContactSort field, int startingAtId, int limit);
	
}
