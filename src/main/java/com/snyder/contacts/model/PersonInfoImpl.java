/**
 * 
 */
package com.snyder.contacts.model;

/**
 * 
 * @author greg
 */
public class PersonInfoImpl implements PersonInfo
{

	private long id = -1;
    private Title title;
    private String middleInitial;
    private String firstName;
    private String lastName;
    
    public PersonInfoImpl()
    {
    	
    }
    
    public PersonInfoImpl(PersonInfo copy)
    {
    	this.id = copy.getId();
        this.title = copy.getTitle();
        this.firstName = copy.getFirstName();
        this.middleInitial = copy.getMiddleInitial();
        this.lastName = copy.getLastName();
    }
    
    public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
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

}
