package com.nanosim.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.event.ISendBudgetHandler;
import com.nanosim.client.internal.EventHandlerCollection;
import com.nanosim.client.rpc.TransferService;
import com.nanosim.client.rpc.TransferServiceAsync;

public class SendFundScreen extends Composite {

	private EventHandlerCollection<ISendBudgetHandler> sendBudgetHandlerColl = new EventHandlerCollection<ISendBudgetHandler>();

	private final TransferServiceAsync transferService = TransferService.Util
	.getInstance();

	public SendFundScreen() {
		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

		// Create a Flex Table
		final FlexTable flexTable = new FlexTable();
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName("cw-FlexTable");
		flexTable.setWidth("32em");
		flexTable.setCellSpacing(5);
		flexTable.setCellPadding(3);

		// Add some text
		cellFormatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_LEFT);
		// Label lblMsg = new Label();
		// flexTable.setHTML(0, 0, lblMsg);
		cellFormatter.setColSpan(0, 0, 2);

		final Label lblUsername = new Label();
		lblUsername.setText("Credit: ");
		flexTable.setWidget(1, 0, lblUsername);

		final TextBox txtUsername = new TextBox();
		flexTable.setWidget(1, 1, txtUsername);

		final Label lblPassword = new Label();
		lblPassword.setText("To groupID: ");
		flexTable.setWidget(2, 0, lblPassword);

		final PasswordTextBox txtPassowrd = new PasswordTextBox();
		flexTable.setWidget(2, 1, txtPassowrd);

		Button btnSignin = new Button("Transfer");
		flexTable.setWidget(3, 0, btnSignin);
		cellFormatter.setColSpan(3, 0, 2);
		btnSignin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String creditString = txtUsername.getText();
				String groupIDString = txtPassowrd.getText();

				if (creditString == null || creditString.equals("")) {
					flexTable.setHTML(0, 0, "Credit cannot be 0.");
				} else if (groupIDString == null || groupIDString.equals("")) {
					flexTable.setHTML(0, 0, "Please select a groupID.");
				} else {
					boolean valid = true;
					char[] credit = creditString.toCharArray();
					for (int i = 0; i < credit.length; i++) {
						if(Character.isDigit(credit[i]) || credit[i] == '.')
							continue;
						else{
							flexTable.setHTML(0, 0, "Invalid credit. Please inter a number.");
							valid = false;
						}
					}
					
					char[] id = groupIDString.toCharArray();
					for (int i = 0; i < id.length; i++) {
						if(Character.isDigit(id[i]) )
							continue;
						else{
							flexTable.setHTML(0, 0, "Invalid group ID. Please inter a number.");
							valid = false;
						}
					}
					if(valid){
						
					transferService.insertBudget(creditString, groupIDString,
							new AsyncCallback<Integer>() {

								@Override
								public void onFailure(Throwable caught) {
									flexTable.setHTML(0, 0,
											"Transaction failed !!!");
								}

								@Override
								public void onSuccess(Integer result) {
									if (result == -1) {
										flexTable.setHTML(0, 0,
												"Transaction failed !!!");
									} else {
										flexTable.setHTML(0, 0, "");
										for (ISendBudgetHandler handler : sendBudgetHandlerColl
												.getList()) {
											handler.OnSuccess(result);
										}
									}
								}
							});
					}
				}
			}
		});

		dock.add(flexTable, DockPanel.CENTER);
		initWidget(dock);
	}

	public void addLoginHandler(ISendBudgetHandler handler) {
		sendBudgetHandlerColl.addListener(handler);
	}

	public void removeLoginHandler(ISendBudgetHandler handler) {
		sendBudgetHandlerColl.removeListener(handler);
	}
}