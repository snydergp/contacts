/**
 * This class is generated by jOOQ
 */
package org.jooq.h2.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Contact extends org.jooq.impl.TableImpl<org.jooq.h2.generated.tables.records.ContactRecord> {

	private static final long serialVersionUID = 1073862077;

	/**
	 * The singleton instance of <code>CONTACTS_DATA.CONTACT</code>
	 */
	public static final org.jooq.h2.generated.tables.Contact CONTACT = new org.jooq.h2.generated.tables.Contact();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.h2.generated.tables.records.ContactRecord> getRecordType() {
		return org.jooq.h2.generated.tables.records.ContactRecord.class;
	}

	/**
	 * The column <code>CONTACTS_DATA.CONTACT.CONTACT_ID</code>. 
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.ContactRecord, java.lang.Integer> CONTACT_ID = createField("CONTACT_ID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>CONTACTS_DATA.CONTACT.INFO</code>. 
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.ContactRecord, java.lang.String> INFO = createField("INFO", org.jooq.impl.SQLDataType.CLOB.length(2147483647), this);

	/**
	 * Create a <code>CONTACTS_DATA.CONTACT</code> table reference
	 */
	public Contact() {
		super("CONTACT", org.jooq.h2.generated.ContactsData.CONTACTS_DATA);
	}

	/**
	 * Create an aliased <code>CONTACTS_DATA.CONTACT</code> table reference
	 */
	public Contact(java.lang.String alias) {
		super(alias, org.jooq.h2.generated.ContactsData.CONTACTS_DATA, org.jooq.h2.generated.tables.Contact.CONTACT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<org.jooq.h2.generated.tables.records.ContactRecord, java.lang.Integer> getIdentity() {
		return org.jooq.h2.generated.Keys.IDENTITY_CONTACT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.ContactRecord> getPrimaryKey() {
		return org.jooq.h2.generated.Keys.CONSTRAINT_6;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.ContactRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.ContactRecord>>asList(org.jooq.h2.generated.Keys.CONSTRAINT_6);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.h2.generated.tables.Contact as(java.lang.String alias) {
		return new org.jooq.h2.generated.tables.Contact(alias);
	}
}
