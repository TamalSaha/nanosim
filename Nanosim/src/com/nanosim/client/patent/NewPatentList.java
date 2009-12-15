package com.nanosim.client.patent;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.rpc.PatentService;
import com.nanosim.client.rpc.PatentServiceAsync;
import com.nanosim.model.Patent;

public class NewPatentList extends ContentListBase {

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "#");
		table.setText(0, 1, "Title");
		table.setText(0, 2, "Group");
		table.setText(0, 3, "Submission Time");
		table.setText(0, 4, "Status");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	protected void update() {
		PatentServiceAsync patentService = PatentService.Util.getInstance();

		patentService.getNewPatents(new AsyncCallback<List<Patent>>() {

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
					table.setText(i + 1, 0, "" + item.getPatentId());
					table.setText(i + 1, 1, item.getResearchTitle());
					table.setText(i + 1, 2, item.getGroupName());
					table.setText(i + 1, 3, "");
					table.setText(i + 1, 4, "Accepted");
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
