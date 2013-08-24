/**
 * 
 */
package com.snyder.contacts.model;

/**
 * Defines the basic info stored for a person
 * 
 * @author greg
 */
public interface PersonInfo
{

	long getId();
	
	Title getTitle();
	
	String getFirstName();
	
	String getLastName();
	
	String getMiddleInitial();
	
}
