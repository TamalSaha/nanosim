package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.nanosim.model.Group;
import com.nanosim.model.Person;

public class TopPanel extends Composite implements ClickHandler {

	/**
	 * An image bundle for this widgets images.
	 */
	public interface Images extends ImageBundle {
		AbstractImagePrototype logo();
	}

	private HTML signOutLink = new HTML("<a href='javascript:;'>Sign Out</a>");
	private HTML aboutLink = new HTML("<a href='javascript:;'>About</a>");

	public TopPanel(Person person, Group group, Images images) {
		HorizontalPanel outer = new HorizontalPanel();
		VerticalPanel inner = new VerticalPanel();

		outer.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		inner.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

		// outer.setHeight("150");
		outer.setWidth("100%");

		HorizontalPanel links = new HorizontalPanel();
		links.setSpacing(4);
		links.add(signOutLink);
		links.add(aboutLink);

		final Image logo = images.logo().createImage();
		outer.add(logo);
		outer.setCellHorizontalAlignment(logo, HorizontalPanel.ALIGN_LEFT);
		outer.setCellHeight(logo, "60");

		outer.add(inner);
		inner.add(new HTML("<b>" + person.getName() + "</b>"));
		inner.add(links);

		signOutLink.addClickHandler(this);
		aboutLink.addClickHandler(this);

		initWidget(outer);
		setStyleName("NanoSim-TopPanel");
		links.setStyleName("NanoSim-TopPanelLinks");
	}

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == signOutLink) {
			Nanosim.getInstance().Logout();
		} else if (sender == aboutLink) {
			// When the 'About' item is selected, show the AboutDialog. Note
			// that showing a dialog box does not block -- execution continues
			// normally, and the dialog fires an event when it is closed.
			AboutDialog dlg = new AboutDialog();
			dlg.show();
			dlg.center();
		}
	}
}
