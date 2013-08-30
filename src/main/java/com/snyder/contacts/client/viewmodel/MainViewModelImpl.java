package com.snyder.contacts.client.viewmodel;

import com.snyder.contacts.client.model.modifiable.ModifiableContact;
import com.snyder.contacts.client.serverinterface.Callback;
import com.snyder.contacts.client.serverinterface.ContactServer;
import com.snyder.contacts.client.serverinterface.ErrorHandler;
import com.snyder.contacts.client.viewmodel.displaycontact.DisplayContactViewModel;
import com.snyder.contacts.client.viewmodel.editcontact.EditContactViewModel;
import com.snyder.contacts.client.viewmodel.listing.ContactListingViewModel;
import com.snyder.contacts.client.viewmodel.listing.ContactListingViewModelImpl;
import com.snyder.contacts.client.viewmodel.shared.DialogViewModel;
import com.snyder.contacts.shared.exceptions.InvalidContactException;
import com.snyder.contacts.shared.model.Contact;
import com.snyder.contacts.shared.model.ContactImpl;
import com.snyder.contacts.shared.model.ContactSummary;
import com.snyder.contacts.shared.model.ContactSummaryImpl;
import com.snyder.modifiable.undo.ModificationManager;
import com.snyder.modifiable.undo.UndoControls;
import com.snyder.state.BaseMutableState;
import com.snyder.state.MutableState;
import com.snyder.state.State;
import com.snyder.state.nonnull.BaseMutableNonNullState;
import com.snyder.state.nonnull.MutableNonNullState;
import com.snyder.state.nonnull.NonNullState;
import com.snyder.state.store.BaseStore;
import com.snyder.state.util.Mapping;


public class MainViewModelImpl implements MainViewModel
{
    
    private final ContactServer server;
    private final BaseStore<ContactSummary, Integer> store = 
        new BaseStore<ContactSummary, Integer>(new IdMapping());
    private final ContactListingViewModel listingViewModel;
    private Contact loadedContact;
    private final MutableNonNullState<DisplayMode> displayMode = 
        new BaseMutableNonNullState<DisplayMode>(DisplayMode.NONE);
    private final MutableState<DialogViewModel> dialogState = 
        new BaseMutableState<DialogViewModel>();
    
    private EditContactViewModelImpl currentEdit;

    public MainViewModelImpl(ContactServer server)
    {
        this.server = server;
        this.listingViewModel = new ContactListingViewModelImpl(store, new ContactSelectionImpl());
    }

    @Override
    public ContactListingViewModel getListingViewModel()
    {
        return listingViewModel;
    }

    @Override
    public DisplayContactViewModel getDisplayViewModel()
    {
        return new DisplayContactViewModelImpl();
    }

    @Override
    public EditContactViewModel getEditViewModel()
    {
        return new EditContactViewModelImpl();
    }

    @Override
    public NonNullState<DisplayMode> getDisplayMode()
    {
        return displayMode;
    }
    
    @Override
    public State<DialogViewModel> getDialogState()
    {
        return dialogState;
    }

    private class DisplayContactViewModelImpl implements DisplayContactViewModel
    {

        @Override
        public Contact getDisplayedContact()
        {
            return loadedContact;
        }

        @Override
        public void edit()
        {
            displayMode.set(DisplayMode.EDIT);
        }
        
    }
    
    private class EditContactViewModelImpl implements EditContactViewModel
    {
        
        private final ModifiableContact modifiableContact;
        private final ModificationManager modManager;
        
        public EditContactViewModelImpl()
        {
            modManager = new ModificationManager();
            modifiableContact = new ModifiableContact(loadedContact, modManager);
        }

        @Override
        public ModifiableContact getContact()
        {
            return modifiableContact;
        }

        @Override
        public UndoControls getUndoControls()
        {
            return modManager;
        }

        @Override
        public void save(ErrorHandler<InvalidContactException> onFailure)
        {
            if(modifiableContact.getValidator().isValidState().get())
            {
                Contact toSave = modifiableContact.getModified();
                if(toSave.getId() == Contact.NEW_CONTACT_ID)
                {
                    server.createContact(toSave, new CreateContactCallback(toSave), onFailure);
                }
                else
                {
                    server.updateContact(toSave, new UpdateContactCallback(toSave), onFailure);
                }
            }
        }

        @Override
        public void cancel()
        {
            displayMode.set(DisplayMode.DISPLAY);
        }
        
    }
    
    private class CreateContactCallback implements Callback<Integer>
    {
        
        private final Contact saved;
        
        public CreateContactCallback(Contact saved)
        {
            this.saved = saved;
        }

        @Override
        public void onSuccess(Integer value)
        {
            ((ContactImpl) saved).setId(value); 
            loadedContact = saved;
            displayMode.set(DisplayMode.DISPLAY);
            store.add(ContactSummaryImpl.fromContact(saved));
        }
        
    }
    
    private class UpdateContactCallback implements Callback<Void>
    {
        
        private final Contact saved;
        
        public UpdateContactCallback(Contact saved)
        {
            this.saved = saved;
        }

        @Override
        public void onSuccess(Void value)
        {
            loadedContact = saved;
            displayMode.set(DisplayMode.DISPLAY);
        }
        
    }
    
    private class ContactSelectionImpl implements ContactSelection
    {
        
        private final MutableState<Integer> selectedId = new BaseMutableState<Integer>();

        @Override
        public void displayContact(int contactId)
        {
            if(currentEdit != null && currentEdit.modifiableContact.getIsModifiedState().get())
            {
                //TODO display dialog
            }
            else
            {
                // TODO load contact
            }
        }

        @Override
        public State<Integer> getSelectedContactId()
        {
            return selectedId;
        }
        
    }
    
    private class IdMapping implements Mapping<ContactSummary, Integer>
    {

        @Override
        public Integer map(ContactSummary t)
        {
            return t.getId();
        }
        
    }
    
}
