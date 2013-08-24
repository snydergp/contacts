package com.snyder.contacts.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.model.Address;
import com.snyder.review.shared.validator.algorithm.CompositeValidationAlgorithm;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.NullValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class AddressValidation implements ValidationAlgorithm<Address>
{

    private static final int MAX_ADDRESS_LINE_LENGTH = 128;
    private static final int MAX_CITY_LENGTH = 128;
    private static final int MAX_POSTAL_CODE_LENGTH = 10;
    private static final int MAX_COUNTRY_LENGTH = 64;
    private static final int MAX_ADDITIONAL_INFO_LENGTH = 64;

    public static final ValidationAlgorithm<String> LINE_1 = 
        new StringValidationBase("Line 1", MAX_ADDRESS_LINE_LENGTH, true);
    public static final ValidationAlgorithm<String> LINE_2 = 
        new StringValidationBase("Line 2", MAX_ADDRESS_LINE_LENGTH, false);
    public static final ValidationAlgorithm<String> LINE_3 = 
        new StringValidationBase("Line 3", MAX_ADDRESS_LINE_LENGTH, false);
    public static final ValidationAlgorithm<String> CITY = 
        new StringValidationBase("City", MAX_CITY_LENGTH, true);
    public static final ValidationAlgorithm<String> POSTAL_CODE = 
        new StringValidationBase("Postal code", MAX_POSTAL_CODE_LENGTH, true);
    public static final ValidationAlgorithm<String> COUNTRY = 
        new StringValidationBase("Country", MAX_COUNTRY_LENGTH, true);
    public static final ValidationAlgorithm<String> ADDITIONAL_INFO = 
        new StringValidationBase("Additional Info", MAX_ADDITIONAL_INFO_LENGTH, true);

    @Override
    public void validate(Address address, Builder<String> errors)
    {
        LINE_1.validate(address.getLine1(), errors);
        LINE_2.validate(address.getLine2(), errors);
        LINE_3.validate(address.getLine3(), errors);
        CITY.validate(address.getCity(), errors);
        //TODO state
        POSTAL_CODE.validate(address.getPostalCode(), errors);
        COUNTRY.validate(address.getCountry(), errors);
        ADDITIONAL_INFO.validate(address.getAdditionalInformation(), errors);
    }
    
    private static class StringValidationBase extends CompositeValidationAlgorithm<String>
    {
        
        public StringValidationBase(String name, int max, boolean required)
        {
            if(required)
            {
                this.addChild(new NullValidation<String>(name));
            }
            this.addChild(LengthValidation.max(name, max));
        }
        
    }
    
}
