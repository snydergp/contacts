/**
 * 
 */
package com.snyder.contacts.client.model.modifiable;

import com.snyder.contacts.shared.model.EmailAddress;
import com.snyder.contacts.shared.model.EmailAddressImpl;
import com.snyder.contacts.shared.model.validation.EmailAddressValidation;
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
		type = this.buildLeaf(initial.getType(), EmailAddressValidation.TYPE);
		email = this.buildLeaf(initial.getEmail(), EmailAddressValidation.EMAIL);
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
