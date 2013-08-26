/**
 * 
 */
package com.snyder.contacts.server.data;

import org.jooq.DSLContext;

import com.snyder.contacts.model.Business;

/**
 * 
 * @author greg
 */
public interface BusinessData
{

	void insertBusiness(DSLContext context, Business business);
	
	Business getBusiness(DSLContext context, int contactId);

	void updateBusiness(DSLContext context, Business business);
	
	void deleteBusiness(DSLContext context, int contactId);
	
}
