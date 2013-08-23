package com.snyder.contacts.model;

import java.util.ArrayList;
import java.util.List;


public class PersonImpl implements Person
{

    private Title title;
    private String middleInitial;
    private String firstName;
    private String lastName;
    private final List<PersonAddress> addresses = new ArrayList<PersonAddress>();
    private final List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    
    public PersonImpl()
    {
        
    }
    
    public PersonImpl(Person copy)
    {
        this.title = copy.getTitle();
        this.firstName = copy.getFirstName();
        this.middleInitial = copy.getMiddleInitial();
        this.lastName = copy.getLastName();
        this.addresses.addAll(copy.getAddresses());
        this.phoneNumbers.addAll(copy.getPhoneNumbers());
    }
    
    public Title getTitle()
    {
        return title;
    }
    
    public void setTitle(Title title)
    {
        this.title = title;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getMiddleInitial()
    {
        return middleInitial;
    }
    
    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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
