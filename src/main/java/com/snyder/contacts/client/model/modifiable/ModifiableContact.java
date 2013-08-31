package com.snyder.contacts.client.model.modifiable;

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
import com.snyder.modifiable.list.ModifiableListControl;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;
import com.snyder.modifiable.validation.ValidatedApprovedLeafModifiable;
import com.snyder.modifiable.validation.ValidatedModifiable;
import com.snyder.review.shared.validator.algorithm.NullValidation;
import com.snyder.state.State;
import com.snyder.state.StateObserver;


/**
 * Allows modification of an instance of Contact. Clients can switch the contact between 
 * {@link Person} and {@link Business} subtypes, and edit the contained fields in the selected 
 * subtype. In addition, the lists of {@link Address}es, {@link EmailAddress}es, and 
 * {@link PhoneNumber}s are made modifiable through {@link ModifiableListControl} instances of type
 * {@link ModifiableAddress}, {@link ModifiableEmailAddress}, and {@link ModifiablePhoneNumber},
 * respectively.
 * 
 * @author greg
 */
public class ModifiableContact extends ValidatedApprovedCompositeModifiable<Contact>
{
	
	private final ModifiablePerson modifiablePerson;
	private final ModifiableBusiness modifiableBusiness;
    private final ValidatedApprovedLeafModifiable<ContactType> typeSelector;
    private final ValidatedApprovedLeafModifiable<String> info;
    private final ValidatedListControls<Address, ModifiableAddress> addresses;
    private final ValidatedListControls<PhoneNumber, ModifiablePhoneNumber> phoneNumbers;
    private final ValidatedListControls<EmailAddress, ModifiableEmailAddress> emailAddresses;

    /**
     * Creates a new {@link ModifiableContact} for editing the provided initial contact.
     * 
     * @param initial
     * @param approver
     */
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
        modifiablePerson = new ModifiablePerson(person, this);
        
        // Create the Modifiable for the Business subtype
        modifiableBusiness = new ModifiableBusiness(business, this);
        
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
            mod = new PersonImpl(modifiablePerson.getModified());
        }
        else if(typeSelector.getModified() == ContactType.BUSINESS)
        {
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
    
    /**
     * @return A leaf modifiable representing the subtype of the {@link Contact}.  When the type is
     * set to {@value ContactType#PERSON}, the {@link ModifiablePerson} instance accessed through
     * {@link #getModifiablePerson()} represents the subtype.  When type is set to 
     * {@value ContactType#BUSINESS}, the subtype is represented by the {@link ModifiableBusiness}
     * obtained from {@link #getModifiableBusiness()}.
     */
    public ValidatedApprovedLeafModifiable<ContactType> getType()
    {
        return typeSelector;
    }

    /**
     * @return The Modifiable instance containing fields unique to the {@link Contact}'s 
     * {@link Person} subtype.
     */
    public ModifiablePerson getModifiablePerson()
	{
		return modifiablePerson;
	}

    /**
     * @return The Modifiable instance containing fields unique to the {@link Contact}'s 
     * {@link Business} subtype.
     */
	public ModifiableBusiness getModifiableBusiness()
	{
		return modifiableBusiness;
	}

	/**
	 * @return The modifiable leaf representing the {@link Contact#getInfo()} field
	 */
	public ValidatedApprovedLeafModifiable<String> getInfo()
    {
        return info;
    }
    
    /**
     * @return a {@link ModifiableListControl} of {@link ModifiableAddress} instances representing
     * the {@link Contact#getAddresses()} field
     */
    public ModifiableListControl<ModifiableAddress> getAddresses()
    {
        return addresses;
    }

    /**
     * @return a {@link ModifiableListControl} of {@link ModifiablePhoneNumber} instances 
     * representing the {@link Contact#getPhoneNumbers()} field
     */
    public ModifiableListControl<ModifiablePhoneNumber> getPhoneNumbers()
    {
        return phoneNumbers;
    }

    /**
     * @return a {@link ModifiableListControl} of {@link ModifiableEmailAddress} instances 
     * representing the {@link Contact#getEmailAddresses()} field
     */
    public ModifiableListControl<ModifiableEmailAddress> getEmailAddresses()
    {
        return emailAddresses;
    }
    
    /**
     * Utility method for obtaining the subtype modifiable instance ({@link ModifiableBusiness} or 
     * {@link ModifiablePerson}) representing the given {@link ContactType}.
     * 
     * @param type
     * @return
     */
    private ValidatedModifiable<? extends Contact> getSubtype(ContactType type)
    {
    	switch(type)
    	{
    		case PERSON: return modifiablePerson;
    		case BUSINESS: return modifiableBusiness;
    		default:
    			throw new IllegalStateException();
    	}
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
        
        /**
         * @return the leaf modifiable corresponding to {@link Person#getFirstName()}
         */
        public ValidatedApprovedLeafModifiable<String> getFirstName()
        {
            return firstName;
        }
        
        /**
         * @return the leaf modifiable corresponding to {@link Person#getMiddleInitial()}
         */
        public ValidatedApprovedLeafModifiable<String> getMiddleInitial()
        {
            return middleInitial;
        }
        
        /**
         * @return the leaf modifiable corresponding to {@link Person#getLastName()}
         */
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
        
        /**
         * @return the leaf modifiable corresponding to {@link Business#getName()}
         */
        public ValidatedApprovedLeafModifiable<String> getName()
        {
            return name;
        }
        
    }

    
    /**
     * Utility class declaring methods used to instantiate new {@link ModifiableAddress}es
     * @author greg
     */
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
    
    /**
     * Utility class declaring methods used to instantiate new {@link ModifiableEmailAddress}es
     * @author greg
     */
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

    
    /**
     * Utility class declaring methods used to instantiate new {@link ModifiablePhoneNumber}s
     * @author greg
     */
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
            ValidatedModifiable<? extends Contact> previous = getSubtype(oldValue);
            if(previous != null)
            {
                ModifiableContact.this.removeChild(previous);
                ModifiableContact.this.validator.removeChildValidator(previous.getValidator());
            }
            ValidatedModifiable<? extends Contact> current = getSubtype(newValue);
            ModifiableContact.this.addChild(current);
            ModifiableContact.this.validator.addChildValidator(current.getValidator());
        }
        
    }
    
}
