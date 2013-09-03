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
import org.jooq.h2.generated.tables.records.PhoneNumberRecord;

import com.snyder.contacts.shared.model.PhoneNumber;
import com.snyder.contacts.shared.model.PhoneNumberImpl;

/**
 * 
 * @author greg
 */
public class PhoneNumberDataImpl
{

	private static final org.jooq.h2.generated.tables.PhoneNumber PHONE_NUMBER =
	    org.jooq.h2.generated.tables.PhoneNumber.PHONE_NUMBER;
	private static final Field<Integer> PHONE_CONTACT_ID = PHONE_NUMBER.CONTACT_ID;
	private static final Field<String> PHONE_TYPE = PHONE_NUMBER.PHONE_TYPE;
	private static final Field<String> NUMBER = PHONE_NUMBER.NUMBER;

	public List<PhoneNumber> getPhoneNumbersForContact(DSLContext context, int contactId)
	{
		Result<Record2<String, String>> results =
		    context.select(PHONE_TYPE, NUMBER).from(PHONE_NUMBER)
		        .where(PHONE_CONTACT_ID.equal(contactId)).fetch();

		List<PhoneNumber> out = new ArrayList<PhoneNumber>();
		for (Record2<String, String> result : results)
		{
			PhoneNumberImpl number = new PhoneNumberImpl();
			number.setType(result.value1());
			number.setNumber(result.value2());
			out.add(number);
		}

		return out;
	}

	public void insertPhoneNumbersForContact(DSLContext context, int contactId,
	    List<PhoneNumber> phoneNumbers)
	{
		if (phoneNumbers.isEmpty())
		{
			return;
		}

		InsertValuesStep3<PhoneNumberRecord, Integer, String, String> insert =
		    context.insertInto(PHONE_NUMBER, PHONE_CONTACT_ID, PHONE_TYPE, NUMBER);
		for (PhoneNumber phoneNumber : phoneNumbers)
		{
			insert.values(contactId, phoneNumber.getType(), phoneNumber.getNumber());
		}

		insert.execute();
	}

	public void clearPhoneNumbersForContact(DSLContext context, int contactId)
	{
		context.delete(PHONE_NUMBER).where(PHONE_CONTACT_ID.equal(contactId)).execute();
	}

}
