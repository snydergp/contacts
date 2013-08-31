package com.snyder.contacts.client.view.editcontact;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.snyder.modifiable.list.ModifiableListControl;
import com.snyder.state.util.Mapping;

public class ListControlView<M> implements IsWidget
{

	private final ModifiableListControl<M> listControl;
	private final Mapping<M, IsWidget> viewMapping;
	private final FlowLayoutContainer view = new FlowLayoutContainer();

	public ListControlView(ModifiableListControl<M> listControl, Mapping<M, IsWidget> viewMapping)
	{
		this.listControl = listControl;
		this.viewMapping = viewMapping;

		Label add = new Label("Add");
		add.addClickHandler(new AddHandler());
	}

	@Override
	public Widget asWidget()
	{
		return view;
	}

	private class AddHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event)
		{
			listControl.addItem();
		}

	}

}