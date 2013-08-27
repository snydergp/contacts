package com.snyder.contacts.server.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.snyder.contacts.server.domain.ContactsDomain;
import com.snyder.contacts.server.exceptions.InvalidContactException;
import com.snyder.contacts.shared.model.Business;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactType;
import com.snyder.contacts.shared.model.Person;


public class ContactServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final Gson GSON = new Gson();
    
    private final ContactsDomain domain;

    @Inject
    public ContactServlet(ContactsDomain domain)
    {
        this.domain = domain;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException
    {
        int contactId = Integer.parseInt(req.getParameter("contactId"));
        
        Contact contact = domain.getContactById(contactId);
        //TODO add ContactNotFound exception and make interface NonNull
        
        String contactJson = GSON.toJson(contact);
        
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentLength(contactJson.length());
        resp.getWriter().append(contactJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException
    {
        String contactJson = req.getParameter("contact");
        ContactType type = ContactType.fromCode("contactTypeCode");
        
        Contact contact;
        try
        {
            switch(type)
            {
                case PERSON:
                    contact = GSON.fromJson(contactJson, Person.class);
                    break;
                case BUSINESS:
                    contact = GSON.fromJson(contactJson, Business.class);
                    break;
                default:
                    throw new Exception("Unknown contactTypeCode parameter");
            }
        }
        catch (Exception e)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType(MediaType.JSON_UTF_8.subtype());
            resp.getWriter().append("[" + e.getMessage() + "]"); // errors return JSON string array
            return;
        }
        
        
        try
        {
            if(contact.getId() < 0)
            {
                int id = domain.createContact(contact);
                resp.getWriter().append(Integer.toString(id));
            }
            else
            {
                domain.updateContact(contact);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        catch (InvalidContactException e)
        {
            String errorsJson = GSON.toJson(e.getErrors(), TypeToken.get(String.class).getType());
            
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentLength(errorsJson.length());
            resp.setContentType(MediaType.JSON_UTF_8.subtype());
            resp.getWriter().append(errorsJson);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        int contactId = Integer.parseInt(req.getParameter("contactId"));
        
        domain.deleteContact(contactId);
        
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    
}
