package com.nanosim.client.profile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;

public class ProfileObjective extends Composite  {

	private final GroupServiceAsync groupService = GroupService.Util
			.getInstance();
	private Nanosim nanosim;

	public ProfileObjective() {
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
		
		HTML title = new HTML("Change Group's Mission Objective", true);
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolbar.add(title);	

		FlowPanel wrapper2 = new FlowPanel();
		wrapper2.setStylePrimaryName("gwt-StackPanelItem");

		// Setup the table
		FlexTable table = new FlexTable();
		FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
		table.setCellSpacing(0);
		table.setCellPadding(2);
		table.setWidth("100%");
		wrapper2.add(table);
		vPanel.add(wrapper2);
		
		// Create a table to layout the form options
		table.setWidth("100%");
		table.setCellSpacing(2);
		
//		// Add a title to the form
//		table.setText(0, 0, "Mission Objective");
//		//table.getFlexCellFormatter().setColSpan(0, 0, 4);
//		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
//		cellFormatter.setHorizontalAlignment(0, 0,
//				HasHorizontalAlignment.ALIGN_CENTER);

		final TextArea txtObjective = new TextArea();
		txtObjective.setText(nanosim.Group.getObjective());
		txtObjective.setWidth("100%");
		txtObjective.setHeight("100px");
		table.setWidget(0, 0, txtObjective);

		final Button btnSave = new Button("Submit New Objective");
		table.setWidget(1, 0, btnSave);
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		btnSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				nanosim.Group.setObjective(txtObjective.getText());

				groupService.updateObjective(txtObjective.getText(),
						nanosim.Person.getGroupId(), new AsyncCallback<Integer>() {

							@Override
							public void onSuccess(Integer result) {
								if (result != -1)
									nanosim.endLoadingSuccess();
								else
									nanosim.endLoadingFailure();
							}

							@Override
							public void onFailure(Throwable caught) {
								nanosim.endLoadingFailure();
							}
						});
				nanosim.updateHeader();
			}
		});

		initWidget(vPanel);
	}
}