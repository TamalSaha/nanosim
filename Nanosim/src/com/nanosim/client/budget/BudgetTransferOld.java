package com.nanosim.client.budget;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.event.ISendBudgetHandler;
import com.nanosim.client.internal.EventHandlerCollection;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.client.rpc.TransferService;
import com.nanosim.client.rpc.TransferServiceAsync;
import com.nanosim.model.Group;

public class BudgetTransferOld extends Composite {

	private EventHandlerCollection<ISendBudgetHandler> sendBudgetHandlerColl = new EventHandlerCollection<ISendBudgetHandler>();
	private ListBox lstGroupID;

	private final TransferServiceAsync transferService = TransferService.Util
			.getInstance();

	private final GroupServiceAsync groupService = GroupService.Util
			.getInstance();

	public BudgetTransferOld() {
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

		final Label lblToGroupID = new Label();
		lblToGroupID.setText("To groupID: ");
		flexTable.setWidget(2, 0, lblToGroupID);

		// final TextBox txtToGroupID = new TextBox();
		// flexTable.setWidget(2, 1, txtToGroupID);

		lstGroupID = new ListBox(false);
		lstGroupID.setWidth("400px");
		flexTable.setWidget(2, 1, lstGroupID);

		groupService.getGroups(new AsyncCallback<List<Group>>() {
			@Override
			public void onFailure(Throwable caught) {
				flexTable.setHTML(0, 0, "Failure !!!");
			}

			@Override
			public void onSuccess(List<Group> result) {
				if (result == null) {
					flexTable.setHTML(0, 0, "Failure !!!");
				} else {
					flexTable.setHTML(0, 0, "");
					for (Group group : result) {
						lstGroupID.addItem(group.getName(), ""
								+ group.getGroupId());
					}
				}
			}
		});

		final Label lblPurpose = new Label();
		lblPurpose.setText("Purpose: ");
		flexTable.setWidget(3, 0, lblPurpose);

		final TextArea txtPurpose = new TextArea();
		flexTable.setWidget(3, 1, txtPurpose);

		Button btnSignin = new Button("Transfer");
		flexTable.setWidget(4, 0, btnSignin);
		cellFormatter.setColSpan(4, 0, 2);
		btnSignin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String creditString = txtUsername.getText();
				String groupIDString = "";
				//String groupNameString = lstGroupID.getItemText(lstGroupID
				//		.getSelectedIndex());

				int index = lstGroupID.getSelectedIndex();
				if (index > -1) {
					groupIDString = lstGroupID.getValue(index);
				}
				String purposeString = txtPurpose.getText();

				if (creditString == null || creditString.equals("")) {
					flexTable.setHTML(0, 0, "Credit cannot be 0.");
				} else if (groupIDString == null || groupIDString.equals("")) {
					flexTable.setHTML(0, 0, "Please select a groupID.");
				} else {
					boolean valid = true;
					char[] credit = creditString.toCharArray();
					for (int i = 0; i < credit.length; i++) {
						if (Character.isDigit(credit[i]) || credit[i] == '.')
							continue;
						else {
							flexTable.setHTML(0, 0,
									"Invalid credit. Please enter a number.");
							valid = false;
						}
					}

					char[] id = groupIDString.toCharArray();
					for (int i = 0; i < id.length; i++) {
						if (Character.isDigit(id[i]))
							continue;
						else {
							flexTable.setHTML(0, 0,
									"Invalid group ID. Please inter a number.");
							valid = false;
						}
					}

					if (purposeString.equals("")) {
						flexTable.setHTML(0, 0, "Please enter your purpose.");
						valid = false;
					}
					if (valid) {

						transferService.insertBudget(creditString,
								groupIDString, purposeString,
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
											flexTable
													.setHTML(0, 0,
															"You send your money successfully!");
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