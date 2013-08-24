package com.snyder.contacts.model.modifiable;

import com.snyder.contacts.model.PersonAddress;
import com.snyder.contacts.model.PersonAddressImpl;
import com.snyder.modifiable.LeafModifiable;
import com.snyder.modifiable.approved.ModificationApprover;


public class ModifiablePersonAddress extends ModifiableAddress
{
    
    private final LeafModifiable<String> addressType;

    public ModifiablePersonAddress(PersonAddress initial, ModificationApprover approver)
    {
        super(initial, approver);
        addressType = this.buildLeaf(initial.getAddressType());
    }

    @Override
    public PersonAddress getModified()
    {
        PersonAddressImpl mod = new PersonAddressImpl(super.getModified());
        mod.setAddressType(addressType.getModified());
        return mod;
    }
    
    public LeafModifiable<String> getAddressType()
    {
        return addressType;
    }
    
}
