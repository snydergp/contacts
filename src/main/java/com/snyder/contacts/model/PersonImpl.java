package com.snyder.contacts.model;

import java.util.ArrayList;
import java.util.List;


public class PersonImpl extends PersonInfoImpl implements Person
{
    private final List<PersonAddress> addresses = new ArrayList<PersonAddress>();
    private final List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    
    public PersonImpl()
    {
        
    }
    
    public PersonImpl(Person copy)
    {
    	super(copy);
        this.addresses.addAll(copy.getAddresses());
        this.phoneNumbers.addAll(copy.getPhoneNumbers());
    }
    
    public List<PersonAddress> getAddresses()
    {
        return addresses;
    }
    
    public List<PhoneNumber> getPhoneNumbers()
    {
        return phoneNumbers;
    }
    
}
