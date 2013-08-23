package com.snyder.contacts.model.modifiable;

import com.snyder.contacts.model.PhoneNumber;
import com.snyder.contacts.model.PhoneNumberImpl;
import com.snyder.contacts.model.validation.PhoneNumberValidation;
import com.snyder.modifiable.approved.ModificationApprover;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;
import com.snyder.modifiable.validation.ValidatedApprovedLeafModifiable;


public class ModifiablePhoneNumber extends ValidatedApprovedCompositeModifiable<PhoneNumber>
{

    private final ValidatedApprovedLeafModifiable<String> type;
    private final ValidatedApprovedLeafModifiable<String> number;

    public ModifiablePhoneNumber(PhoneNumber initial, ModificationApprover approver)
    {
        super(initial, approver);
        type = this.buildLeaf(initial.getType(), PhoneNumberValidation.TYPE);
        number = this.buildLeaf(initial.getNumber(), PhoneNumberValidation.NUMBER);
    }

    @Override
    public PhoneNumber getModified()
    {
        PhoneNumberImpl mod = new PhoneNumberImpl(getCurrent());
        mod.setType(type.getModified());
        mod.setNumber(number.getModified());
        return mod;
    }
    
    public ValidatedApprovedLeafModifiable<String> getType()
    {
        return type;
    }
    
    public ValidatedApprovedLeafModifiable<String> getNumber()
    {
        return number;
    }
    
}
