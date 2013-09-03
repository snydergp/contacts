package com.snyder.contacts.shared.model;

public class PhoneNumberImpl implements PhoneNumber
{

	private String type;
	private String number;

	public PhoneNumberImpl()
	{

	}

	public PhoneNumberImpl(PhoneNumber copy)
	{
		this.type = copy.getType();
		this.number = copy.getNumber();
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
	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

}
