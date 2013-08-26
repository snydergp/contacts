/**
 * 
 */
package com.snyder.contacts.server.data;

import org.jooq.DSLContext;

import com.snyder.contacts.model.Person;

/**
 * 
 * @author greg
 */
public interface PersonData
{

	void insertPerson(DSLContext context, Person person);
	
	Person getPerson(DSLContext context, int contactId);

	void updatePerson(DSLContext context, Person person);
	
	void deletePerson(DSLContext context, int contactId);
	
}
