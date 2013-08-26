package com.snyder.contacts.server.data;

/**
 * An exception indicating a data source failure
 * @author SnyderGP
 */
public class DataException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public DataException() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DataException(String arg0, Throwable arg1) 
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DataException(String arg0) 
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DataException(Throwable arg0) 
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}