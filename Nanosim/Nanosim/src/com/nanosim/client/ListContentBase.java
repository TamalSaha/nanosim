package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.nanosim.client.Nanosim;

public abstract class ListContentBase extends Composite implements ClickHandler {
	protected Nanosim nanosim;
	protected FlexTable table;
	protected int startIndex, selectedRow = -1;
	protected HorizontalPanel toolbar;
	protected FlexTable header;
	protected HorizontalPanel text;
	protected FlexCellFormatter cellFormatter;
	protected FlowPanel wrapper1 = new FlowPanel();
	protected ScrollPanel wrapper2 = new ScrollPanel();
	protected FlowPanel wrapper3 = new FlowPanel();

	public ListContentBase() {
		this.nanosim = Nanosim.getInstance();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");

		wrapper1.setStylePrimaryName("gwt-StackPanel");

		toolbar = new HorizontalPanel();
		toolbar.setHeight("30px");
		toolbar.setWidth("100%");
		toolbar.setStylePrimaryName("gwt-StackPanelItem");
		wrapper1.add(toolbar);
		vPanel.add(wrapper1);

		wrapper2.setStylePrimaryName("gwt-StackPanelItem");

		// Setup the table
		table = new FlexTable();
		cellFormatter = table.getFlexCellFormatter();
		table.setCellSpacing(0);
		table.setCellPadding(2);
		table.setWidth("100%");
		table.addClickHandler(this);
		wrapper2.add(table);
		vPanel.add(wrapper2);

		wrapper3 = new FlowPanel();
		wrapper3.setStylePrimaryName("gwt-StackPanelItem");

		// Setup the table
		header = new FlexTable();
		text = new HorizontalPanel();
		cellFormatter = header.getFlexCellFormatter();
		header.setCellSpacing(0);
		header.setCellPadding(2);
		header.setWidth("100%");
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setWidth(1, 0, "80px");
		header.addClickHandler(this);
		text.setWidth("100%");
		wrapper3.add(header);
		wrapper3.add(text);
		vPanel.add(wrapper3);
		
		initWidget(vPanel);
		initTable();
	}

	@Override
	public void onAttach() {
		super.onAttach();
		update();
	}

	protected abstract void initTable();

	public abstract void update();

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == table) {
			// Select the row that was clicked (-1 to account for header row).
			Cell cell = table.getCellForEvent(event);
			if (cell != null) {
				int row = cell.getRowIndex();
				if (row > 0) {
					selectRow(row - 1);
				}
			}
		}
	}

	private void selectRow(int row) {
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			if (selected) {
				table.getRowFormatter().addStyleName(row + 1,
						"mail-SelectedRow");
			} else {
				table.getRowFormatter().removeStyleName(row + 1,
						"mail-SelectedRow");
			}
		}
	}

	protected void setCell(int row, int column, String text,
			HorizontalAlignmentConstant alignCenter) {
		table.setText(row, column, text);
		cellFormatter.setHorizontalAlignment(row, column, alignCenter);
	}

	protected void clearTable() {
		for (int i = table.getRowCount(); i-- > 1;) {
			table.removeRow(1);
		}
		selectedRow = -1;
	}
}
