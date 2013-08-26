package com.snyder.contacts.server.data;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SortField;
import org.jooq.h2.generated.tables.NameByFirst;
import org.jooq.h2.generated.tables.NameByLast;

import com.snyder.contacts.model.ContactSort;
import com.snyder.contacts.model.ContactSummary;
import com.snyder.contacts.model.ContactSummaryImpl;
import com.snyder.contacts.model.ContactType;


public class ContactSummaryDataImpl implements ContactSummaryData
{
    
    private static final NameByFirst NAME_BY_FIRST = NameByFirst.NAME_BY_FIRST;
    private static final Field<Integer> F_ID = NameByFirst.NAME_BY_FIRST.CONTACT_ID;
    private static final Field<String> F_NAME = NameByFirst.NAME_BY_FIRST.NAME;
    private static final Field<String> F_TYPE = NameByFirst.NAME_BY_FIRST.TYPE;
    
    private static final NameByLast NAME_BY_LAST = NameByLast.NAME_BY_LAST;
    private static final Field<Integer> L_ID = NameByLast.NAME_BY_LAST.CONTACT_ID;
    private static final Field<String> L_NAME = NameByLast.NAME_BY_LAST.NAME;
    private static final Field<String> L_TYPE = NameByLast.NAME_BY_LAST.TYPE;

    @Override
    public List<ContactSummary> getContacts(DSLContext context, ContactSort field, 
        boolean ascending, int limit)
    {
        SelectJoinStep<Record3<Integer, String, String>> selectJoin = getSelectJoin(context, field);
        SelectLimitStep<Record3<Integer, String, String>> selectLimit = 
            getSelectLimit(selectJoin, field, ascending);
        Result<Record3<Integer, String, String>> results = selectLimit.limit(limit).fetch();
        return processResults(results);
    }

    @Override
    public List<ContactSummary> getContacts(DSLContext context, ContactSort field, 
        boolean ascending, int offset, int limit)
    {
        SelectJoinStep<Record3<Integer, String, String>> selectJoin = getSelectJoin(context, field);
        SelectLimitStep<Record3<Integer, String, String>> selectLimit = 
            getSelectLimit(selectJoin, field, ascending);
        Result<Record3<Integer, String, String>> results = selectLimit.limit(offset, limit).fetch();
        return processResults(results);
    }
    
    /**
     * Get the SelectJoinStep instance, which declared the fields and table from which data is 
     * selected.
     * 
     * @param context
     * @param sort
     * @return
     */
    private SelectJoinStep<Record3<Integer, String, String>> getSelectJoin(DSLContext context, 
        ContactSort sort)
    {
        switch(sort)
        {
            case FIRST_NAME:
                return context.select(F_ID, F_NAME, F_TYPE).from(NAME_BY_FIRST);
            case LAST_NAME:
                return context.select(L_ID, L_NAME, L_TYPE).from(NAME_BY_LAST);
            default:
                throw new IllegalStateException();
        }
    }
    
    /**
     * Get the SelectLimitStep, which expands on the SelectJoinStep parameter with a sort 
     * specification.
     * 
     * @param selectJoin
     * @param sort
     * @param ascending
     * @return
     */
    private SelectLimitStep<Record3<Integer, String, String>> getSelectLimit(
        SelectJoinStep<Record3<Integer, String, String>> selectJoin, ContactSort sort, 
        boolean ascending)
    {
        Field<String> field;
        switch(sort)
        {
            case FIRST_NAME:
                field = F_NAME;
                break;
            case LAST_NAME:
                field = L_NAME;
                break;
            default:
                throw new IllegalStateException();
        }
        
        SortField<String> sortField = ascending ? field.asc() : field.desc();
        
        return selectJoin.orderBy(sortField);
    }
    
    /**
     * Process the results ({contactId, name, type} tuples) into a list of ContactSummary objects
     * 
     * @param results
     * @return
     */
    private List<ContactSummary> processResults(Result<Record3<Integer, String, String>> results)
    {
        List<ContactSummary> out = new ArrayList<ContactSummary>();
        for(Record3<Integer, String, String> result: results)
        {
            ContactSummaryImpl summary = new ContactSummaryImpl();
            summary.setId(result.value1());
            summary.setDisplayName(result.value2());
            summary.setType(ContactType.fromCode(result.value3()));
            out.add(summary);
        }
        return out;
    }
     
}
