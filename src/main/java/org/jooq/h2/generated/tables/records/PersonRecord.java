/**
 * This class is generated by jOOQ
 */
package org.jooq.h2.generated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PersonRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.h2.generated.tables.records.PersonRecord> implements org.jooq.Record5<java.lang.Integer, java.lang.Short, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -1850585156;

	/**
	 * Setter for <code>CONTACTS_DATA.PERSON.CONTACT_ID</code>. 
	 */
	public void setContactId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.PERSON.CONTACT_ID</code>. 
	 */
	public java.lang.Integer getContactId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.PERSON.TITLE_CD</code>. 
	 */
	public void setTitleCd(java.lang.Short value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.PERSON.TITLE_CD</code>. 
	 */
	public java.lang.Short getTitleCd() {
		return (java.lang.Short) getValue(1);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.PERSON.FIRST_NAME</code>. 
	 */
	public void setFirstName(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.PERSON.FIRST_NAME</code>. 
	 */
	public java.lang.String getFirstName() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.PERSON.MIDDLE_INITIAL</code>. 
	 */
	public void setMiddleInitial(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.PERSON.MIDDLE_INITIAL</code>. 
	 */
	public java.lang.String getMiddleInitial() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>CONTACTS_DATA.PERSON.LAST_NAME</code>. 
	 */
	public void setLastName(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>CONTACTS_DATA.PERSON.LAST_NAME</code>. 
	 */
	public java.lang.String getLastName() {
		return (java.lang.String) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.Short, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.Short, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.jooq.h2.generated.tables.Person.PERSON.CONTACT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field2() {
		return org.jooq.h2.generated.tables.Person.PERSON.TITLE_CD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.jooq.h2.generated.tables.Person.PERSON.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return org.jooq.h2.generated.tables.Person.PERSON.MIDDLE_INITIAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return org.jooq.h2.generated.tables.Person.PERSON.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getContactId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Short value2() {
		return getTitleCd();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getMiddleInitial();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getLastName();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PersonRecord
	 */
	public PersonRecord() {
		super(org.jooq.h2.generated.tables.Person.PERSON);
	}
}
