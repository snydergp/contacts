/**
 * 
 */
package com.snyder.contacts.server.data;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record5;

import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.Person;
import com.snyder.contacts.shared.model.PersonImpl;
import com.snyder.contacts.shared.model.Title;

/**
 * 
 * @author greg
 */
public class PersonDataImpl implements PersonData
{
	
	private static final org.jooq.h2.generated.tables.Person PERSON =
		org.jooq.h2.generated.tables.Person.PERSON;
	private static final Field<Integer> PERSON_ID =
		org.jooq.h2.generated.tables.Person.PERSON.CONTACT_ID;
	private static final Field<Short> TITLE_CD =
		org.jooq.h2.generated.tables.Person.PERSON.TITLE_CD;
	private static final Field<String> FIRST_NAME =
		org.jooq.h2.generated.tables.Person.PERSON.FIRST_NAME;
	private static final Field<String> MIDDLE_INITIAL =
		org.jooq.h2.generated.tables.Person.PERSON.MIDDLE_INITIAL;
	private static final Field<String> LAST_NAME =
		org.jooq.h2.generated.tables.Person.PERSON.LAST_NAME;
	
	private static Short titleToCode(Title title)
	{
		if(title == null)
		{
			return null;
		}
		return (short) title.ordinal();
	}

    @Override
    public boolean isSubtypeInstance(Contact contact)
    {
        return contact instanceof Person;
    }

    @Override
    public void insertSubtype(DSLContext context, Contact contact)
    {
        if(!isSubtypeInstance(contact))
        {
            throw new IllegalArgumentException();
        }
        
        Person person = (Person) contact;
        
        context.insertInto(PERSON, PERSON_ID, TITLE_CD, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME)
        .values(person.getId(), 
            titleToCode(person.getTitle()), 
            person.getFirstName(), 
            person.getMiddleInitial(), 
            person.getLastName())
        .execute();
    }

    @Override
    public Person getSubtype(DSLContext context, int contactId)
    {
        Record5<Integer, Short, String, String, String> record = 
            context.select(PERSON_ID, TITLE_CD, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME)
                .from(PERSON)
                .where(PERSON_ID.equal(contactId))
                .fetchAny();
        
        if(record == null)
        {
            return null;
        }
        
        PersonImpl person = new PersonImpl();
        person.setId(contactId);
        //TODO title
        person.setFirstName(record.getValue(FIRST_NAME));
        person.setMiddleInitial(record.getValue(MIDDLE_INITIAL));
        person.setLastName(record.getValue(LAST_NAME));
        
        return person;
    }

    @Override
    public void deleteSubtype(DSLContext context, int contactId)
    {
        context.delete(PERSON).where(PERSON_ID.equal(contactId)).execute();
    }

}
