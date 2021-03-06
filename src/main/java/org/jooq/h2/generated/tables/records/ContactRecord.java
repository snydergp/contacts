/**
 * This class is generated by jOOQ
 */
package org.jooq.h2.generated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value = { "http://www.jooq.org", "3.1.0" },
    comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactRecord extends
    org.jooq.impl.UpdatableRecordImpl<org.jooq.h2.generated.tables.records.ContactRecord> implements
    org.jooq.Record2<java.lang.Integer, java.lang.String>
{

	private static final long serialVersionUID = -1245339741;

	/**
	 * Setter for <code>CONTACTS_DATA.CONTACT.CONTACT_ID</code>.
	 */
	public void setContactId(java.lang.Integer value)
	{
		setValue(0, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.CONTACT.CONTACT_ID</code>.
	 */
	public java.lang.Integer getContactId()
	{
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.CONTACT.INFO</code>.
	 */
	public void setInfo(java.lang.String value)
	{
		setValue(1, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.CONTACT.INFO</code>.
	 */
	public java.lang.String getInfo()
	{
		return (java.lang.String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key()
	{
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> fieldsRow()
	{
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> valuesRow()
	{
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1()
	{
		return org.jooq.h2.generated.tables.Contact.CONTACT.CONTACT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2()
	{
		return org.jooq.h2.generated.tables.Contact.CONTACT.INFO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1()
	{
		return getContactId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2()
	{
		return getInfo();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ContactRecord
	 */
	public ContactRecord()
	{
		super(org.jooq.h2.generated.tables.Contact.CONTACT);
	}
}
