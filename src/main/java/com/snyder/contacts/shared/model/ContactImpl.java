/**
 * 
 */
package com.snyder.contacts.shared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author greg
 */
public class ContactImpl implements Contact
{

	private int id = -1;
	private String info;
	private final List<Address> addresses = new ArrayList<Address>();
	private final List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
	private final List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

	public ContactImpl()
	{

	}

	public ContactImpl(Contact copy)
	{
		this.id = copy.getId();
		this.info = copy.getInfo();
		this.addresses.addAll(copy.getAddresses());
		this.phoneNumbers.addAll(copy.getPhoneNumbers());
		this.emailAddresses.addAll(copy.getEmailAddresses());
	}

	@Override
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	@Override
	public List<Address> getAddresses()
	{
		return addresses;
	}

	@Override
	public List<PhoneNumber> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	@Override
	public List<EmailAddress> getEmailAddresses()
	{
		return emailAddresses;
	}

}
