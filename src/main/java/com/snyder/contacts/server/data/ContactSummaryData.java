package com.snyder.contacts.server.data;

import java.util.List;

import org.jooq.DSLContext;

import com.snyder.contacts.model.ContactSort;
import com.snyder.contacts.model.ContactSummary;


public interface ContactSummaryData
{
    
    List<ContactSummary> getContacts(DSLContext context, ContactSort field, boolean ascending, 
        int limit);
    
    List<ContactSummary> getContacts(DSLContext context, ContactSort field, boolean ascending, 
        int startingAt, int limit);
    
}
