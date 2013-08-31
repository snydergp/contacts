/**
 * 
 */
package com.snyder.contacts.shared.model;

/**
 * Summarizes a contact
 * 
 * @author greg
 */
public interface ContactSummary
{

	int getId();

	String getFirstName();

	String getMiddleInitial();

	String getLastName();

	ContactType getType();

	String toDisplay(ContactSort sort);

}
