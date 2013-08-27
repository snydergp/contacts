/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import org.jooq.DSLContext;

import com.snyder.contacts.shared.model.PhoneNumber;

/**
 * 
 * @author greg
 */
public interface PhoneNumberData
{

	List<PhoneNumber> getPhoneNumbersForContact(DSLContext context, int contactId);
	
	void insertPhoneNumbersForContact(DSLContext context, int contactId, 
		List<PhoneNumber> phoneNumbers);
	
	void clearPhoneNumbersForContact(DSLContext context, int contactId);
	
}
