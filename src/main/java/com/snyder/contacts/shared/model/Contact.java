/**
 * 
 */
package com.snyder.contacts.shared.model;

import java.util.List;

/**
 * 
 * @author greg
 */
public interface Contact
{
    
    /**
     * The ID used to indicate a new contact
     */
    public static final int NEW_CONTACT_ID = -1;

	int getId();
	
	String getInfo();
    
    List<Address> getAddresses();
    
    List<PhoneNumber> getPhoneNumbers();
    
    List<EmailAddress> getEmailAddresses();
	
}
