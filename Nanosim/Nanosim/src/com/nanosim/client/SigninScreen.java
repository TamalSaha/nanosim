package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.event.ISigninHandler;
import com.nanosim.client.internal.EventHandlerCollection;
import com.nanosim.client.rpc.SigninService;
import com.nanosim.client.rpc.SigninServiceAsync;
import com.nanosim.model.Person;

public class SigninScreen extends Composite {

	private EventHandlerCollection<ISigninHandler> signinHandlerColl = new EventHandlerCollection<ISigninHandler>();

	private final SigninServiceAsync signinService = SigninService.Util
			.getInstance();

	public SigninScreen() {
		final Image logo = Nanosim.images.logo().createImage();
		logo.setHeight("150px");

		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		HTML h1 = new HTML();
		h1.setHeight("100px");
		dock.add(h1, DockPanel.NORTH);
		dock.add(logo, DockPanel.NORTH);

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
		btnSignin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String userName = txtUsername.getText();
				String password = txtPassowrd.getText();

				if (userName == null || userName.equals("")) {
					layout.setHTML(0, 0, "<p>Username not found.</p><p></p>");
				} else if (password == null || password.equals("")) {
					layout.setHTML(0, 0, "<p>Password not found.</p><p></p>");
				} else {
					signinService.signin(userName, password,
							new AsyncCallback<Person>() {

								@Override
								public void onFailure(Throwable caught) {
									layout
											.setHTML(0, 0,
													"<p>Authentication failed !!!</p><p></p>");
								}

								@Override
								public void onSuccess(Person result) {
									if (result == null) {
										layout
												.setHTML(0, 0,
														"<p>Authentication failed !!!</p><p></p>");
									} else {
										layout.setHTML(0, 0, "");
										for (ISigninHandler handler : signinHandlerColl
												.getList()) {
											handler.OnSuccess(result);
										}
									}
								}
							});
				}
			}
		});

		// Wrap the content in a DecoratorPanel
		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidget(layout);

		dock.add(decPanel, DockPanel.CENTER);
		dock.setWidth("100%");
		initWidget(dock);
	}

	public void addLoginHandler(ISigninHandler handler) {
		if (handler != null)
			signinHandlerColl.addListener(handler);
	}

	public void removeLoginHandler(ISigninHandler handler) {
		if (handler != null)
			signinHandlerColl.removeListener(handler);
	}
}
