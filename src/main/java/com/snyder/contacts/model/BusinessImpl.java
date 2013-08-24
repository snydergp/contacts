/**
 * 
 */
package com.snyder.contacts.model;

/**
 * 
 * @author greg
 */
public class BusinessImpl extends ContactImpl
{

	private String name;
	
	public BusinessImpl()
	{
		
	}
	
	public BusinessImpl(Business copy)
	{
		super(copy);
		this.name = copy.getName();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
}
