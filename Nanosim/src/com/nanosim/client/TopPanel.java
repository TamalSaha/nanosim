package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.model.Group;
import com.nanosim.model.Person;
import com.nanosim.client.event.ISignoutHandler;
import com.nanosim.client.internal.EventHandlerCollection;

public class TopPanel extends Composite {

	private EventHandlerCollection<ISignoutHandler> signoutHandlerColl = new EventHandlerCollection<ISignoutHandler>();
	private final FlexTable layout;

	public TopPanel() {
		layout = new FlexTable();
		layout.setWidth("100%");
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setWidget(0, 0, Nanosim.images.logo().createImage());
		cellFormatter.setRowSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);

		layout.setHTML(0, 1, "");
		cellFormatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);

		layout.setHTML(0, 2, "<b>Welcome back</b>");
		cellFormatter.setColSpan(0, 2, 2);
		cellFormatter.setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setVerticalAlignment(0, 2,
				HasVerticalAlignment.ALIGN_BOTTOM);

		layout.setHTML(1, 0, "");
		cellFormatter.setWidth(1, 0, "50%");
		final Anchor signOutLink = new Anchor("Sign Out", "signout");
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
		cellFormatter.setWidth(1, 1, "200px");

		final Anchor aboutLink = new Anchor("About", "about");
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

	public void setHeader() {
		Nanosim nanosim = Nanosim.getInstance();
		layout.setHTML(0, 2, "<b>Welcome back, <i>" + nanosim.Person.getName()
				+ " (" + nanosim.Group.getName() + ")" + "</b></i>");
	}

	public void addLoginHandler(ISignoutHandler handler) {
		if (handler != null)
			signoutHandlerColl.addListener(handler);
	}

	public void removeLoginHandler(ISignoutHandler handler) {
		if (handler != null)
			signoutHandlerColl.removeListener(handler);
	}

	public void showMessage(String msg) {
		layout.setHTML(0, 1, msg);
	}
}
