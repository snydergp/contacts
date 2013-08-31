/**
 * 
 */
package com.snyder.contacts.shared.model;

/**
 * Extends {@link Contact} with fields pertaining to businesses
 * 
 * @author greg
 */
public interface Business extends Contact
{

	/**
	 * @return the business name
	 */
	String getName();

}
