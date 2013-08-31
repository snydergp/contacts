/**
 * 
 */
package com.snyder.contacts.shared.model;

import java.util.List;

/**
 * Supertype representing a contact with linked address, phone number, and/or email info
 * 
 * @author greg
 */
public interface Contact
{

	/**
	 * The ID used to indicate a new contact
	 */
	public static final int NEW_CONTACT_ID = -1;

	/**
	 * @return the ID of this contact
	 */
	int getId();

	/**
	 * @return Free-form text info pertaining to this contact
	 */
	String getInfo();

	/**
	 * @return a list of addresses associated with this contact
	 */
	List<Address> getAddresses();

	/**
	 * @return a list of phone numbers associated with this contact
	 */
	List<PhoneNumber> getPhoneNumbers();

	/**
	 * @return a list of email addresses associated with this contact
	 */
	List<EmailAddress> getEmailAddresses();

}
