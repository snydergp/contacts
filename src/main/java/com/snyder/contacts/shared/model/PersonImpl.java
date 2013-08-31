/**
 * 
 */
package com.snyder.contacts.shared.model;

/**
 * 
 * @author greg
 */
public class PersonImpl extends ContactImpl implements Person
{

	private Title title;
	private String firstName;
	private String middleInitial;
	private String lastName;

	public PersonImpl()
	{

	}

	public PersonImpl(Person copy)
	{
		super(copy);
		this.title = copy.getTitle();
		this.firstName = copy.getFirstName();
		this.middleInitial = copy.getMiddleInitial();
		this.lastName = copy.getLastName();
	}

	@Override
	public Title getTitle()
	{
		return title;
	}

	public void setTitle(Title title)
	{
		this.title = title;
	}

	@Override
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	@Override
	public String getMiddleInitial()
	{
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial)
	{
		this.middleInitial = middleInitial;
	}

	@Override
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

}
