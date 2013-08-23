package com.snyder.contacts.model;

import java.util.List;


public interface Person
{
    
    Title getTitle();
    
    String getFirstName();
    
    String getMiddleInitial();
    
    String getLastName();
    
    List<PersonAddress> getAddresses();
    
    List<PhoneNumber> getPhoneNumbers();
    
}
