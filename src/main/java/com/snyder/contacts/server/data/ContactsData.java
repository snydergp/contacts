/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactSummary;

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

	List<ContactSummary> getContacts();

}
