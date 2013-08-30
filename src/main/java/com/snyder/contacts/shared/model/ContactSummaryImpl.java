/**
 * 
 */
package com.snyder.contacts.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;


/**
 * 
 * @author greg
 */
public class ContactSummaryImpl implements ContactSummary
{
    
    private static final Joiner NAME_JOINER = Joiner.on(' ');
    
    public static ContactSummaryImpl fromContact(Contact contact)
    {
        ContactSummaryImpl summary = new ContactSummaryImpl();
        summary.setId(contact.getId());
        if(contact instanceof Person)
        {
            summary.setType(ContactType.PERSON);
            Person person = (Person) contact;
            summary.setFirstName(person.getFirstName());
            summary.setMiddleInitial(person.getMiddleInitial());
            summary.setLastName(person.getLastName());
        }
        else if(contact instanceof Business)
        {
            summary.setType(ContactType.BUSINESS);
            Business business = (Business) contact;
            summary.setFirstName(business.getName());
            summary.setMiddleInitial("");
            summary.setLastName("");
        }
        else
        {
            throw new IllegalStateException("Unknown contact subtype");
        }
        return summary;
    }

    @SerializedName("i")
	private int id = -1;
	@SerializedName("f")
    private String firstName;
    @SerializedName("m")
    private String middleInitial;
    @SerializedName("l")
    private String lastName;
    @SerializedName("t")
	private ContactType type;
    
    public ContactSummaryImpl()
    {
    	
    }
    
    public ContactSummaryImpl(ContactSummary copy)
    {
    	this.id = copy.getId();
    	this.firstName = copy.getFirstName();
    	this.middleInitial = copy.getMiddleInitial();
    	this.lastName = copy.getLastName();
    	this.type = copy.getType();
    }

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

    public ContactType getType()
    {
        return type;
    }
    
    public void setType(ContactType type)
    {
        this.type = type;
    }

    @Override
    public String toDisplay(ContactSort sort)
    {
        switch(type)
        {
            case PERSON:
                List<String> names = new ArrayList<String>(3);
                switch(sort)
                {
                    case FIRST_NAME:
                        if(!Strings.isNullOrEmpty(firstName))
                        {
                            names.add(firstName);
                        }
                        if(!Strings.isNullOrEmpty(middleInitial))
                        {
                            names.add(middleInitial + ".");
                        }
                        if(!Strings.isNullOrEmpty(lastName))
                        {
                            names.add(lastName);
                        }
                        return NAME_JOINER.join(names);
                    case LAST_NAME:
                        if(!Strings.isNullOrEmpty(lastName))
                        {
                            names.add(lastName + ",");
                        }
                        if(!Strings.isNullOrEmpty(firstName))
                        {
                            names.add(firstName);
                        }
                        if(!Strings.isNullOrEmpty(middleInitial))
                        {
                            names.add(middleInitial + ".");
                        }
                        return NAME_JOINER.join(names);
                    default: 
                        throw new IllegalStateException();
                }
            case BUSINESS:
                return firstName;
            default: 
                throw new IllegalStateException();
        }
    }

}
