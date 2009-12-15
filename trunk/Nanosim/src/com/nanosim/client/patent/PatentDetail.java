package com.nanosim.client.patent;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.model.Patent;

/**
 * A composite for displaying the details of an email message.
 */
public class PatentDetail extends DialogBox {
	private ScrollPanel scroller;

	public PatentDetail() {
		// Create a table to layout the form options
		final FlexTable layout = new FlexTable();
		layout.setWidth("300px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		layout.setHTML(0, 0, "");
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		layout.setHTML(1, 0, "Username:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		final TextBox txtUsername = new TextBox();
		txtUsername.setText("ibm");
		txtUsername.setWidth("150px");
		layout.setWidget(1, 1, txtUsername);

		layout.setHTML(2, 0, "Password:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		final PasswordTextBox txtPassowrd = new PasswordTextBox();
		txtPassowrd.setText("demo");
		txtPassowrd.setWidth("150px");
		layout.setWidget(2, 1, txtPassowrd);

		layout.setHTML(3, 0, "");
		layout.setHTML(3, 1, "");

		Button btnSignin = new Button("Sign in");
		layout.setWidget(4, 0, btnSignin);
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		scroller = new ScrollPanel(layout);
		setWidget(scroller);
	}

	/**
	 * Adjusts the widget's size such that it fits within the window's client
	 * area.
	 */
	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth - scroller.getAbsoluteLeft() - 9;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight - scroller.getAbsoluteTop() - 9;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		scroller.setPixelSize(scrollWidth, scrollHeight);
	}

	public void setItem(Patent item) {
		// subject.setHTML(item.subject);
		// sender.setHTML("<b>From:</b>&nbsp;" + item.sender);
		// recipient.setHTML("<b>To:</b>&nbsp;user@nanosim.com");
		// body.setHTML(item.body);
	}
}
