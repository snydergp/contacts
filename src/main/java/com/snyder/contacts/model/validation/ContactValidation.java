package com.snyder.contacts.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.model.Address;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.Person;
import com.snyder.contacts.model.PhoneNumber;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class ContactValidation implements ValidationAlgorithm<Contact>
{
    
    public static final PersonValidation PERSON = new PersonValidation();
    public static final PhoneNumberValidation PHONE = new PhoneNumberValidation();
    public static final AddressValidation ADDRESS = new AddressValidation();

    @Override
    public void validate(Contact contact, Builder<String> errors)
    {
        if(contact instanceof Person)
        {
            PERSON.validate((Person) contact, errors);
        }
        //TODO business
        
        //TODO email
        for(PhoneNumber phoneNumber: contact.getPhoneNumbers())
        {
            PHONE.validate(phoneNumber, errors);
        }
        for(Address address: contact.getAddresses())
        {
            ADDRESS.validate(address, errors);
        }
    }
    
}
