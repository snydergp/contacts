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
public class EmailRecord extends
    org.jooq.impl.UpdatableRecordImpl<org.jooq.h2.generated.tables.records.EmailRecord> implements
    org.jooq.Record4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String>
{

	private static final long serialVersionUID = -980538516;

	/**
	 * Setter for <code>CONTACTS_DATA.EMAIL.EMAIL_ID</code>.
	 */
	public void setEmailId(java.lang.Integer value)
	{
		setValue(0, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.EMAIL.EMAIL_ID</code>.
	 */
	public java.lang.Integer getEmailId()
	{
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.EMAIL.CONTACT_ID</code>.
	 */
	public void setContactId(java.lang.Integer value)
	{
		setValue(1, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.EMAIL.CONTACT_ID</code>.
	 */
	public java.lang.Integer getContactId()
	{
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.EMAIL.EMAIL_TYPE</code>.
	 */
	public void setEmailType(java.lang.String value)
	{
		setValue(2, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.EMAIL.EMAIL_TYPE</code>.
	 */
	public java.lang.String getEmailType()
	{
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.EMAIL.EMAIL</code>.
	 */
	public void setEmail(java.lang.String value)
	{
		setValue(3, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.EMAIL.EMAIL</code>.
	 */
	public java.lang.String getEmail()
	{
		return (java.lang.String) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String> fieldsRow()
	{
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String> valuesRow()
	{
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1()
	{
		return org.jooq.h2.generated.tables.Email.EMAIL.EMAIL_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2()
	{
		return org.jooq.h2.generated.tables.Email.EMAIL.CONTACT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3()
	{
		return org.jooq.h2.generated.tables.Email.EMAIL.EMAIL_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4()
	{
		return org.jooq.h2.generated.tables.Email.EMAIL.EMAIL_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1()
	{
		return getEmailId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2()
	{
		return getContactId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3()
	{
		return getEmailType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4()
	{
		return getEmail();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EmailRecord
	 */
	public EmailRecord()
	{
		super(org.jooq.h2.generated.tables.Email.EMAIL);
	}
}
