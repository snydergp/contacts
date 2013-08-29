package com.snyder.contacts.client.viewmodel;

import com.snyder.state.State;


public interface ContactSelection
{
    
    void displayContact(int contactId);
    
    State<Integer> getSelectedContactId();
    
}
