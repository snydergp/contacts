package com.snyder.contacts.server.domain;

import java.util.List;

import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSort;
import com.snyder.contacts.model.ContactSummary;
import com.snyder.contacts.server.exceptions.InvalidContactException;


public interface ContactsDomain
{
    
    int createContact(Contact contact) throws InvalidContactException;
    
    void updateContact(Contact contact) throws InvalidContactException;
    
    void deleteContact(int id);

    Contact getContactById(int id);
    
    List<ContactSummary> getContacts(ContactSort field, boolean ascending, int startingAtId, int limit);
    
}
