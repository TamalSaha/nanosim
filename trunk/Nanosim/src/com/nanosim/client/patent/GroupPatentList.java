package com.nanosim.client.patent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.Nanosim;

public class GroupPatentList extends ContentListBase {

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "Title");
		table.setText(0, 1, "Group");
		table.setText(0, 2, "Submission Time");
		table.setText(0, 3, "Status");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");

		// Initialize the rest of the rows.
		for (int i = 0; i < 100; ++i) {
			table.setText(i + 1, 0, "");
			table.setText(i + 1, 1, "");
			table.setText(i + 1, 2, "");
			table.getCellFormatter().setWordWrap(i + 1, 0, false);
			table.getCellFormatter().setWordWrap(i + 1, 1, false);
			table.getCellFormatter().setWordWrap(i + 1, 2, false);
			table.getFlexCellFormatter().setColSpan(i + 1, 2, 2);
		}
	}

	@Override
	protected void update() {
		// // Update the older/newer buttons & label.
		// int count = MailItems.getMailItemCount();
		// int max = startIndex + VISIBLE_EMAIL_COUNT;
		// if (max > count) {
		// max = count;
		// }
		//
		// newerButton.setVisible(startIndex != 0);
		// olderButton.setVisible(startIndex + VISIBLE_EMAIL_COUNT < count);
		// countLabel
		// .setText("" + (startIndex + 1) + " - " + max + " of " + count);
		//
		// // Show the selected emails.
		// int i = 0;
		// for (; i < VISIBLE_EMAIL_COUNT; ++i) {
		// // Don't read past the end.
		// if (startIndex + i >= MailItems.getMailItemCount()) {
		// break;
		// }
		//
		// MailItem item = MailItems.getMailItem(startIndex + i);
		//
		// // Add a new row to the table, then set each of its columns to the
		// // email's sender and subject values.
		// table.setText(i + 1, 0, item.sender);
		// table.setText(i + 1, 1, item.email);
		// table.setText(i + 1, 2, item.subject);
		// }
		//
		// // Clear any remaining slots.
		// for (; i < VISIBLE_EMAIL_COUNT; ++i) {
		// table.setHTML(i + 1, 0, "&nbsp;");
		// table.setHTML(i + 1, 1, "&nbsp;");
		// table.setHTML(i + 1, 2, "&nbsp;");
		// }
		//
		// // Select the first row if none is selected.
		// if (selectedRow == -1) {
		// selectRow(0);
		// }
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
}
