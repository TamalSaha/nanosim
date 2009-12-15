package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.Nanosim;

public abstract class ContentListBase extends Composite implements ClickHandler {
	protected Nanosim nanosim;
	protected FlexTable table;
	protected int startIndex, selectedRow = -1;

	public ContentListBase() {
		this.nanosim = Nanosim.getInstance();

		// Setup the table && Hook up events.
		table = new FlexTable();
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("100%");
		table.addClickHandler(this);

		// ScrollPanel scPanel = new ScrollPanel(table);
		// scPanel.setHeight("243px");
		// initWidget(scPanel);
		// setStyleName("mail-List");

		initWidget(table);

		initTable();
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
					selectRow(row - 1);
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
	private void selectRow(int row) {
		// // When a row (other than the first one, which is used as a header)
		// is
		// // selected, display its associated MailItem.
		// MailItem item = MailItems.getMailItem(startIndex + row);
		// if (item == null) {
		// return;
		// }
		//
		// styleRow(selectedRow, false);
		// styleRow(row, true);
		//
		// item.read = true;
		// selectedRow = row;
		// Mailboxes.get().displayItem(item);
	}

	private void styleRow(int row, boolean selected) {
		// if (row != -1) {
		// if (selected) {
		// table.getRowFormatter().addStyleName(row + 1,
		// "mail-SelectedRow");
		// } else {
		// table.getRowFormatter().removeStyleName(row + 1,
		// "mail-SelectedRow");
		// }
		// }
	}
}
