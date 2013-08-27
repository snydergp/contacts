package com.snyder.contacts.server.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.server.domain.ContactsDomain;


public class GetContactServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final Gson GSON = new Gson();
    
    private final ContactsDomain domain;

    @Inject
    public GetContactServlet(ContactsDomain domain)
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
    
}
