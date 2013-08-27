/**
 * 
 */
package com.snyder.contacts.shared.model;

/**
 * 
 * @author greg
 */
public class ContactSummaryImpl implements ContactSummary
{
	
	private int id = -1;
	private String displayName;
	private ContactType type;
    
    public ContactSummaryImpl()
    {
    	
    }
    
    public ContactSummaryImpl(ContactSummary copy)
    {
    	this.id = copy.getId();
    	this.displayName = copy.getDisplayName();
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

	public String getDisplayName()
	{
		return displayName;
	}
    
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    
    public ContactType getType()
    {
        return type;
    }
    
    public void setType(ContactType type)
    {
        this.type = type;
    }

}
