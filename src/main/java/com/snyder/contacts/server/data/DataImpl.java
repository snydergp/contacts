/**
 * 
 */
package com.snyder.contacts.server.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * 
 * @author greg
 */
public abstract class DataImpl 
{

	private final DataSource dataSource;
	private final SQLDialect dialect;
	
	public DataImpl(DataSource dataSource, SQLDialect dialect)
	{
		this.dataSource = dataSource;
		this.dialect = dialect;
	}
	
	protected DSLContext getDSLContext() throws DataException
	{
		try 
		{
			Connection connection = dataSource.getConnection();
			return DSL.using(connection, dialect);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DataException(e);
		}
	}
	
}
