/**
 * 
 */
package com.snyder.contacts.client.serverinterface;

import java.util.List;

import com.snyder.contacts.model.PersonInfo;

/**
 * 
 * @author greg
 */
public interface ContactServer
{
	
	void getContacts(ContactSortableField sortField, boolean ascending, int limit, 
		Callback<List<PersonInfo>> callback, ErrorHandler errorHandler);
	
	void getContacts(long startId, ContactSortableField sortField, boolean ascending, int limit, 
		Callback<List<PersonInfo>> callback, ErrorHandler errorHandler);
	
}
