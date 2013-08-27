/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertValuesStep3;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.h2.generated.tables.records.EmailRecord;

import com.snyder.contacts.shared.model.EmailAddress;
import com.snyder.contacts.shared.model.EmailAddressImpl;

/**
 * 
 * @author greg
 */
public class EmailDataImpl implements EmailData
{
	
	private static final org.jooq.h2.generated.tables.Email EMAIL =
		org.jooq.h2.generated.tables.Email.EMAIL;
	private static final Field<Integer> EMAIL_CONTACT_ID =
		org.jooq.h2.generated.tables.Email.EMAIL.CONTACT_ID;
	private static final Field<String> EMAIL_TYPE =
		org.jooq.h2.generated.tables.Email.EMAIL.EMAIL_TYPE;
	private static final Field<String> EMAIL_ =
		org.jooq.h2.generated.tables.Email.EMAIL.EMAIL_;

	@Override
    public List<EmailAddress> getEmailAddressesForContactId(DSLContext context, int contactId)
    {
		Result<Record2<String, String>> results = 
			context.select(EMAIL_TYPE, EMAIL_)
				.from(EMAIL)
				.where(EMAIL_CONTACT_ID.equal(contactId))
				.fetch();
		
		List<EmailAddress> out = new ArrayList<EmailAddress>();
		for(Record2<String, String> result: results)
		{
			EmailAddressImpl address = new EmailAddressImpl();
			address.setType(result.value1());
			address.setEmail(result.value2());
			out.add(address);
		}
		
		return out;
    }

	@Override
    public void insertEmailAddressesForContactId(DSLContext context, int contactId,
        List<EmailAddress> addresses)
    {
	    if(addresses.isEmpty())
	    {
	        return;
	    }
	    
	    InsertValuesStep3<EmailRecord, Integer, String, String> insertStep = 
	        context.insertInto(EMAIL, EMAIL_CONTACT_ID, EMAIL_TYPE, EMAIL_);
	    for(EmailAddress address: addresses)
	    {
	        insertStep = insertStep.values(contactId, address.getType(), address.getEmail());
	    }
	    insertStep.execute();
    }

	@Override
    public void clearEmailAddressesForContactId(DSLContext context, int contactId)
    {
		context.delete(EMAIL)
			.where(EMAIL_CONTACT_ID.equal(contactId))
			.execute();
    }

	
	
}
