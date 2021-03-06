/**
 * This class is generated by jOOQ
 */
package org.jooq.h2.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value = { "http://www.jooq.org", "3.1.0" },
    comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Business extends
    org.jooq.impl.TableImpl<org.jooq.h2.generated.tables.records.BusinessRecord>
{

	private static final long serialVersionUID = -2084355529;

	/**
	 * The singleton instance of <code>CONTACTS_DATA.BUSINESS</code>
	 */
	public static final org.jooq.h2.generated.tables.Business BUSINESS =
	    new org.jooq.h2.generated.tables.Business();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.h2.generated.tables.records.BusinessRecord> getRecordType()
	{
		return org.jooq.h2.generated.tables.records.BusinessRecord.class;
	}

	/**
	 * The column <code>CONTACTS_DATA.BUSINESS.CONTACT_ID</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.BusinessRecord, java.lang.Integer> CONTACT_ID =
	    createField("CONTACT_ID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>CONTACTS_DATA.BUSINESS.NAME</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.BusinessRecord, java.lang.String> NAME =
	    createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(128), this);

	/**
	 * Create a <code>CONTACTS_DATA.BUSINESS</code> table reference
	 */
	public Business()
	{
		super("BUSINESS", org.jooq.h2.generated.ContactsData.CONTACTS_DATA);
	}

	/**
	 * Create an aliased <code>CONTACTS_DATA.BUSINESS</code> table reference
	 */
	public Business(java.lang.String alias)
	{
		super(alias, org.jooq.h2.generated.ContactsData.CONTACTS_DATA,
		    org.jooq.h2.generated.tables.Business.BUSINESS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.BusinessRecord> getPrimaryKey()
	{
		return org.jooq.h2.generated.Keys.CONSTRAINT_E;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.BusinessRecord>> getKeys()
	{
		return java.util.Arrays
		    .<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.BusinessRecord>> asList(org.jooq.h2.generated.Keys.CONSTRAINT_E);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<org.jooq.h2.generated.tables.records.BusinessRecord, ?>> getReferences()
	{
		return java.util.Arrays
		    .<org.jooq.ForeignKey<org.jooq.h2.generated.tables.records.BusinessRecord, ?>> asList(org.jooq.h2.generated.Keys.CONSTRAINT_EA);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.h2.generated.tables.Business as(java.lang.String alias)
	{
		return new org.jooq.h2.generated.tables.Business(alias);
	}
}
