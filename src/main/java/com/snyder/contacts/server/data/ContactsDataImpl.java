/**
 * 
 */
package com.snyder.contacts.server.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.h2.generated.tables.records.ContactRecord;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSort;
import com.snyder.contacts.model.ContactSummary;

/**
 * 
 * @author greg
 */
@Singleton
public class ContactsDataImpl extends DataImpl implements ContactsData
{

	private static final org.jooq.h2.generated.tables.Contact CONTACT = 
		org.jooq.h2.generated.tables.Contact.CONTACT;
	private static final Field<Integer> CONTACT_ID = 
		org.jooq.h2.generated.tables.Contact.CONTACT.CONTACT_ID;
	private static final Field<String> INFO = 
		org.jooq.h2.generated.tables.Contact.CONTACT.INFO;
	

	
	
	private final List<ContactSubtypeData<?>> contactSubtypeData = 
	    new ArrayList<ContactSubtypeData<?>>();
	private final ContactSummaryData summaryData;
	private final EmailData emailData;
	private final PhoneNumberData phoneNumberData;
	
	/**
	 * @param dataSource
	 * @param dialect
	 */
	@Inject
    public ContactsDataImpl(DataSource dataSource, SQLDialect dialect,
        List<ContactSubtypeData<?>> contactSubtypeData, ContactSummaryData summaryData, 
        EmailData emailData, PhoneNumberData phoneNumberData)
    {
	    super(dataSource, dialect);
	    this.contactSubtypeData.addAll(contactSubtypeData);
	    this.summaryData = summaryData;
	    this.emailData = emailData;
	    this.phoneNumberData = phoneNumberData;
    }

	@Override
    public int createContact(Contact contact)
    {
		TransactionContext transactionContext = this.getTransactionContext();
		try
		{
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
	        
	        // Find the correct ContactSubtypeData interface and insert a subtype record
	        for(ContactSubtypeData<?> subtypeData: contactSubtypeData)
	        {
	            if(subtypeData.isSubtypeInstance(contact))
	            {
	                subtypeData.insertSubtype(context, contact);
	                break;
	            }
	        }
	        
	        emailData.insertEmailAddressesForContactId(context, id, contact.getEmailAddresses());
	        
	        phoneNumberData.insertPhoneNumbersForContact(context, id, contact.getPhoneNumbers());
	        
	        // Commit the transaction
	        transactionContext.commit();
	    
	        return id;
		}
	    finally
	    {
    	    // Close the connection
    	    transactionContext.close();
	    }
    }

	@Override
	public void updateContact(Contact contact)
	{
		TransactionContext transactionContext = this.getTransactionContext();
		try
		{
    	    DSLContext context = transactionContext.getContext();
    		
    	    int contactId = contact.getId();
    	    
    	    Contact existing = this.getContactById(contactId);
    	    
    	    // Rather than updating if no subtype change, and deleting/inserting if changed, we'll
    	    // simplify by just doing an insert/delete for all cases.
    	    this.deleteSubtype(context, existing);
    	    this.insertSubtype(context, contact);
    	    
    	    emailData.clearEmailAddressesForContactId(context, contactId);
    	    emailData.insertEmailAddressesForContactId(context, contactId, 
    	        contact.getEmailAddresses());
    	    
    	    phoneNumberData.clearPhoneNumbersForContact(context, contactId);
    	    phoneNumberData.insertPhoneNumbersForContact(context, contactId, 
    	        contact.getPhoneNumbers());
    	    
    	    //TODO addresses
    	    
    	    // Commit the transaction
    	    transactionContext.commit();
		}
		finally
		{
    	    // Close the connection
    	    transactionContext.close();
		}
	}

	@Override
    public void deleteContact(int id)
    {
		TransactionContext transactionContext = this.getTransactionContext();
		try
		{
		    DSLContext context = transactionContext.getContext();
	        
	        // All child tables are set up to cascade delete on deletion the the referenced CONTACT
	        // parent record.  Therefore no additional calls are needed.
	        context.delete(CONTACT)
	            .where(CONTACT_ID.equal(id))
	            .execute();
	        
	        // Commit the transaction
	        transactionContext.commit();
		}
	    finally
    	{
    	    // Close the connection
    	    transactionContext.close();
	    }
    }

	@Override
    public Contact getContactById(int id)
    {
		TransactionContext transactionContext = this.getTransactionContext();
		try
		{
    	    DSLContext context = transactionContext.getContext();
    	    
    	    // This approach requires more queries than is strictly necessary when the correct subtype
            // table is not queried first. This could be combined into a single query by joining
            // PERSON and BUSINESS to CONTACT and then creating the subtype object corresponding to the
            // populated fields. Although this saves an occasionally-wasted query, it is much more
            // complex, difficult-to-maintain, and the join comes with its own performance hit. If more
            // than 2 subtypes are ever needed, it may be better to have a query that does a ID-only
            // join between CONTACT and all subtype tables to determine the actual subtype table to
            // query.
            Contact contact = null;
            for(ContactSubtypeData<?> subtypeData: contactSubtypeData)
            {
                contact = subtypeData.getSubtype(context, id);
                if(contact != null)
                {
                    break;
                }
            }
            if(contact == null)
            {
                return null;
            }
            
            contact.getEmailAddresses().addAll(
                emailData.getEmailAddressesForContactId(context, id));
            
            contact.getPhoneNumbers().addAll(
                phoneNumberData.getPhoneNumbersForContact(context, id));
            
            // TODO addresses
            
            return contact;
		}
		finally
		{
    	    // Close the connection
    	    transactionContext.close();
		}
    }

	@Override
    public List<ContactSummary> getContacts(ContactSort field, boolean ascending, int startingAt,
        int limit)
    {
		TransactionContext transactionContext = this.getTransactionContext();
		try
		{
    	    DSLContext context = transactionContext.getContext();
    	    return summaryData.getContacts(context, field, ascending, startingAt, limit);
		}
		finally
		{
		    // Close the connection
		    transactionContext.close();
		}
    }
	
	private void deleteSubtype(DSLContext context, Contact contact)
	{
	    for(ContactSubtypeData<?> subtypeData: contactSubtypeData)
        {
            if(subtypeData.isSubtypeInstance(contact))
            {
                subtypeData.deleteSubtype(context, contact.getId());
            }
        }
	}
	
	private void insertSubtype(DSLContext context, Contact contact)
	{
	    for(ContactSubtypeData<?> subtypeData: contactSubtypeData)
        {
            if(subtypeData.isSubtypeInstance(contact))
            {
                subtypeData.insertSubtype(context, contact);
            }
        }
	}
	
}
