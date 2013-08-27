package com.snyder.contacts.model;

import java.util.HashMap;
import java.util.Map;


public enum ContactType
{
    
    PERSON,
    BUSINESS;
    
    private static final Map<String, ContactType> CODE_MAP = new HashMap<String, ContactType>();
    static
    {
        for(ContactType contactType: ContactType.values())
        {
            CODE_MAP.put(contactType.toCode(), contactType);
        }
    }
    
    public static ContactType fromCode(String code)
    {
        return CODE_MAP.get(code);
    }
    
    public String toCode()
    {
        switch(this)
        {
            case PERSON: return "P";
            case BUSINESS: return "B";
            default: throw new IllegalStateException();
        }
    }
    
}
