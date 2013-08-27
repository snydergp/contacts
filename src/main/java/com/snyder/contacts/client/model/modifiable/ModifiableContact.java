package com.snyder.contacts.client.model.modifiable;

import java.util.EnumMap;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.snyder.contacts.shared.model.Address;
import com.snyder.contacts.shared.model.AddressImpl;
import com.snyder.contacts.shared.model.Business;
import com.snyder.contacts.shared.model.BusinessImpl;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactImpl;
import com.snyder.contacts.shared.model.ContactType;
import com.snyder.contacts.shared.model.EmailAddress;
import com.snyder.contacts.shared.model.EmailAddressImpl;
import com.snyder.contacts.shared.model.Person;
import com.snyder.contacts.shared.model.PersonImpl;
import com.snyder.contacts.shared.model.PhoneNumber;
import com.snyder.contacts.shared.model.PhoneNumberImpl;
import com.snyder.contacts.shared.model.validation.BusinessValidation;
import com.snyder.contacts.shared.model.validation.ContactValidation;
import com.snyder.contacts.shared.model.validation.PersonValidation;
import com.snyder.modifiable.Modifiable;
import com.snyder.modifiable.approved.ModificationApprover;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;
import com.snyder.modifiable.validation.ValidatedApprovedLeafModifiable;
import com.snyder.review.shared.validator.algorithm.NullValidation;
import com.snyder.state.BaseMutableState;
import com.snyder.state.MutableState;
import com.snyder.state.State;
import com.snyder.state.StateObserver;


public class ModifiableContact extends ValidatedApprovedCompositeModifiable<Contact>
{
    
    private final MutableState<ValidatedApprovedCompositeModifiable<? extends Contact>> subtype = 
        new BaseMutableState<ValidatedApprovedCompositeModifiable<? extends Contact>>();
    private final 
    Map<ContactType, ValidatedApprovedCompositeModifiable<? extends Contact>> subtypeMap = 
        new EnumMap<ContactType, ValidatedApprovedCompositeModifiable<? extends Contact>>(
            ContactType.class);
    private final ValidatedApprovedLeafModifiable<ContactType> typeSelector;
    private final ValidatedApprovedLeafModifiable<String> info;
    private final ValidatedListControls<Address, ModifiableAddress> addresses;
    private final ValidatedListControls<PhoneNumber, ModifiablePhoneNumber> phoneNumbers;
    private final ValidatedListControls<EmailAddress, ModifiableEmailAddress> emailAddresses;

    public ModifiableContact(Contact initial, ModificationApprover approver)
    {
        super(initial, approver);
        
        // Build the info modifiable
        info = this.buildLeaf(initial.getInfo(), ContactValidation.INFO);
        
        // Determine the initial subtype and generate both of the subtype POJOs
        ContactType currentType;
        Person person;
        Business business;
        if(initial instanceof Person)
        {
            currentType = ContactType.PERSON;
            person = (Person) initial;
            business = new BusinessImpl();
        }
        else if(initial instanceof Business)
        {
            currentType = ContactType.BUSINESS;
            person = new PersonImpl();
            business = (Business) initial;
        }
        else
        {
            throw new IllegalStateException("Unknown Contact subtype");
        }
        
        // Create the Modifiable for the Person subtype
        ModifiablePerson modifiablePerson = new ModifiablePerson(person, this);
        subtypeMap.put(ContactType.PERSON, modifiablePerson);
        
        // Create the Modifiable for the Business subtype
        ModifiableBusiness modifiableBusiness = new ModifiableBusiness(business, this);
        subtypeMap.put(ContactType.BUSINESS, modifiableBusiness);
        
        // Generate the leaf used to control the selected subtype
        typeSelector = this.buildLeaf(currentType, new NullValidation<ContactType>("Type"));
        
        // Start observing the type selector (performs initialization of subtype member)
        typeSelector.getModifiedState().addObserver(new TypeObserver(), true);
        
        // Build the modifiable address listing
        AddressFunctions addressFunctions = new AddressFunctions();
        addresses = new ValidatedListControls<Address, ModifiableAddress>(initial.getAddresses(), 
            addressFunctions, addressFunctions);
        this.addChild(addresses.getModifiableList());
        
        // Build the modifiable phone number listing
        PhoneNumberFunctions phoneNumberFunctions = new PhoneNumberFunctions();
        phoneNumbers = new ValidatedListControls<PhoneNumber, ModifiablePhoneNumber>(
            initial.getPhoneNumbers(), phoneNumberFunctions, phoneNumberFunctions);
        this.addChild(phoneNumbers.getModifiableList());
        
        // Build the modifiable email address listing
        EmailAddressFunctions emailAddressFunctions = new EmailAddressFunctions();
        emailAddresses = new ValidatedListControls<EmailAddress, ModifiableEmailAddress>(
            initial.getEmailAddresses(), emailAddressFunctions, emailAddressFunctions);
        this.addChild(emailAddresses.getModifiableList());
    }

