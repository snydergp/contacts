/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import org.jooq.DSLContext;

import com.snyder.contacts.model.EmailAddress;

/**
 * 
 * @author greg
 */
public interface EmailData
{

	List<EmailAddress> getEmailAddressesForContactId(DSLContext context, int contactId);

	void insertEmailAddressesForContactId(DSLContext context, int contactId, 
		List<EmailAddress> addresses);

	void clearEmailAddressesForContactId(DSLContext context, int contactId);
	
}
