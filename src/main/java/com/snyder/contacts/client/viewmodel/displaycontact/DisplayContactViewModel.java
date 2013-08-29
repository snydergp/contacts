package com.snyder.contacts.client.viewmodel.displaycontact;

import com.snyder.contacts.shared.model.Contact;
import com.snyder.state.State;


public interface DisplayContactViewModel
{
    
    State<Contact> getDisplayedContact();
    
    void edit();
    
}
