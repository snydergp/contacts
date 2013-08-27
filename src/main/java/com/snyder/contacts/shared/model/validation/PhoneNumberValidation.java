package com.snyder.contacts.shared.model.validation;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.shared.model.PhoneNumber;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class PhoneNumberValidation implements ValidationAlgorithm<PhoneNumber>
{
    
    public static final ValidationAlgorithm<String> TYPE = LengthValidation.max("type", 32);
    public static final ValidationAlgorithm<String> NUMBER = new PhoneNumberValidator();

    @Override
    public void validate(PhoneNumber phoneNumber, Builder<String> errors)
    {
        TYPE.validate(phoneNumber.getType(), errors);
        NUMBER.validate(phoneNumber.getNumber(), errors);
    }
    
    private static class PhoneNumberValidator implements ValidationAlgorithm<String>
    {
        
        private static final CharMatcher NUMBER_MATCHER = CharMatcher.inRange('0', '9');

        @Override
        public void validate(String number, Builder<String> errors)
        {
            if(number == null)
            {
                errors.add("Phone number is required.");
            }
            else
            {
                if(number.length() > 10)
                {
                    errors.add("Phone number cannot exceed 10 characters.");
                }
                if(!NUMBER_MATCHER.matchesAllOf(number))
                {
                    errors.add("Phone number can only contain numerals.");
                }
            }
        }
        
    }
    
}
