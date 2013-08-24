/**
 * 
 */
package com.snyder.contacts.model.modifiable;

import com.snyder.contacts.model.EmailAddress;
import com.snyder.contacts.model.EmailAddressImpl;
import com.snyder.modifiable.LeafModifiable;
import com.snyder.modifiable.approved.ModificationApprover;
import com.snyder.modifiable.validation.ValidatedApprovedCompositeModifiable;

/**
 * 
 * @author greg
 */
public class ModifiableEmailAddress extends ValidatedApprovedCompositeModifiable<EmailAddress>
{
	
	private final LeafModifiable<String> type;
	private final LeafModifiable<String> email;

	/**
	 * @param initial
	 * @param approver
	 */
    public ModifiableEmailAddress(EmailAddress initial, ModificationApprover approver)
    {
	    super(initial, approver);
	    type = this.buildLeaf(initial.getType());
	    email = this.buildLeaf(initial.getEmail());
    }

	@Override
    public EmailAddress getModified()
    {
	    EmailAddressImpl mod = new EmailAddressImpl(getCurrent());
	    mod.setType(type.getModified());
	    mod.setEmail(email.getModified());
	    return mod;
    }

}
