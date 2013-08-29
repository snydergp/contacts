package com.snyder.contacts.server.presentation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snyder.contacts.server.domain.ContactsDomain;
import com.snyder.contacts.shared.model.ContactSummary;


@Singleton
public class ContactSummaryServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final Gson GSON = new Gson();
    
    private final ContactsDomain domain;

    @Inject
    public ContactSummaryServlet(ContactsDomain domain)
    {
        this.domain = domain;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException
    {
        List<ContactSummary> summaries = domain.getContacts();
        
        String summariesJson = 
            GSON.toJson(summaries, TypeToken.get(ContactSummary.class).getType());
        
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentLength(summariesJson.length());
        resp.getWriter().append(summariesJson);
    }
    
}
