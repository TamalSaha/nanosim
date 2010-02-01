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
import com.nanosim.client.rpc.SigninService;
import com.nanosim.client.rpc.SigninServiceAsync;

public class ProfileNotes extends Composite  {

	private final SigninServiceAsync signinService = SigninService.Util
		.getInstance();
	private Nanosim nanosim;

	public ProfileNotes() {
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
		
		HTML title = new HTML("Personal Notes", true);
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
		
		final TextArea txtNotes = new TextArea();
		txtNotes.setText(nanosim.Person.getNotes());
		txtNotes.setWidth("100%");
		txtNotes.setHeight("100%");
		table.setWidget(0, 0, txtNotes);

		final Button btnSave = new Button("Save Notes");
		table.setWidget(1, 0, btnSave);
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		btnSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				nanosim.Person.setNotes(txtNotes.getText());

				signinService.sendNotes(nanosim.Person.getPersonId(), txtNotes.getText(),
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
		initWidget(vPanel);
	}
}