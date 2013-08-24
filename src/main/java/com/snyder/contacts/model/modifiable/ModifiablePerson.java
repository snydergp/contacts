package com.snyder.contacts.model.modifiable;

import java.util.ArrayList;
import java.util.List;

import com.snyder.contacts.model.Address;
import com.snyder.contacts.model.EmailAddress;
import com.snyder.contacts.model.Person;
import com.snyder.contacts.model.PersonImpl;
import com.snyder.contacts.model.PhoneNumber;
import com.snyder.contacts.model.validation.PersonValidation;
import com.snyder.modifiable.LeafModifiable;
import com.snyder.modifiable.approved.ModificationApprover;
import com.snyder.modifiable.validation.ModifiableList;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;
import com.snyder.state.list.HasListEvents;


public class ModifiablePerson extends ValidatedApprovedCompositeModifiable<Person>
{

    private final LeafModifiable<String> firstName;
    private final LeafModifiable<String> middleInitial;
    private final LeafModifiable<String> lastName;
    private final ModifiableList<ModifiableAddress> addresses;
    private final ModifiableList<ModifiablePhoneNumber> phoneNumbers;
    private final ModifiableList<ModifiableEmailAddress> emailAddresses;

    public ModifiablePerson(Person initial, ModificationApprover approver)
    {
        super(initial, approver);
        firstName = this.buildLeaf(initial.getFirstName(), PersonValidation.FIRST);
        middleInitial = this.buildLeaf(initial.getMiddleInitial());
        lastName = this.buildLeaf(initial.getLastName(), PersonValidation.LAST);
        
        List<ModifiableAddress> modifiableAddresses = 
            new ArrayList<ModifiableAddress>();
        for(Address address: initial.getAddresses())
        {
            modifiableAddresses.add(new ModifiableAddress(address, this));
        }
        addresses = new ModifiableList<ModifiableAddress>(modifiableAddresses, this);
        
        List<ModifiablePhoneNumber> modifiableNumbers = new ArrayList<ModifiablePhoneNumber>();
        for(PhoneNumber phoneNumber: initial.getPhoneNumbers())
        {
            modifiableNumbers.add(new ModifiablePhoneNumber(phoneNumber, this));
        }
        phoneNumbers = new ModifiableList<ModifiablePhoneNumber>(modifiableNumbers, this);
        
        List<ModifiableEmailAddress> modifiableEmails = new ArrayList<ModifiableEmailAddress>();
        for(EmailAddress email: initial.getEmailAddresses())
        {
        	modifiableEmails.add(new ModifiableEmailAddress(email, this));
        }
        emailAddresses = new ModifiableList<ModifiableEmailAddress>(modifiableEmails, this);
    }

    @Override
    public Person getModified()
    {
        PersonImpl mod = new PersonImpl(getCurrent());
        mod.setFirstName(firstName.getModified());
        mod.setMiddleInitial(middleInitial.getModified());
        mod.setLastName(lastName.getModified());
        
        mod.getAddresses().clear();
        for(ModifiableAddress modAddress: addresses.getModified())
        {
            mod.getAddresses().add(modAddress.getModified());
        }
        
        mod.getPhoneNumbers().clear();
        for(ModifiablePhoneNumber modNumber: phoneNumbers.getModified())
        {
            mod.getPhoneNumbers().add(modNumber.getModified());
        }
        
        return mod;
    }

    public LeafModifiable<String> getFirstName()
    {
        return firstName;
    }

    public LeafModifiable<String> getMiddleInitial()
    {
        return middleInitial;
    }

    public LeafModifiable<String> getLastName()
    {
        return lastName;
    }
    
    public HasListEvents<ModifiableAddress> getAddresses()
    {
        return addresses.getModifiedView();
    }
    
    public ModifiableAddress addAddress()
    {
        ModifiableAddress newAddress = new ModifiableAddress(null, this); // TODO
        this.addChild(newAddress);
        validator.addChildValidator(newAddress.getValidator());
        addresses.add(newAddress);
        return newAddress;
    }
    
    public void deleteAddress(int index)
    {
        ModifiableAddress removed = addresses.getModifiedView().getView().get(index);
        this.removeChild(removed);
        validator.removeChildValidator(removed.getValidator());
        addresses.remove(index);
    }
    
    public HasListEvents<ModifiablePhoneNumber> getPhoneNumbers()
    {
        return phoneNumbers.getModifiedView();
    }
    
    public ModifiablePhoneNumber addPhoneNumber()
    {
        ModifiablePhoneNumber newNumber = new ModifiablePhoneNumber(null, this); // TODO
        this.addChild(newNumber);
        validator.addChildValidator(newNumber.getValidator());
        phoneNumbers.add(newNumber);
        return newNumber;
    }
    
    public void deletePhoneNumber(int index)
    {
        ModifiablePhoneNumber removed = phoneNumbers.getModifiedView().getView().get(index);
        this.removeChild(removed);
        validator.removeChildValidator(removed.getValidator());
        phoneNumbers.remove(index);
    }
    
    public HasListEvents<ModifiableEmailAddress> getEmailAddresses()
    {
        return emailAddresses.getModifiedView();
    }
    
    public ModifiableEmailAddress addEmailAddress()
    {
    	ModifiableEmailAddress newEmail = new ModifiableEmailAddress(null, this); // TODO
        this.addChild(newEmail);
        validator.addChildValidator(newEmail.getValidator());
        emailAddresses.add(newEmail);
        return newEmail;
    }
    
    public void deleteEmailAddress(int index)
    {
    	ModifiableEmailAddress removed = emailAddresses.getModifiedView().getView().get(index);
        this.removeChild(removed);
        validator.removeChildValidator(removed.getValidator());
        emailAddresses.remove(index);
    }
    
}
