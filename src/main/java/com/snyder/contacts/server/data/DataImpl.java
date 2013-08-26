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
	
	protected TransactionContext getTransactionContext() throws DataException
	{
		try
        {
	        return new TransactionContext();
        }
        catch (SQLException e)
        {
	        throw new DataException(e);
        }
	}
	
	protected class TransactionContext
	{
		
		private final Connection connection;
		private final DSLContext context;
		
		public TransactionContext() throws SQLException
        {
	        connection = dataSource.getConnection();
	        connection.setAutoCommit(false);
	        context = DSL.using(connection, dialect);
        }

		public DSLContext getContext()
		{
			return context;
		}
		
		public void commit() throws DataException
		{
			try
            {
	            connection.commit();
            }
	        catch (SQLException e)
	        {
		        throw new DataException(e);
	        }
		}
		
		public void rollback() throws DataException
		{
			try
            {
	            connection.rollback();
            }
	        catch (SQLException e)
	        {
		        throw new DataException(e);
	        }
		}
		
		public void close() throws DataException
		{
			try
            {
	            connection.close();
            }
	        catch (SQLException e)
	        {
		        throw new DataException(e);
	        }
		}
		
	}
	
}
