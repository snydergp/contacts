package com.snyder.contacts.shared.model.validation;

import com.google.common.collect.ImmutableCollection.Builder;
import com.snyder.contacts.shared.model.Person;
import com.snyder.review.shared.validator.algorithm.CompositeValidationAlgorithm;
import com.snyder.review.shared.validator.algorithm.LengthValidation;
import com.snyder.review.shared.validator.algorithm.NullValidation;
import com.snyder.review.shared.validator.algorithm.ValidationAlgorithm;


public class PersonValidation implements ValidationAlgorithm<Person>
{

    public static final FirstNameValidation FIRST = new FirstNameValidation();
    public static final MiddleInitialValidation MIDDLE = new MiddleInitialValidation();
    public static final LastNameValidation LAST = new LastNameValidation();

    @Override
    public void validate(Person person, Builder<String> errors)
    {
        FIRST.validate(person.getFirstName(), errors);
        MIDDLE.validate(person.getMiddleInitial(), errors);
        LAST.validate(person.getLastName(), errors);
    }
    
    private static class FirstNameValidation extends CompositeValidationAlgorithm<String>
    {

        private static final String NAME = "First name";
        private static final int MAX_LENGTH = 128;
        
        public FirstNameValidation()
        {
            this.addChild(new NullValidation<String>(NAME));
            this.addChild(LengthValidation.max(NAME, MAX_LENGTH));
        }
        
    }
    
    private static class MiddleInitialValidation extends CompositeValidationAlgorithm<String>
    {

        private static final String NAME = "Middle initial";
        private static final int MAX_LENGTH = 1;
        
        public MiddleInitialValidation()
        {
            this.addChild(new NullValidation<String>(NAME));
            this.addChild(LengthValidation.max(NAME, MAX_LENGTH));
        }
        
    }
    
    private static class LastNameValidation extends CompositeValidationAlgorithm<String>
    {

        private static final String NAME = "Last name";
        private static final int MAX_LENGTH = 128;
        
        public LastNameValidation()
        {
            this.addChild(new NullValidation<String>(NAME));
            this.addChild(LengthValidation.max(NAME, MAX_LENGTH));
        }
        
    }
    
}
