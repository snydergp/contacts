package com.snyder.contacts.server.domain;

import java.util.List;

import com.snyder.contacts.server.exceptions.InvalidContactException;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactSummary;


public interface ContactsDomain
{
    
    int createContact(Contact contact) throws InvalidContactException;
    
    void updateContact(Contact contact) throws InvalidContactException;
    
    void deleteContact(int id);

    Contact getContactById(int id);
    
    List<ContactSummary> getContacts();
    
}
