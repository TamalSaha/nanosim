package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.nanosim.client.Nanosim;

public abstract class ContentListBase extends Composite implements ClickHandler {
	protected Nanosim nanosim;
	protected FlexTable table;
	protected int startIndex, selectedRow = -1;
	protected FlowPanel toolbar;
	protected FlexCellFormatter cellFormatter;

	public ContentListBase() {
		this.nanosim = Nanosim.getInstance();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");

		FlowPanel wrapper1 = new FlowPanel();
		wrapper1.setStylePrimaryName("gwt-StackPanel");

		toolbar = new FlowPanel();
		toolbar.setHeight("30px");
		toolbar.setStylePrimaryName("gwt-StackPanelItem");
		wrapper1.add(toolbar);
		vPanel.add(wrapper1);

		FlowPanel wrapper2 = new FlowPanel();
		wrapper2.setStylePrimaryName("nanosim-Listbase");

		// Setup the table && Hook up events.
		table = new FlexTable();
		cellFormatter = table.getFlexCellFormatter();
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("100%");
		table.addClickHandler(this);
		wrapper2.add(table);
		vPanel.add(wrapper2);

		initWidget(vPanel);
		initTable();
	}

	@Override
	public void onLoad() {
		update();
	}

	/**
	 * Initializes the table so that it contains enough rows for a full page of
	 * items. Also creates the images that will be used as 'read' flags.
	 */
	protected abstract void initTable();

	protected abstract void update();

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == table) {
			// Select the row that was clicked (-1 to account for header row).
			Cell cell = table.getCellForEvent(event);
			if (cell != null) {
				int row = cell.getRowIndex();
				if (row > 0) {
					innerSelectRow(row - 1);
				}
			}
		}
	}

	/**
	 * Selects the given row (relative to the current page).
	 * 
	 * @param row
	 *            the row to be selected
	 */
	private void innerSelectRow(int row) {
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
		//selectRow();
	}

	protected void selectRow() {
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
}
