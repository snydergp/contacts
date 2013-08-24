package com.snyder.contacts.model.modifiable;

import com.snyder.contacts.model.Address;
import com.snyder.contacts.model.AddressImpl;
import com.snyder.contacts.model.validation.AddressValidation;
import com.snyder.modifiable.approved.ModificationApprover;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;
import com.snyder.modifiable.validation.ValidatedApprovedLeafModifiable;


public class ModifiableAddress extends ValidatedApprovedCompositeModifiable<Address>
{

    private final ValidatedApprovedLeafModifiable<String> line1;
    private final ValidatedApprovedLeafModifiable<String> line2;
    private final ValidatedApprovedLeafModifiable<String> line3;
    private final ValidatedApprovedLeafModifiable<String> city;
    private final ValidatedApprovedLeafModifiable<String> stateProvince;
    private final ValidatedApprovedLeafModifiable<String> postalCode;
    private final ValidatedApprovedLeafModifiable<String> country;
    private final ValidatedApprovedLeafModifiable<String> additionalInformation;

    public ModifiableAddress(Address initial, ModificationApprover approver)
    {
        super(initial, approver);
        line1 = this.buildLeaf(initial.getLine1(), AddressValidation.LINE_1);
        line2 = this.buildLeaf(initial.getLine2(), AddressValidation.LINE_2);
        line3 = this.buildLeaf(initial.getLine3(), AddressValidation.LINE_3);
        city = this.buildLeaf(initial.getCity(), AddressValidation.CITY);
        stateProvince = this.buildLeaf(initial.getStateProvince(), null); //TODO validation
        postalCode = this.buildLeaf(initial.getPostalCode(), AddressValidation.POSTAL_CODE);
        country = this.buildLeaf(initial.getCountry(), AddressValidation.COUNTRY);
        additionalInformation = this.buildLeaf(initial.getAdditionalInformation(), 
            AddressValidation.ADDITIONAL_INFO);
    }

    @Override
    public Address getModified()
    {
        AddressImpl mod = new AddressImpl(getCurrent());
        mod.setLine1(line1.getModified());
        mod.setLine2(line2.getModified());
        mod.setLine3(line3.getModified());
        mod.setCity(city.getModified());
        mod.setStateProvince(stateProvince.getModified());
        mod.setPostalCode(postalCode.getModified());
        mod.setCountry(country.getModified());
        mod.setAdditionalInformation(additionalInformation.getModified());
        return mod;
    }

    public ValidatedApprovedLeafModifiable<String> getLine1()
    {
        return line1;
    }

    public ValidatedApprovedLeafModifiable<String> getLine2()
    {
        return line2;
    }

    public ValidatedApprovedLeafModifiable<String> getLine3()
    {
        return line3;
    }

    public ValidatedApprovedLeafModifiable<String> getCity()
    {
        return city;
    }

    public ValidatedApprovedLeafModifiable<String> getStateProvince()
    {
        return stateProvince;
    }

    public ValidatedApprovedLeafModifiable<String> getPostalCode()
    {
        return postalCode;
    }

    public ValidatedApprovedLeafModifiable<String> getCountry()
    {
        return country;
    }

    public ValidatedApprovedLeafModifiable<String> getAdditionalInformation()
    {
        return additionalInformation;
    }
    
}
