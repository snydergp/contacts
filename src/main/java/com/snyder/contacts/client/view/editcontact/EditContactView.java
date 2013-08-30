package com.snyder.contacts.client.view.editcontact;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.error.ErrorHandler;
import com.sencha.gxt.widget.core.client.form.error.SideErrorHandler;
import com.snyder.contacts.client.model.modifiable.ModifiableContact;
import com.snyder.contacts.client.model.modifiable.ModifiableContact.ModifiableBusiness;
import com.snyder.contacts.client.model.modifiable.ModifiableContact.ModifiablePerson;
import com.snyder.contacts.client.viewmodel.editcontact.EditContactViewModel;
import com.snyder.contacts.shared.model.ContactType;
import com.snyder.gxtstate.ErrorHandlerSynchronizer;
import com.snyder.gxtstate.FieldSynchronizer;
import com.snyder.modifiable.validation.ValidatedApprovedLeafModifiable;
import com.snyder.state.State;
import com.snyder.state.StateObserver;


public class EditContactView implements IsWidget
{
    
    private final EditContactViewModel viewModel;
    private final FlowLayoutContainer view = new FlowLayoutContainer();
    
    private final FlowLayoutContainer subtypeContainer = new FlowLayoutContainer();
    private PersonView personView;
    private BusinessView businessView;
    
    public EditContactView(EditContactViewModel viewModel)
    {
        this.viewModel = viewModel;
        
        ModifiableContact contact = viewModel.getContact();
        
        Label header = new Label("Edit Contact"); // TODO change to "Edit $NAME"
        //TODO style
        view.add(header);
        
        view.add(subtypeContainer);
        contact.getType().addObserver(new TypeObserver(), true);

        
    }

    @Override
    public Widget asWidget()
    {
        return view;
    }
    
    private class TypeObserver implements StateObserver<ContactType>
    {

        @Override
        public void stateChanged(State<? extends ContactType> state, ContactType oldValue,
            ContactType newValue)
        {
            subtypeContainer.clear();
            IsWidget subtypeView;
            switch(newValue)
            {
                case PERSON:
                    if(personView == null)
                    {
                        personView = new PersonView(viewModel.getContact().getPerson());
                    }
                    subtypeView = personView;
                    break;
                case BUSINESS:
                    if(businessView == null)
                    {
                        businessView = new BusinessView(viewModel.getContact().getBusiness());
                    }
                    subtypeView = businessView;
                    break;
                default:
                    throw new IllegalStateException("Unknown contact subtype");
            }
            subtypeContainer.add(subtypeView);
        }
        
    }
    
    private class PersonView implements IsWidget
    {
        
        private final FlowLayoutContainer view = new FlowLayoutContainer();
        
        public PersonView(ModifiablePerson person)
        {
            FieldContainer fields = new FieldContainer();
            view.add(fields);
            
            ValidatedApprovedLeafModifiable<String> firstName = person.getFirstName();
            TextField firstNameField = new TextField();
            FieldSynchronizer.sync(firstName, firstNameField);
            ErrorHandler firstNameErrors = new SideErrorHandler(firstNameField);
            ErrorHandlerSynchronizer.sync(firstNameErrors, firstName.getValidator());
            fields.addField("First Name", firstNameField);

            ValidatedApprovedLeafModifiable<String> middleInitial = person.getMiddleInitial();
            TextField middleInitialField = new TextField();
            FieldSynchronizer.sync(middleInitial, middleInitialField);
            ErrorHandler middleInitialErrors = new SideErrorHandler(middleInitialField);
            ErrorHandlerSynchronizer.sync(middleInitialErrors, middleInitial.getValidator());
            fields.addField("Middle Initial", middleInitialField);

            ValidatedApprovedLeafModifiable<String> lastName = person.getLastName();
            TextField lastNameField = new TextField();
            FieldSynchronizer.sync(lastName, lastNameField);
            ErrorHandler lastNameErrors = new SideErrorHandler(lastNameField);
            ErrorHandlerSynchronizer.sync(lastNameErrors, lastName.getValidator());
            fields.addField("Last Name", lastNameField);
        }

        @Override
        public Widget asWidget()
        {
            return view;
        }
        
    }
    
    private class BusinessView implements IsWidget
    {
        
        private final FlowLayoutContainer view = new FlowLayoutContainer();
        
        public BusinessView(ModifiableBusiness business)
        {
            FieldContainer fields = new FieldContainer();
            view.add(fields);

            ValidatedApprovedLeafModifiable<String> name = business.getName();
            TextField nameField = new TextField();
            FieldSynchronizer.sync(name, nameField);
            ErrorHandler nameErrors = new SideErrorHandler(nameField);
            ErrorHandlerSynchronizer.sync(nameErrors, name.getValidator());
            fields.addField("Name", nameField);
        }

        @Override
        public Widget asWidget()
        {
            return view;
        }
        
    }
    
    
    
}
