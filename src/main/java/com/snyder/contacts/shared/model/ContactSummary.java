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
	
	String getDisplayName();
	
	ContactType getType();
	
}
