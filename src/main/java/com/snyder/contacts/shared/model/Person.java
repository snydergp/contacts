package com.snyder.contacts.shared.model;



/**
 * Extends {@link ContactSummary} with additional contact information
 * 
 * @author greg
 */
public interface Person extends Contact
{
	
	Title getTitle();
	
	String getFirstName();
	
	String getMiddleInitial();
	
	String getLastName();
    
}
