package com.snyder.contacts.model;


public class PhoneNumberImpl implements PhoneNumber
{
    
    private String type;
    private String number;
    
    public PhoneNumberImpl()
    {
        
    }
    
    public PhoneNumberImpl(PhoneNumber copy)
    {
        this.type = copy.getType();
        this.number = copy.getNumber();
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getNumber()
    {
        return number;
    }
    
    public void setNumber(String number)
    {
        this.number = number;
    }
    
}
