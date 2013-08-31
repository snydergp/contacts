package com.snyder.contacts.server.presentation;

import com.google.inject.servlet.ServletModule;

public class ServletConfigModule extends ServletModule
{

	@Override
	protected void configureServlets()
	{
		serve("/contact/*").with(ContactServlet.class);
		serve("/contact-summary/*").with(ContactSummaryServlet.class);
	}

}
