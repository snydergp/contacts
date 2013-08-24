/**
 * 
 */
package com.snyder.contacts.model;

/**
 * 
 * @author greg
 */
public class ContactSummaryImpl implements ContactSummary
{
	
	private long id = -1;
	private String displayName;
    
    public ContactSummaryImpl()
    {
    	
    }
    
    public ContactSummaryImpl(ContactSummary copy)
    {
    	this.id = copy.getId();
    	this.displayName = copy.getDisplayName();
    }

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setName(String displayName)
	{
		this.displayName = displayName;
	}

}
