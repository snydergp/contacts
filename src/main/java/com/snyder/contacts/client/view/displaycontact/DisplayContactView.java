package com.snyder.contacts.client.view.displaycontact;

import com.google.common.base.Strings;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.snyder.contacts.client.viewmodel.displaycontact.DisplayContactViewModel;
import com.snyder.contacts.shared.model.Address;
import com.snyder.contacts.shared.model.Business;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.EmailAddress;
import com.snyder.contacts.shared.model.Person;
import com.snyder.contacts.shared.model.PhoneNumber;

public class DisplayContactView implements IsWidget
{

	private static final Resources RESOURCES = GWT.create(Resources.class);

	public static interface Resources extends ClientBundle
	{

		ImageResource edit();

		@Source("DisplayContactView.css")
		Css css();

	}

	public static interface Css extends CssResource
	{

		String actionContainer();

		String actionContainerItem();

		String typeContentTypeLabel();

		String typeContentContentContainer();

	}

	/**
	 * A view that displayed "typed" content, like addresses, email, and phone, where "type" is
	 * generally a value like "HOME", "WORK", "FAX", etc. This view lays out the type value on the
	 * left, then provides a content panel on the right (right-aligned) that subtypes will populate.
	 * The height of this view will grow along with the content panel.
	 * 
	 * @author SnyderGP
	 */
	private static class TypedContentView implements IsWidget
	{

		private final FlowLayoutContainer view = new FlowLayoutContainer();
		private final FlowLayoutContainer content = new FlowLayoutContainer();

		public TypedContentView(String type)
		{
			view.add(content);
			Label typeLabel = new Label(type);
			view.add(typeLabel);
			typeLabel.addStyleName(RESOURCES.css().typeContentTypeLabel());
			content.addStyleName(RESOURCES.css().typeContentContentContainer());
		}

		@Override
		public final Widget asWidget()
		{
			return view;
		}

		protected final FlowLayoutContainer getContentContainer()
		{
			return content;
		}

	}

	private static class AddressView extends TypedContentView
	{

		public AddressView(Address address)
		{
			super(address.getAddressType());
			// TODO layout address
		}

	}

	private static class EmailAddressView extends TypedContentView
	{

		public EmailAddressView(EmailAddress emailAddress)
		{
			super(emailAddress.getType());
			Label emailLabel = new Label(emailAddress.getEmail());
			this.getContentContainer().add(emailLabel);
		}

	}

	private static class PhoneNumberView extends TypedContentView
	{

		public PhoneNumberView(PhoneNumber phoneNumber)
		{
			super(phoneNumber.getType());
			Label phoneLabel = new Label(phoneNumber.getNumber());
			this.getContentContainer().add(phoneLabel);
		}

	}

	private final DisplayContactViewModel viewModel;
	private final FlowLayoutContainer view = new FlowLayoutContainer();

	private final Label nameLabel = new Label();
	private final FlowLayoutContainer addressContainer = new FlowLayoutContainer();
	private final FlowLayoutContainer emailAddressContainer = new FlowLayoutContainer();
	private final FlowLayoutContainer phoneNumberContainer = new FlowLayoutContainer();

	public DisplayContactView(DisplayContactViewModel viewModel)
	{
		this.viewModel = viewModel;

		this.renderContact(viewModel.getDisplayedContact());
	}

	@Override
	public Widget asWidget()
	{
		return view;
	}

	private void renderContact(Contact contact)
	{
		if (contact instanceof Person)
		{
			this.renderPerson((Person) contact);
		}
		else if (contact instanceof Business)
		{
			this.renderBusiness((Business) contact);
		}

		for (Address address : contact.getAddresses())
		{
			addressContainer.add(new AddressView(address));
		}

		for (EmailAddress emailAddress : contact.getEmailAddresses())
		{
			emailAddressContainer.add(new EmailAddressView(emailAddress));
		}

		for (PhoneNumber phoneNumber : contact.getPhoneNumbers())
		{
			phoneNumberContainer.add(new PhoneNumberView(phoneNumber));
		}
	}

	private void renderPerson(Person person)
	{
		StringBuilder name = new StringBuilder();
		if (!Strings.isNullOrEmpty(person.getFirstName()))
		{
			name.append(person.getFirstName()).append(' ');
		}
		if (!Strings.isNullOrEmpty(person.getMiddleInitial()))
		{
			name.append(person.getMiddleInitial()).append(". ");
		}
		if (!Strings.isNullOrEmpty(person.getLastName()))
		{
			name.append(person.getLastName());
		}
		nameLabel.setText(name.toString());
	}

	private void renderBusiness(Business business)
	{
		nameLabel.setText(business.getName());
	}

	private class ActionContainer implements IsWidget
	{

		private final FlowLayoutContainer view = new FlowLayoutContainer();

		public ActionContainer()
		{
			Image edit = new Image(RESOURCES.edit());
			edit.addClickHandler(new EditSelectHandler());
			edit.addStyleName(RESOURCES.css().actionContainerItem());
			view.add(edit);
			view.addStyleName(RESOURCES.css().actionContainer());
		}

		@Override
		public Widget asWidget()
		{
			return view;
		}

	}

	private class EditSelectHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event)
		{
			viewModel.edit();
		}

	}

}
