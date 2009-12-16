package com.nanosim.client.patent;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.rpc.PatentService;
import com.nanosim.client.rpc.PatentServiceAsync;
import com.nanosim.model.Patent;

public class NewPatentList extends ContentListBase {

	private List<Patent> data;
	private PatentDetail detail;
	private final PatentServiceAsync patentService = PatentService.Util
			.getInstance();

	public NewPatentList() {
		detail = new PatentDetail();
		Button btnView = new Button("View Proposal");
		btnView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (selectedRow >= 0 && data.size() > selectedRow) {
					Patent item = data.get(selectedRow);
					detail.setItem(item);
					detail.center();
					detail.show();
				}
			}
		});
		toolbar.add(btnView);
	}

	@Override
	protected void initTable() {
		// Create the header row.
		setCell(0, 0, "#", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 1, "Title", HasHorizontalAlignment.ALIGN_LEFT);
		setCell(0, 2, "Group", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 3, "Submission Time", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 4, "Status", HasHorizontalAlignment.ALIGN_CENTER);

		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	protected void update() {
		patentService.getApprovedPatents(new AsyncCallback<List<Patent>>() {

			@Override
			public void onSuccess(List<Patent> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();

				data = result;
				int length = result.size();
				for (int i = 0; i < length; ++i) {
					Patent item = result.get(i);
					setCell(i + 1, 0, "" + item.getPatentId(),
							HasHorizontalAlignment.ALIGN_CENTER);
					setCell(i + 1, 1, item.getResearchTitle(),
							HasHorizontalAlignment.ALIGN_LEFT);
					setCell(i + 1, 2, item.getGroupName(),
							HasHorizontalAlignment.ALIGN_CENTER);
					setCell(i + 1, 3, "", HasHorizontalAlignment.ALIGN_CENTER);
					setCell(i + 1, 4, "Accepted",
							HasHorizontalAlignment.ALIGN_CENTER);
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
