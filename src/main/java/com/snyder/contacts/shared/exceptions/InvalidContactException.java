package com.snyder.contacts.shared.exceptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Thrown when attempting to save an invalid contact
 * 
 * @author SnyderGP
 */
public class InvalidContactException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    private final List<String> errors = new ArrayList<String>();
    
    public InvalidContactException(List<String> errors)
    {
        errors.addAll(errors);
    }
    
    /**
     * @return the list of validation errors associated with the contact
     */
    public List<String> getErrors()
    {
        return new ArrayList<String>(errors);
    }
    
}
