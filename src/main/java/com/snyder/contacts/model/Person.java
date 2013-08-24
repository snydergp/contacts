package com.snyder.contacts.model;

import java.util.List;


/**
 * Extends {@link PersonInfo} with additional contact information
 * 
 * @author greg
 */
public interface Person extends PersonInfo
{
    
    List<PersonAddress> getAddresses();
    
    List<PhoneNumber> getPhoneNumbers();
    
}
