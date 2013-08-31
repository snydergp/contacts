package com.snyder.contacts.server.data;

import org.jooq.DSLContext;

import com.snyder.contacts.shared.model.Contact;

public interface ContactSubtypeData<T extends Contact>
{

	/**
	 * Tests whether a contact instance is of this subtype
	 * 
	 * @param contact
	 *            the contact
	 * @return true if the contact is of this module's subtype
	 */
	boolean isSubtypeInstance(Contact contact);

	/**
	 * Inserts a subtype record
	 * 
	 * @param context
	 *            the context through which the query should be executed
	 * @param contact
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 *             when <code>isSubtypeInstance(contact) == false</code>
	 */
	void insertSubtype(DSLContext context, Contact contact);

	/**
	 * Gets a subtype record (without contact fields populated)
	 * 
	 * @param context
	 *            the context through which the query should be executed
	 * @param contactId
	 * @return
	 */
	T getSubtype(DSLContext context, int contactId);

	/**
	 * Deletes a subtype record (but not the parent CONTACT record)
	 * 
	 * @param context
	 *            the context through which the query should be executed
	 * @param contactId
	 * @return
	 */
	void deleteSubtype(DSLContext context, int contactId);

}
