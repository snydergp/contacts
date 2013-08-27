package com.snyder.contacts.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.model.Business;
import com.snyder.review.shared.validator.algorithm.CompositeValidationAlgorithm;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.NullValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class BusinessValidation implements ValidationAlgorithm<Business>
{
    
    public static final ValidationAlgorithm<String> NAME = new NameValidation();

    @Override
    public void validate(Business t, Builder<String> errors)
    {
        NAME.validate(t.getName(), errors);
    }
    
    private static class NameValidation extends CompositeValidationAlgorithm<String>
    {

        private static final String NAME = "Name";
        private static final int MAX_LENGTH = 128;
        
        public NameValidation()
        {
            this.addChild(new NullValidation<String>("name"));
            this.addChild(LengthValidation.max(NAME, MAX_LENGTH));
        }
        
    }
    
}
