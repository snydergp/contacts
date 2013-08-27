package com.snyder.contacts.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.model.Address;
import com.snyder.contacts.model.Business;
import com.snyder.contacts.model.Contact;
import com.snyder.contacts.model.EmailAddress;
import com.snyder.contacts.model.Person;
import com.snyder.contacts.model.PhoneNumber;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class ContactValidation implements ValidationAlgorithm<Contact>
{
    
    public static final ValidationAlgorithm<String> INFO = LengthValidation.max("Info", 65535);
    public static final PersonValidation PERSON = new PersonValidation();
    public static final BusinessValidation BUSINESS = new BusinessValidation();
    public static final EmailAddressValidation EMAIL = new EmailAddressValidation();
    public static final PhoneNumberValidation PHONE = new PhoneNumberValidation();
    public static final AddressValidation ADDRESS = new AddressValidation();

    @Override
    public void validate(Contact contact, Builder<String> errors)
    {
        INFO.validate(contact.getInfo(), errors);
        
        if(contact instanceof Person)
        {
            PERSON.validate((Person) contact, errors);
        }
        if(contact instanceof Business)
        {
            BUSINESS.validate((Business) contact, errors);
        }
        
        for(EmailAddress emailAddress: contact.getEmailAddresses())
        {
            EMAIL.validate(emailAddress, errors);
        }
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
