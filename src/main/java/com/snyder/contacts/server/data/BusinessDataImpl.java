/**
 * 
 */
package com.snyder.contacts.server.data;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.h2.generated.tables.records.BusinessRecord;

import com.snyder.contacts.shared.model.Business;
import com.snyder.contacts.shared.model.BusinessImpl;
import com.snyder.contacts.shared.model.Contact;

/**
 * 
 * @author greg
 */
public class BusinessDataImpl implements BusinessData
{
	
	private static final org.jooq.h2.generated.tables.Business BUSINESS = 
		org.jooq.h2.generated.tables.Business.BUSINESS;
	private static final Field<Integer> CONTACT_ID =
		org.jooq.h2.generated.tables.Business.BUSINESS.CONTACT_ID;
	private static final Field<String> NAME =
		org.jooq.h2.generated.tables.Business.BUSINESS.NAME;

    @Override
    public boolean isSubtypeInstance(Contact contact)
    {
        return contact instanceof Business;
    }

    @Override
    public void insertSubtype(DSLContext context, Contact contact)
    {
        if(!isSubtypeInstance(contact))
        {
            throw new IllegalArgumentException();
        }
        
        Business business = (Business) contact;
        
        context.insertInto(BUSINESS, CONTACT_ID, NAME)
            .values(business.getId(), business.getName())
            .execute();
    }

    @Override
    public Business getSubtype(DSLContext context, int contactId)
    {
        BusinessRecord record = context.selectFrom(BUSINESS)
            .where(CONTACT_ID.equal(contactId))
            .fetchAny();
        
        if(record == null)
        {
            return null;
        }
        
        BusinessImpl business = new BusinessImpl();
        business.setId(record.getContactId());
        business.setName(record.getName());
        
        return business;
    }

    @Override
    public void deleteSubtype(DSLContext context, int contactId)
    {
        context.delete(BUSINESS).where(CONTACT_ID.equal(contactId)).execute();
    }

}
