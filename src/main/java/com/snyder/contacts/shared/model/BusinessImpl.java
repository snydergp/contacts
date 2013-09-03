/**
 * 
 */
package com.snyder.contacts.shared.model;

/**
 * 
 * @author greg
 */
public class BusinessImpl extends ContactImpl implements Business
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

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
