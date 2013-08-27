package com.snyder.contacts.server.data;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.Result;
import org.jooq.h2.generated.tables.records.AddressRecord;

import com.snyder.contacts.model.Address;
import com.snyder.contacts.model.AddressImpl;


public class AddressDataImpl implements AddressData
{
    
    private static final org.jooq.h2.generated.tables.Address ADDRESS = 
        org.jooq.h2.generated.tables.Address.ADDRESS;
    private static final Field<Integer> CONTACT_ID = ADDRESS.CONTACT_ID;

    @Override
    public List<Address> getAddressesForContact(DSLContext context, int contactId)
    {
        Result<AddressRecord> results = 
            context.selectFrom(ADDRESS)
                .where(CONTACT_ID.equal(contactId))
                .fetch();
        
        List<Address> out = new ArrayList<Address>();
        for(AddressRecord result: results)
        {
            AddressImpl address = new AddressImpl();
            address.setAddressType(result.getAddressType());
            address.setLine1(result.getLine_1());
            address.setLine2(result.getLine_2());
            address.setLine3(result.getLine_3());
            address.setCity(result.getCity());
            address.setStateProvince(result.getStateProvince());
            address.setPostalCode(result.getPostalCode());
            address.setCountry(result.getCountry());
            address.setAdditionalInformation(result.getAdditionalInfo());
            out.add(address);
        }
        return out;
    }

    @Override
    public void insertAddressesForContact(DSLContext context, int contactId, 
        List<Address> addresses)
    {
        if(addresses.isEmpty())
        {
            return;
        }
        
        InsertSetStep<AddressRecord> insertSetStep = context.insertInto(ADDRESS);
        for(Address address: addresses)
        {
            AddressRecord addressRecord = new AddressRecord();
            addressRecord.setContactId(contactId);
            addressRecord.setAddressType(address.getAddressType());
            addressRecord.setLine_1(address.getLine1());
            addressRecord.setLine_2(address.getLine2());
            addressRecord.setLine_3(address.getLine3());
            addressRecord.setCity(address.getCity());
            addressRecord.setStateProvince(address.getStateProvince());
            addressRecord.setPostalCode(address.getPostalCode());
            addressRecord.setCountry(address.getCountry());
            addressRecord.setAdditionalInfo(address.getAdditionalInformation());
            insertSetStep = insertSetStep.set(addressRecord);
        }
        ((InsertSetMoreStep<AddressRecord>) insertSetStep).execute();
    }

    @Override
    public void clearAddressesForContact(DSLContext context, int contactId)
    {
        context.delete(ADDRESS).where(CONTACT_ID.equal(contactId)).execute();
    }
    
}
