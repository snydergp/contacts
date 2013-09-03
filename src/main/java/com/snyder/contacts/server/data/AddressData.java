package com.snyder.contacts.server.data;

import java.util.List;

import org.jooq.DSLContext;

import com.snyder.contacts.shared.model.Address;

public interface AddressData
{

	List<Address> getAddressesForContact(DSLContext context, int contactId);

	void insertAddressesForContact(DSLContext context, int contactId, List<Address> addresses);

	void clearAddressesForContact(DSLContext context, int contactId);

}
