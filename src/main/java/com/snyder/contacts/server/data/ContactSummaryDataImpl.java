package com.snyder.contacts.server.data;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.contacts.shared.model.ContactSummaryImpl;
import com.snyder.contacts.shared.model.ContactType;


public class ContactSummaryDataImpl implements ContactSummaryData
{
    
    private static final String TYPE_FIELD_NAME = "type";
    
    private static final org.jooq.h2.generated.tables.Person PERSON =
        org.jooq.h2.generated.tables.Person.PERSON;
    private static final Field<Integer> PERSON_ID =
        org.jooq.h2.generated.tables.Person.PERSON.CONTACT_ID;
    private static final Field<String> FIRST_NAME =
        org.jooq.h2.generated.tables.Person.PERSON.FIRST_NAME;
    private static final Field<String> MIDDLE_INITIAL =
        org.jooq.h2.generated.tables.Person.PERSON.MIDDLE_INITIAL;
    private static final Field<String> LAST_NAME =
        org.jooq.h2.generated.tables.Person.PERSON.LAST_NAME;
    
    private static final org.jooq.h2.generated.tables.Business BUSINESS = 
        org.jooq.h2.generated.tables.Business.BUSINESS;
    private static final Field<Integer> CONTACT_ID = BUSINESS.CONTACT_ID;
    private static final Field<String> NAME = BUSINESS.NAME;

    @Override
    public List<ContactSummary> getContacts(DSLContext context)
    {
        // Build a field constant to mark person records
        Param<String> personCode = DSL.param(TYPE_FIELD_NAME, String.class);
        personCode.setValue(ContactType.PERSON.toCode());
        
        // Build the person select
        SelectJoinStep<Record5<Integer, String, String, String, String>> personSelect = 
            context.select(PERSON_ID, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, personCode)
                .from(PERSON);
        
        // Set up the business name fields to alias to the person field names (w/ M.I. & LAST as "")
        Field<String> firstName = NAME.as(FIRST_NAME.getName());
        Param<String> middleInitial = DSL.param(MIDDLE_INITIAL.getName(), String.class);
        middleInitial.setValue("");
        Param<String> lastName = DSL.param(LAST_NAME.getName(), String.class);
        lastName.setValue("");

        // Build a field constant to mark business records
        Param<String> businessCode = DSL.param(TYPE_FIELD_NAME, String.class);
        personCode.setValue(ContactType.BUSINESS.toCode());
        
        // Build the business select, with fields aliased to match the person select
        SelectJoinStep<Record5<Integer, String, String, String, String>> businessSelect = 
            context.select(CONTACT_ID, firstName, middleInitial, lastName, businessCode)
                .from(BUSINESS);
        
        // Union the buiness and person selects
        Select<Record5<Integer, String, String, String, String>> union = 
            personSelect.union(businessSelect);
        
        // Execute the union select
        Result<Record5<Integer, String, String, String, String>> results = union.fetch();
        
        return processResults(results);
    }
    
    /**
     * Process the results ({contactId, firstName, middleInitial, lastName, type} tuples) into a 
     * list of ContactSummary objects
     * 
     * @param results
     * @return
     */
    private List<ContactSummary> processResults(
        Result<Record5<Integer, String, String, String, String>> results)
    {
        List<ContactSummary> out = new ArrayList<ContactSummary>();
        for(Record5<Integer, String, String, String, String> result: results)
        {
            ContactSummaryImpl summary = new ContactSummaryImpl();
            summary.setId(result.value1());
            summary.setFirstName(result.value2());
            summary.setMiddleInitial(result.value3());
            summary.setLastName(result.value4());
            summary.setType(ContactType.fromCode(result.value5()));
            out.add(summary);
        }
        return out;
    }
     
}
