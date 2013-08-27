package com.snyder.contacts.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.model.EmailAddress;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class EmailAddressValidation implements ValidationAlgorithm<EmailAddress>
{

    public static final ValidationAlgorithm<String> TYPE = LengthValidation.max("Type", 32);
    public static final ValidationAlgorithm<String> EMAIL = LengthValidation.max("Email", 1024);

    @Override
    public void validate(EmailAddress emailAddress, Builder<String> errors)
    {
        TYPE.validate(emailAddress.getType(), errors);
        EMAIL.validate(emailAddress.getEmail(), errors);
    }
    
}
