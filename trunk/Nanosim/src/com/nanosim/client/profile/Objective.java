package com.nanosim.client.profile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;

public class Objective extends Composite {

	private final GroupServiceAsync groupService = GroupService.Util
			.getInstance();
	private Nanosim nanosim;

	public Objective() {
		nanosim = Nanosim.getInstance();

		// Create a table to layout the form options
		final FlexTable layout = new FlexTable();
		layout.setWidth("100%");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		layout.setHTML(0, 0, "Mission Objective");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		final TextArea txtObjective = new TextArea();
		txtObjective.setText(nanosim.Group.getObjective());
		txtObjective.setWidth("100%");
		txtObjective.setHeight("100px");
		layout.setWidget(1, 0, txtObjective);

		final Button btnSave = new Button("Submit New Objective");
		layout.setWidget(2, 0, btnSave);
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		btnSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				nanosim.Group.setObjective(txtObjective.getText());

				groupService.updateObjective(nanosim.Group.getObjective(),
						new AsyncCallback<Integer>() {

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
			}
		});

		initWidget(layout);
	}
}

// DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
// p.addNorth(new HTML("header"), 2);
// p.addSouth(new HTML("footer"), 2);
// p.addWest(new HTML("navigation"), 10);
// p.add(new HTML(content))