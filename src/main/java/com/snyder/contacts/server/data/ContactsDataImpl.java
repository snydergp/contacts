/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.h2.generated.tables.records.ContactRecord;

import com.snyder.contacts.client.serverinterface.ContactSort;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSummary;
import com.snyder.contacts.model.Person;
import com.snyder.contacts.model.PersonImpl;

/**
 * 
 * @author greg
 */
public class ContactsDataImpl extends DataImpl implements ContactsData
{

	private static final org.jooq.h2.generated.tables.Contact CONTACT = 
		org.jooq.h2.generated.tables.Contact.CONTACT;
	private static final Field<Integer> CONTACT_ID = 
		org.jooq.h2.generated.tables.Contact.CONTACT.CONTACT_ID;
	private static final Field<String> INFO = 
		org.jooq.h2.generated.tables.Contact.CONTACT.INFO;
	
	private static final org.jooq.h2.generated.tables.Person PERSON =
		org.jooq.h2.generated.tables.Person.PERSON;
	private static final Field<Integer> PERSON_ID =
		org.jooq.h2.generated.tables.Person.PERSON.CONTACT_ID;
	private static final Field<Short> TITLE_CD =
		org.jooq.h2.generated.tables.Person.PERSON.TITLE_CD;
	private static final Field<String> FIRST_NAME =
		org.jooq.h2.generated.tables.Person.PERSON.FIRST_NAME;
	private static final Field<String> LAST_NAME =
		org.jooq.h2.generated.tables.Person.PERSON.LAST_NAME;
	private static final Field<String> MIDDLE_INITIAL =
		org.jooq.h2.generated.tables.Person.PERSON.MIDDLE_INITIAL;
	
	private static final Field<?>[] PERSON_FIELDS = 
		(Field<?>[]) ArrayUtils.addAll(CONTACT.fields(), PERSON.fields());
	
	/**
	 * @param dataSource
	 * @param dialect
	 */
    public ContactsDataImpl(DataSource dataSource, SQLDialect dialect)
    {
	    super(dataSource, dialect);
    }

	@Override
    public int createContact(Contact contact)
    {
	    DSLContext context = this.getDSLContext();
	    
	    ContactRecord inserted = context
	    	.insertInto(CONTACT, INFO)
	    	.values(contact.getInfo())
	    	.returning(CONTACT_ID)
	    	.fetchOne();
	    
	    if(inserted == null)
	    {
	    	//TODO
	    }
	    
	    int id = inserted.getContactId();
	    
	    // TODO insert subtype
	    
	    return id;
    }

	@Override
    public void deleteContact(int id)
    {
	    DSLContext context = this.getDSLContext();
	    
	    context.delete(CONTACT)
	    	.where(CONTACT_ID.equal(id))
	    	.execute();
    }

	@Override
    public Contact getContactById(int id)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<ContactSummary> getContacts(ContactSort field, int startingAtId, int limit)
    {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	private Person getPersonById(int id)
	{
		DSLContext context = this.getDSLContext();
		
		Record record = 
			context.select(PERSON_FIELDS)
				.from(CONTACT, PERSON)
				.where(CONTACT_ID.equal(id), CONTACT_ID.equal(PERSON_ID))
				.fetchAny();
		
		PersonImpl person = new PersonImpl();
		person.setId(id);
		person.setInfo(record.getValue(INFO));
		//TODO title
		person.setFirstName(record.getValue(FIRST_NAME));
		person.setMiddleInitial(record.getValue(MIDDLE_INITIAL));
		person.setLastName(record.getValue(LAST_NAME));
		
		return person;
	}

}
