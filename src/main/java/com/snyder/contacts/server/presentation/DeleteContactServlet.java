package com.snyder.contacts.server.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.snyder.contacts.server.domain.ContactsDomain;


public class DeleteContactServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private final ContactsDomain domain;

    @Inject
    public DeleteContactServlet(ContactsDomain domain)
    {
        this.domain = domain;
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