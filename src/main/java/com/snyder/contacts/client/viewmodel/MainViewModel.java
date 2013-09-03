package com.snyder.contacts.client.viewmodel;

import com.snyder.contacts.client.viewmodel.displaycontact.DisplayContactViewModel;
import com.snyder.contacts.client.viewmodel.editcontact.EditContactViewModel;
import com.snyder.contacts.client.viewmodel.listing.ContactListingViewModel;
import com.snyder.contacts.client.viewmodel.shared.DialogViewModel;
import com.snyder.state.State;
import com.snyder.state.nonnull.NonNullState;

public interface MainViewModel
{

	public enum DisplayMode
	{
		NONE, DISPLAY, EDIT;
	}

	ContactListingViewModel getListingViewModel();

	DisplayContactViewModel getDisplayViewModel();

	EditContactViewModel getEditViewModel();

	NonNullState<DisplayMode> getDisplayMode();

	State<DialogViewModel> getDialogState();

}
