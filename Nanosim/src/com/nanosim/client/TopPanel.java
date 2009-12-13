package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.model.Group;
import com.nanosim.model.Person;
import com.nanosim.client.event.ISignoutHandler;
import com.nanosim.client.internal.EventHandlerCollection;

public class TopPanel extends Composite {

	private EventHandlerCollection<ISignoutHandler> signoutHandlerColl = new EventHandlerCollection<ISignoutHandler>();

	public TopPanel(Person person, Group group) {
		// Create a table to layout the form options
		final FlexTable layout = new FlexTable();
		layout.setWidth("100%");
		//layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setWidget(0, 0, Nanosim.images.logo().createImage());
		cellFormatter.setRowSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		layout.setHTML(0, 1, "<b>Welcome back, <i>" + person.getName()
				+ "</b></i>");
		cellFormatter.setColSpan(0, 1, 3);
		cellFormatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_BOTTOM);

		layout.setHTML(1, 0, "");
		cellFormatter.setWidth(1, 0, "50%");
		final Hyperlink signOutLink = new Hyperlink("Sign Out", "signout");
		signOutLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ISignoutHandler handler : signoutHandlerColl.getList())
					handler.OnSignout();
			}
		});
		layout.setWidget(1, 1, signOutLink);
		cellFormatter.setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter
				.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setWidth(1, 1, "100px");

		final Hyperlink aboutLink = new Hyperlink("About", "about");
		aboutLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AboutDialog dlg = new AboutDialog();
				dlg.center();
				dlg.show();
			}
		});
		layout.setWidget(1, 2, aboutLink);
		cellFormatter.setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter
				.setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setWidth(1, 2, "50px");

		initWidget(layout);
	}

	public void addLoginHandler(ISignoutHandler handler) {
		if (handler != null)
			signoutHandlerColl.addListener(handler);
	}

	public void removeLoginHandler(ISignoutHandler handler) {
		if (handler != null)
			signoutHandlerColl.removeListener(handler);
	}

}
