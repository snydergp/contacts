/**
 * 
 */
package com.snyder.contacts.model;

/**
 * 
 * @author greg
 */
public class EmailAddressImpl implements EmailAddress
{

	private String type;
	private String email;
	
	public EmailAddressImpl()
	{
		
	}
	
	public EmailAddressImpl(EmailAddress copy)
	{
		this.type = copy.getType();
		this.email = copy.getEmail();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
}
