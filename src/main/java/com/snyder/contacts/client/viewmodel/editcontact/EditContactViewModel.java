package com.snyder.contacts.client.viewmodel.editcontact;

import com.snyder.contacts.client.model.modifiable.ModifiableContact;
import com.snyder.contacts.client.serverinterface.ErrorHandler;
import com.snyder.contacts.shared.exceptions.InvalidContactException;
import com.snyder.modifiable.undo.UndoControls;


public interface EditContactViewModel
{
    
    ModifiableContact getContact();
    
    UndoControls getUndoControls();
    
    void save(ErrorHandler<InvalidContactException> onFailure);
    
    void cancel();
    
}