    @Override
    public Contact getModified()
    {
        Contact initial = getCurrent();
        
        // Obtain the modified instance of the currently-selected subtype
        ContactImpl mod;
        if(typeSelector.getModified() == ContactType.PERSON)
        {
            ModifiablePerson modifiablePerson = (ModifiablePerson) subtype.get();
            mod = new PersonImpl(modifiablePerson.getModified());
        }
        else if(typeSelector.getModified() == ContactType.BUSINESS)
        {
            ModifiableBusiness modifiableBusiness = (ModifiableBusiness) subtype.get();
            mod = new BusinessImpl(modifiableBusiness.getModified());
        }
        else
        {
            throw new IllegalStateException("Unknown contact subtype");
        }
        
        // Set the contact fields
        mod.setId(initial.getId());
        mod.setInfo(info.getModified());
        
        // Set the addresses
        mod.getAddresses().clear();
        mod.getAddresses().addAll(addresses.getModified());
        
        // Set the phone numbers
        mod.getPhoneNumbers().clear();
        mod.getPhoneNumbers().addAll(phoneNumbers.getModified());
        
        // Set the emails
        mod.getEmailAddresses().clear();
        mod.getEmailAddresses().addAll(emailAddresses.getModified());
        
        return mod;
    }
    
    public MutableState<ValidatedApprovedCompositeModifiable<? extends Contact>> getSubtype()
    {
        return subtype;
    }
    
    public ValidatedApprovedLeafModifiable<ContactType> getType()
    {
        return typeSelector;
    }

    public ValidatedApprovedLeafModifiable<String> getInfo()
    {
        return info;
    }
    
    public ValidatedListControls<Address, ModifiableAddress> getAddresses()
    {
        return addresses;
    }
    
    public ValidatedListControls<PhoneNumber, ModifiablePhoneNumber> getPhoneNumbers()
    {
        return phoneNumbers;
    }
    
    public ValidatedListControls<EmailAddress, ModifiableEmailAddress> getEmailAddresses()
    {
        return emailAddresses;
    }

    /**
     * The {@link Modifiable} representing data in the {@link Person} subtype.
     * 
     * @author SnyderGP
     */
    public class ModifiablePerson extends ValidatedApprovedCompositeModifiable<Person>
    {
        
        private final ValidatedApprovedLeafModifiable<String> firstName;
        private final ValidatedApprovedLeafModifiable<String> middleInitial;
        private final ValidatedApprovedLeafModifiable<String> lastName;

        protected ModifiablePerson(Person initial, ModificationApprover approver)
        {
            super(initial, approver);
            firstName = this.buildLeaf(initial.getFirstName(), PersonValidation.FIRST);
            middleInitial = this.buildLeaf(initial.getMiddleInitial(), PersonValidation.MIDDLE);
            lastName = this.buildLeaf(initial.getLastName(), PersonValidation.LAST);
        }

