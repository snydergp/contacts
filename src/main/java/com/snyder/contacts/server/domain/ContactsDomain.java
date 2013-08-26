package com.snyder.contacts.server.domain;

import java.util.List;

import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactSort;
import com.snyder.contacts.model.ContactSummary;


public interface ContactsDomain
{
    
    int createContact(Contact contact);
    
    void updateContact(Contact contact);
    
    void deleteContact(int id);

    Contact getContactById(int id);
    
    List<ContactSummary> getContacts(ContactSort field, boolean ascending, int startingAtId, int limit);
    
}
