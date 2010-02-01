package com.nanosim.client.profile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.SigninService;
import com.nanosim.client.rpc.SigninServiceAsync;

public class ProfilePassword extends Composite  {

	private final SigninServiceAsync signinService = SigninService.Util
	.getInstance();
	private Nanosim nanosim;

	public ProfilePassword() {
		nanosim = Nanosim.getInstance();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");

		FlowPanel wrapper1 = new FlowPanel();
		wrapper1.setStylePrimaryName("gwt-StackPanel");

		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setHeight("30px");
		toolbar.setWidth("100%");
		toolbar.setStylePrimaryName("gwt-StackPanelItem");
		wrapper1.add(toolbar);
		vPanel.add(wrapper1);

		HTML title = new HTML("Change Password", true);
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolbar.add(title);	

		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

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
		txtUsername.setText(nanosim.Person.getName());
		txtUsername.setWidth("150px");
		layout.setWidget(1, 1, txtUsername);

		layout.setHTML(2, 0, "Old Password:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		final PasswordTextBox txtOldPassowrd = new PasswordTextBox();
		txtOldPassowrd.setText("demo");
		txtOldPassowrd.setWidth("150px");
		layout.setWidget(2, 1, txtOldPassowrd);

		layout.setHTML(3, 0, "New Password:");
		cellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		final PasswordTextBox txtNewPassowrd = new PasswordTextBox();
		txtNewPassowrd.setText("demo");
		txtNewPassowrd.setWidth("150px");
		layout.setWidget(3, 1, txtNewPassowrd);

		Button btnSignin = new Button("Change");
		layout.setWidget(4, 0, btnSignin);
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		btnSignin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				layout.setHTML(0, 0,"");
				String userName = txtUsername.getText();
				String oldPassword = txtOldPassowrd.getText();
				String newPassword = txtNewPassowrd.getText();

				if (userName == null || userName.equals("") || !userName.equals(nanosim.Person.getName())) {
					layout.setHTML(0, 0, "<p>Username not found.</p><p></p>");
				} else if (oldPassword == null || oldPassword.equals("") || newPassword == null || newPassword.equals("") ) {
					layout.setHTML(0, 0, "<p>Incorrecct Password.</p><p></p>");
				} else {
					signinService.changePassword(nanosim.Person.getPersonId(), oldPassword, newPassword, new AsyncCallback<Integer>() {
						
						@Override
						public void onFailure(Throwable caught) {
							layout.setHTML(0, 0,
									"<p>Authentication failed !!!</p><p></p>");
						}

						@Override
						public void onSuccess(Integer result) {
							if (result == -1) {
								layout.setHTML(0, 0,
									"<p>Authentication failed !!!</p><p></p>");
							} else {
								layout.setHTML(0, 0,
									"<p>Password Changed !!!</p><p></p>");
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
		vPanel.add(dock);
		initWidget(vPanel);
	}
}