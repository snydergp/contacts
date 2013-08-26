/**
 * 
 */
package com.snyder.contacts.model;

/**
 * Summarizes a contact
 * 
 * @author greg
 */
public interface ContactSummary
{
	
	int getId();
	
	String getDisplayName();
	
	ContactType getType();
	
}
