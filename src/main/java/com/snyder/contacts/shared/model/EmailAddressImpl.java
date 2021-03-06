/**
 * 
 */
package com.snyder.contacts.shared.model;

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

	@Override
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}
