package com.snyder.contacts.shared.model;

public class AddressImpl implements Address
{

	private String addressType;
	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String country;
	private String additionalInformation;

	public AddressImpl()
	{

	}

	public AddressImpl(Address copy)
	{
		this.addressType = copy.getAddressType();
		this.line1 = copy.getLine1();
		this.line2 = copy.getLine2();
		this.line3 = copy.getLine3();
		this.city = copy.getCity();
		this.stateProvince = copy.getStateProvince();
		this.postalCode = copy.getPostalCode();
		this.country = copy.getCountry();
		this.additionalInformation = copy.getAdditionalInformation();
	}

	@Override
	public String getAddressType()
	{
		return addressType;
	}

	public void setAddressType(String addressType)
	{
		this.addressType = addressType;
	}

	@Override
	public String getLine1()
	{
		return line1;
	}

	public void setLine1(String line1)
	{
		this.line1 = line1;
	}

	@Override
	public String getLine2()
	{
		return line2;
	}

	public void setLine2(String line2)
	{
		this.line2 = line2;
	}

	@Override
	public String getLine3()
	{
		return line3;
	}

	public void setLine3(String line3)
	{
		this.line3 = line3;
	}

	@Override
	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String getStateProvince()
	{
		return stateProvince;
	}

	public void setStateProvince(String stateProvince)
	{
		this.stateProvince = stateProvince;
	}

	@Override
	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	@Override
	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	@Override
	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}

}
