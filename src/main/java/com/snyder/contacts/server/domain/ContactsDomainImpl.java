package com.snyder.contacts.server.domain;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.snyder.contacts.server.data.ContactsData;
import com.snyder.contacts.server.exceptions.InvalidContactException;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactSort;
import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.contacts.shared.model.validation.ContactValidation;

public class ContactsDomainImpl implements ContactsDomain
{
    
    private static final ContactValidation CONTACT_VALIDATION = new ContactValidation();
    
    private final ContactsData contactsData;

    public ContactsDomainImpl(ContactsData contactsData)
    {
        this.contactsData = contactsData;
    }

    @Override
    public int createContact(Contact contact) throws InvalidContactException
    {
        // Validate the contact instance
        Builder<String> errors = new ImmutableList.Builder<String>();
        CONTACT_VALIDATION.validate(contact, errors);
        List<String> errorList = errors.build();
        
        // If no errors, create the contact, returning the ID
        if(errorList.isEmpty())
        {
            return contactsData.createContact(contact);
        }
        else
        {
            // If errors were identified, return error listing to client
            throw new InvalidContactException(errorList);
        }
    }

    @Override
    public void updateContact(Contact contact) throws InvalidContactException
    {
        // Validate the contact instance
        Builder<String> errors = new ImmutableList.Builder<String>();
        CONTACT_VALIDATION.validate(contact, errors);
        List<String> errorList = errors.build();
        
        // If no errors, update the contact
        if(errorList.isEmpty())
        {
            contactsData.updateContact(contact);
        }
        else
        {
            // If errors were identified, return error listing to client
            throw new InvalidContactException(errorList);
        }
    }

    @Override
    public void deleteContact(int id)
    {
        contactsData.deleteContact(id);
    }

    @Override
    public Contact getContactById(int id)
    {
        //TODO check for null and throw exception??
        return contactsData.getContactById(id);
    }

    @Override
    public List<ContactSummary> getContacts(ContactSort field, boolean ascending, int startingAtId,
        int limit)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
