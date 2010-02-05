package com.nanosim.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class RightPanel extends Composite {

	private VerticalPanel rightPanel = new VerticalPanel();

	public RightPanel(Widget widget) {
		rightPanel.setStylePrimaryName("nanosim-RightPanel");
		rightPanel.setWidth("100%");
		add(widget);
		initWidget(rightPanel);
	}

	public RightPanel() {
		rightPanel.setStylePrimaryName("nanosim-RightPanel");
		rightPanel.setWidth("300px");
				
		// Create a table to layout the form options
		final FlexTable layout = new FlexTable();
		layout.setWidth("100%");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		layout.setHTML(0, 0, "Notes");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		final TextArea txtObjective = new TextArea();
		txtObjective.setText("hola");
		txtObjective.setWidth("100%");
		txtObjective.setHeight("100px");
		layout.setWidget(1, 0, txtObjective);

		final Button btnSave = new Button("Save Notes");
		layout.setWidget(2, 0, btnSave);
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
	
		rightPanel.add(layout);
		initWidget(rightPanel);
	
	}

	public void add(Widget widget) {
		rightPanel.add(widget);
	}

	public void clear() {
		rightPanel.clear();
	}
}
