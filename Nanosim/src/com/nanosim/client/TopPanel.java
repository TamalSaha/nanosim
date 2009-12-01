package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.nanosim.model.Group;
import com.nanosim.model.Person;
import com.nanosim.client.event.ISignoutHandler;
import com.nanosim.client.internal.EventHandlerCollection;

public class TopPanel extends Composite {

	private EventHandlerCollection<ISignoutHandler> signoutHandlerColl = new EventHandlerCollection<ISignoutHandler>();

	public TopPanel(Person person, Group group) {

		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
		
		//dock.setWidth("100%");
		DOM.setElementAttribute(dock.getElement(), "id", "my-dock-id");

		dock.add(Nanosim.images.logo().createImage(), DockPanel.WEST);

		Hyperlink aboutLink = new Hyperlink("About", "about");
		aboutLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AboutDialog dlg = new AboutDialog();
				dlg.center();
				dlg.show();
			}
		});
		dock.add(aboutLink, DockPanel.EAST);

		Hyperlink signOutLink = new Hyperlink("Sign Out", "signout");
		signOutLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ISignoutHandler handler : signoutHandlerColl.getList())
					handler.OnSignout();
			}
		});
		dock.add(signOutLink, DockPanel.EAST);

		dock.add(new HTML("<b><i>" + person.getName() + "</b></i>"),
				DockPanel.EAST);

		initWidget(dock);
		dock.setStyleName("nanosim-TopPanel");
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
