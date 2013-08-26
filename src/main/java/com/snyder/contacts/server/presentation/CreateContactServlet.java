package com.snyder.contacts.server.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.snyder.contacts.model.Business;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.ContactType;
import com.snyder.contacts.model.Person;
import com.snyder.contacts.server.domain.ContactsDomain;


public class CreateContactServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final Gson GSON = new Gson();
    
    private final ContactsDomain domain;

    @Inject
    public CreateContactServlet(ContactsDomain domain)
    {
        this.domain = domain;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException
    {
        String contactJson = req.getParameter("contact");
        ContactType type = ContactType.fromCode("contactTypeCode");
        
        Contact contact;
        switch(type)
        {
            case PERSON:
                contact = GSON.fromJson(contactJson, Person.class);
                break;
            case BUSINESS:
                contact = GSON.fromJson(contactJson, Business.class);
                break;
            default:
                //TODO encode failure to client
                throw new IllegalArgumentException();
        }
        
        domain.createContact(contact);
        
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    
}