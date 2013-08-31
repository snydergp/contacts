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
public class Address extends
    org.jooq.impl.TableImpl<org.jooq.h2.generated.tables.records.AddressRecord>
{

	private static final long serialVersionUID = 1483033686;

	/**
	 * The singleton instance of <code>CONTACTS_DATA.ADDRESS</code>
	 */
	public static final org.jooq.h2.generated.tables.Address ADDRESS =
	    new org.jooq.h2.generated.tables.Address();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.h2.generated.tables.records.AddressRecord> getRecordType()
	{
		return org.jooq.h2.generated.tables.records.AddressRecord.class;
	}

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.ADDRESS_ID</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.Integer> ADDRESS_ID =
	    createField("ADDRESS_ID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.CONTACT_ID</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.Integer> CONTACT_ID =
	    createField("CONTACT_ID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.ADDRESS_TYPE</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> ADDRESS_TYPE =
	    createField("ADDRESS_TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(32), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.LINE_1</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> LINE_1 =
	    createField("LINE_1", org.jooq.impl.SQLDataType.VARCHAR.length(128), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.LINE_2</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> LINE_2 =
	    createField("LINE_2", org.jooq.impl.SQLDataType.VARCHAR.length(128), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.LINE_3</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> LINE_3 =
	    createField("LINE_3", org.jooq.impl.SQLDataType.VARCHAR.length(128), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.CITY</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> CITY =
	    createField("CITY", org.jooq.impl.SQLDataType.VARCHAR.length(64), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.STATE_PROVINCE</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> STATE_PROVINCE =
	    createField("STATE_PROVINCE", org.jooq.impl.SQLDataType.CHAR.length(2), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.POSTAL_CODE</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> POSTAL_CODE =
	    createField("POSTAL_CODE", org.jooq.impl.SQLDataType.VARCHAR.length(10), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.COUNTRY</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> COUNTRY =
	    createField("COUNTRY", org.jooq.impl.SQLDataType.VARCHAR.length(32), this);

	/**
	 * The column <code>CONTACTS_DATA.ADDRESS.ADDITIONAL_INFO</code>.
	 */
	public final org.jooq.TableField<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.String> ADDITIONAL_INFO =
	    createField("ADDITIONAL_INFO", org.jooq.impl.SQLDataType.VARCHAR.length(128), this);

	/**
	 * Create a <code>CONTACTS_DATA.ADDRESS</code> table reference
	 */
	public Address()
	{
		super("ADDRESS", org.jooq.h2.generated.ContactsData.CONTACTS_DATA);
	}

	/**
	 * Create an aliased <code>CONTACTS_DATA.ADDRESS</code> table reference
	 */
	public Address(java.lang.String alias)
	{
		super(alias, org.jooq.h2.generated.ContactsData.CONTACTS_DATA,
		    org.jooq.h2.generated.tables.Address.ADDRESS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<org.jooq.h2.generated.tables.records.AddressRecord, java.lang.Integer> getIdentity()
	{
		return org.jooq.h2.generated.Keys.IDENTITY_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.AddressRecord> getPrimaryKey()
	{
		return org.jooq.h2.generated.Keys.CONSTRAINT_E6;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.AddressRecord>> getKeys()
	{
		return java.util.Arrays
		    .<org.jooq.UniqueKey<org.jooq.h2.generated.tables.records.AddressRecord>> asList(org.jooq.h2.generated.Keys.CONSTRAINT_E6);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<org.jooq.h2.generated.tables.records.AddressRecord, ?>> getReferences()
	{
		return java.util.Arrays
		    .<org.jooq.ForeignKey<org.jooq.h2.generated.tables.records.AddressRecord, ?>> asList(org.jooq.h2.generated.Keys.CONSTRAINT_E66);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.h2.generated.tables.Address as(java.lang.String alias)
	{
		return new org.jooq.h2.generated.tables.Address(alias);
	}
}
