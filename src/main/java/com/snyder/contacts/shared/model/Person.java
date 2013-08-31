package com.snyder.contacts.shared.model;

/**
 * Extends {@link ContactSummary} with additional contact information pertaining to people
 * 
 * @author greg
 */
public interface Person extends Contact
{

	/**
	 * @return this person's title
	 */
	Title getTitle();

	/**
	 * @return the first name
	 */
	String getFirstName();

	/**
	 * @return the middle intial
	 */
	String getMiddleInitial();

	/**
	 * @return the last name
	 */
	String getLastName();
}
