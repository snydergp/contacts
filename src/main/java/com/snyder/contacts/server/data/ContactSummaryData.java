package com.snyder.contacts.server.data;

import java.util.List;

import org.jooq.DSLContext;

import com.snyder.contacts.shared.model.ContactSummary;

public interface ContactSummaryData
{

	List<ContactSummary> getContacts(DSLContext context);

}
