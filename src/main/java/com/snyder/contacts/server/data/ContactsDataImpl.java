/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.List;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.h2.generated.tables.records.ContactRecord;

import com.snyder.contacts.client.serverinterface.ContactSort;
import com.snyder.contacts.model.Business;
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
	

	
	
	private final PersonData personData;
	private final BusinessData businessData;
	private final EmailData emailData;
	private final PhoneNumberData phoneNumberData;
	
	/**
	 * @param dataSource
	 * @param dialect
	 */
    public ContactsDataImpl(DataSource dataSource, SQLDialect dialect, PersonData personData, 
    	BusinessData businessData, EmailData emailData, PhoneNumberData phoneNumberData)
    {
	    super(dataSource, dialect);
	    this.personData = personData;
	    this.businessData = businessData;
	    this.emailData = emailData;
	    this.phoneNumberData = phoneNumberData;
    }

	@Override
    public int createContact(Contact contact)
    {
		TransactionContext transactionContext = this.getTransactionContext();
	    DSLContext context = transactionContext.getContext();
	    
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
	    
	    if(contact instanceof Person)
	    {
	    	PersonImpl person = new PersonImpl((Person) contact);
	    	person.setId(id);
	    	personData.insertPerson(context, person);
	    }
	    else if(contact instanceof Business)
	    {
	    	//TODO
	    }
	    
	    emailData.insertEmailAddressesForContactId(context, id, contact.getEmailAddresses());
	    
	    phoneNumberData.insertPhoneNumbersForContact(context, id, contact.getPhoneNumbers());
	    
	    // Commit the transaction
	    transactionContext.commit();
	    
	    // Close the connection
	    transactionContext.close();
	    
	    return id;
    }

	@Override
	public void updateContact(Contact contact)
	{
		TransactionContext transactionContext = this.getTransactionContext();
	    DSLContext context = transactionContext.getContext();
		
	    int contactId = contact.getId();
	    
	    Contact existing = this.getContactById(contactId);
	    
	    // Rather than updating if no subtype change, and deleting/inserting if changed, we'll
	    // simplify by just doing an insert/delete for all cases.
	    this.deleteSubtype(context, existing);
	    this.insertSubtype(context, contact);
	    
	    emailData.clearEmailAddressesForContactId(context, contactId);
	    emailData.insertEmailAddressesForContactId(context, contactId, contact.getEmailAddresses());
	    
	    phoneNumberData.clearPhoneNumbersForContact(context, contactId);
	    phoneNumberData.insertPhoneNumbersForContact(context, contactId, contact.getPhoneNumbers());
	    
	    //TODO addresses
	    
	    // Commit the transaction
	    transactionContext.commit();
	    
	    // Close the connection
	    transactionContext.close();
	}

	@Override
    public void deleteContact(int id)
    {
		TransactionContext transactionContext = this.getTransactionContext();
	    DSLContext context = transactionContext.getContext();
	    
	    context.delete(CONTACT)
	    	.where(CONTACT_ID.equal(id))
	    	.execute();
	    
	    // Commit the transaction
	    transactionContext.commit();
	    
	    // Close the connection
	    transactionContext.close();
    }

	@Override
    public Contact getContactById(int id)
    {
		TransactionContext transactionContext = this.getTransactionContext();
	    DSLContext context = transactionContext.getContext();
		
	    //TODO contact fields (join w/ subtype ids to determine type?)
	    
		Contact contact = personData.getPerson(context, id);
		if(contact == null)
		{
			contact = businessData.getBusiness(context, id);
			if(contact == null)
			{
				throw new DataException("No contact subtype found for id " + id);
			}
		}
		
		contact.getEmailAddresses().addAll(emailData.getEmailAddressesForContactId(context, id));
		
		contact.getPhoneNumbers().addAll(phoneNumberData.getPhoneNumbersForContact(context, id));
		
		// TODO addresses
	    
	    // Close the connection
	    transactionContext.close();
		
	    return contact;
    }

	@Override
    public List<ContactSummary> getContacts(ContactSort field, int startingAtId, int limit)
    {
		TransactionContext transactionContext = this.getTransactionContext();
	    DSLContext context = transactionContext.getContext();
	    
	    //TODO
	    
	    // Close the connection
	    transactionContext.close();
	    
	    return null;
    }
	
	private void deleteSubtype(DSLContext context, Contact contact)
	{
		if(contact instanceof Person)
		{
			personData.deletePerson(context, contact.getId());
		}
		if(contact instanceof Business)
		{
			businessData.deleteBusiness(context, contact.getId());
		}
	}
	
	private void insertSubtype(DSLContext context, Contact contact)
	{
		if(contact instanceof Person)
		{
			personData.insertPerson(context, (Person) contact);
		}
		if(contact instanceof Business)
		{
			businessData.insertBusiness(context, (Business) contact);
		}
	}
	
}
