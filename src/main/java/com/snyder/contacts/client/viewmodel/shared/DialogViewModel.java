package com.snyder.contacts.client.viewmodel.shared;

public interface DialogViewModel
{

	String getTitle();

	String getText();

	String getNegativeButtonText();

	String getAffirmativeButtonText();

	void negative();

	void affirmative();

}