        @Override
        public Person getModified()
        {
            PersonImpl mod = new PersonImpl(getCurrent());
            mod.setFirstName(firstName.getModified());
            mod.setMiddleInitial(middleInitial.getModified());
            mod.setLastName(lastName.getModified());
            return mod;
        }
        
        public ValidatedApprovedLeafModifiable<String> getFirstName()
        {
            return firstName;
        }
        
        public ValidatedApprovedLeafModifiable<String> getMiddleInitial()
        {
            return middleInitial;
        }
        
        public ValidatedApprovedLeafModifiable<String> getLastName()
        {
            return lastName;
        }
        
    }

    /**
     * The {@link Modifiable} representing data in the {@link Business} subtype.
     * 
     * @author SnyderGP
     */
    public class ModifiableBusiness extends ValidatedApprovedCompositeModifiable<Business>
    {
        
        private final ValidatedApprovedLeafModifiable<String> name;

        protected ModifiableBusiness(Business initial, ModificationApprover approver)
        {
            super(initial, approver);
            name = this.buildLeaf(initial.getName(), BusinessValidation.NAME);
        }

        @Override
        public Business getModified()
        {
            BusinessImpl mod = new BusinessImpl(getCurrent());
            mod.setName(name.getModified());
            return mod;
        }
        
        public ValidatedApprovedLeafModifiable<String> getName()
        {
            return name;
        }
        
    }
    
    private class AddressFunctions implements Function<Address, ModifiableAddress>, 
        Supplier<ModifiableAddress>
    {

        @Override
        public ModifiableAddress get()
        {
            return new ModifiableAddress(new AddressImpl(), ModifiableContact.this);
        }

        @Override
        public ModifiableAddress apply(Address input)
        {
            return new ModifiableAddress(input, ModifiableContact.this);
        }
        
    }
    
    private class EmailAddressFunctions implements Function<EmailAddress, ModifiableEmailAddress>, 
        Supplier<ModifiableEmailAddress>
    {

        @Override
        public ModifiableEmailAddress get()
        {
            return new ModifiableEmailAddress(new EmailAddressImpl(), ModifiableContact.this);
        }

        @Override
        public ModifiableEmailAddress apply(EmailAddress input)
        {
            return new ModifiableEmailAddress(input, ModifiableContact.this);
        }
        
    }
    
    private class PhoneNumberFunctions implements Function<PhoneNumber, ModifiablePhoneNumber>, 
        Supplier<ModifiablePhoneNumber>
    {

        @Override
        public ModifiablePhoneNumber get()
        {
            return new ModifiablePhoneNumber(new PhoneNumberImpl(), ModifiableContact.this);
        }

        @Override
        public ModifiablePhoneNumber apply(PhoneNumber input)
        {
            return new ModifiablePhoneNumber(input, ModifiableContact.this);
        }
        
    }
    
    /**
     * Switches the subtype modifiable state to correspond to the selected {@link ContactType}.
     * In doing so, it ensures that only the currently-selected subtype is attached to the parent
     * modifiable.
     * 
     * @author SnyderGP
     */
    private class TypeObserver implements StateObserver<ContactType>
    {

        @Override
        public void stateChanged(State<? extends ContactType> state, ContactType oldValue,
            ContactType newValue)
        {
            ValidatedApprovedCompositeModifiable<? extends Contact> previous = subtype.get();
            if(previous != null)
            {
                ModifiableContact.this.removeChild(previous);
                ModifiableContact.this.validator.removeChildValidator(previous.getValidator());
            }
            ValidatedApprovedCompositeModifiable<? extends Contact> current = 
                subtypeMap.get(newValue);
            ModifiableContact.this.addChild(current);
            ModifiableContact.this.validator.addChildValidator(current.getValidator());
            subtype.set(current);
        }
        
    }
    
}
