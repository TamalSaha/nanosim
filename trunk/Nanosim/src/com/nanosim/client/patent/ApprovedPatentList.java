package com.nanosim.client.patent;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.PatentService;
import com.nanosim.client.rpc.PatentServiceAsync;
import com.nanosim.model.Patent;

public class ApprovedPatentList extends ContentListBase {

	// private final PatentServiceAsync patentService = PatentService.Util
	// .getInstance();

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "Title");
		table.setText(0, 1, "Group");
		table.setText(0, 2, "Submission Time");
		table.setText(0, 3, "Status");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");

		// // Initialize the rest of the rows.
		// for (int i = 0; i < 100; ++i) {
		// table.setText(i + 1, 0, "");
		// table.setText(i + 1, 1, "");
		// table.setText(i + 1, 2, "");
		// table.getCellFormatter().setWordWrap(i + 1, 0, false);
		// table.getCellFormatter().setWordWrap(i + 1, 1, false);
		// table.getCellFormatter().setWordWrap(i + 1, 2, false);
		// table.getFlexCellFormatter().setColSpan(i + 1, 2, 2);
		// }
	}

	@Override
	protected void update() {
		final PatentServiceAsync patentService = PatentService.Util
				.getInstance();

		patentService.getApprovedPatents(new AsyncCallback<List<Patent>>() {

			@Override
			public void onSuccess(List<Patent> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();

				int length = result.size();
				for (int i = 0; i < length; ++i) {
					Patent item = result.get(i);
					table.setText(i + 1, 0, "");
					// table.setText(i + 1, 1, item.getGroupId());
					// table.setText(i + 1, 2, item.getSubmitted());
					// table.setText(i + 1, 2, item.getSubmitted());

					/*
					 * table.setText(0, 0, "Title"); table.setText(0, 1,
					 * "Group"); table.setText(0, 2, "Submission Time");
					 * table.setText(0, 3, "Status");
					 */
				}
				// Select the first row if none is selected.
				if (selectedRow == -1) {
					selectRow(0);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();

			}
		});
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
