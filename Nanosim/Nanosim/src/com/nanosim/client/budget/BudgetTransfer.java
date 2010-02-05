package com.nanosim.client.budget;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.BudgetService;
import com.nanosim.client.rpc.BudgetServiceAsync;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.model.Group;

public class BudgetTransfer extends Composite {

	private final GroupServiceAsync groupService = GroupService.Util.getInstance();
	private final BudgetServiceAsync budgetService = BudgetService.Util.getInstance();
	private Nanosim nanosim;
	private ListBox lstGroupID;

	public BudgetTransfer() {
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

		HTML title = new HTML("Transfer Money to another Group", true);
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolbar.add(title);	

		FlowPanel wrapper2 = new FlowPanel();
		wrapper2.setStylePrimaryName("gwt-StackPanelItem");

		//		// Setup the table
		//		FlexTable table = new FlexTable();
		//		FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
		//		table.setCellSpacing(0);
		//		table.setCellPadding(2);
		//		table.setWidth("100%");
		//		wrapper2.add(table);
		//		vPanel.add(wrapper2);
		//
		//		// Create a table to layout the form options
		//		table.setWidth("100%");
		//		table.setCellSpacing(2);

		/////////////////
		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

		final FlexTable flexTable = new FlexTable();
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.setWidth("32em");
		flexTable.setCellSpacing(5);
		flexTable.setCellPadding(3);

		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setColSpan(0, 0, 2);

		final Label lblGroup = new Label();
		lblGroup.setText("Available: ");
		flexTable.setWidget(1, 0, lblGroup);

		final Label lblBudget = new Label();
		NumberFormat fmt = NumberFormat.getCurrencyFormat();
		String formatted = fmt.format(nanosim.Group.getBudget());
		lblBudget.setText(formatted);
		flexTable.setWidget(1, 1, lblBudget);

		final Label lblUsername = new Label();
		lblUsername.setText("Amount to Transfer: ");
		flexTable.setWidget(2, 0, lblUsername);

		final TextBox txtUsername = new TextBox();
		flexTable.setWidget(2, 1, txtUsername);

		final Label lblToGroupID = new Label();
		lblToGroupID.setText("To Group: ");
		flexTable.setWidget(3, 0, lblToGroupID);

		lstGroupID = new ListBox(false);
		//lstGroupID.setWidth("400px");
		flexTable.setWidget(3, 1, lstGroupID);

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
		flexTable.setWidget(4, 0, lblPurpose);

		final TextArea txtPurpose = new TextArea();
		flexTable.setWidget(4, 1, txtPurpose);

		Button btnTransfer = new Button("Transfer");
		flexTable.setWidget(5, 0, btnTransfer);
		cellFormatter.setColSpan(5, 0, 2);

		btnTransfer.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String creditString = txtUsername.getText();
				String groupIDString = "";

				int index = lstGroupID.getSelectedIndex();
				if (index > -1) {
					groupIDString = lstGroupID.getValue(index);
				}
				String purposeString = txtPurpose.getText();

				if (creditString == null || creditString.equals("")) {
					flexTable.setHTML(0, 0, "Amount cannot be empty.");
				} else {
					boolean valid = true;
					char[] credit = creditString.toCharArray();
					for (int i = 0; i < credit.length; i++) {
						if (Character.isDigit(credit[i]) || credit[i] == '.')
							continue;
						else {
							flexTable.setHTML(0, 0,
							"Invalid entry. Please enter a number, use point (.) for decimals and no colons");
							valid = false;
							break;
						}
					}
					if (purposeString.equals("")) {
						flexTable.setHTML(0, 0, "Please enter purpose.");
						valid = false;
					}
					if (valid) {
						int amount = Integer.parseInt(creditString);
						int groupId = Integer.parseInt(groupIDString);
						budgetService.insertBudget(amount, groupId, purposeString,
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
											"Transaction successfull !!!");
								}
							}
						});
					}
				}
			}
		});

		dock.add(flexTable, DockPanel.CENTER);
		///////////////

		vPanel.add(dock);
		initWidget(vPanel);
	}
}
