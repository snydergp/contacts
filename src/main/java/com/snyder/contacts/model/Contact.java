/**
 * 
 */
package com.snyder.contacts.model;

import java.util.List;

/**
 * 
 * @author greg
 */
public interface Contact
{

	int getId();
	
	String getInfo();
    
    List<Address> getAddresses();
    
    List<PhoneNumber> getPhoneNumbers();
    
    List<EmailAddress> getEmailAddresses();
	
}
