package com.snyder.contacts.model;


public class PersonAddressImpl extends AddressImpl implements PersonAddress
{
    
    private String addressType;
    
    public PersonAddressImpl()
    {
        
    }
    
    public PersonAddressImpl(PersonAddress copy)
    {
        super(copy);
        this.addressType = copy.getAddressType();
    }
    
    public PersonAddressImpl(Address copy)
    {
        super(copy);
    }
    
    public String getAddressType()
    {
        return addressType;
    }
    
    public void setAddressType(String addressType)
    {
        this.addressType = addressType;
    }
    
}
