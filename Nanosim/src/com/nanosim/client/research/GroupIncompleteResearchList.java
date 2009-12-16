package com.nanosim.client.research;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.model.Research;
import com.nanosim.model.RiskCertificate;

public class GroupIncompleteResearchList extends ContentListBase {

	private List<Research> data;
	private ResearchDetail detail;
	private final ResearchServiceAsync researchService = ResearchService.Util
			.getInstance();

	public GroupIncompleteResearchList() {
		detail = new ResearchDetail(this);
		Button btnView = new Button("View Research Detail");
		btnView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (selectedRow >= 0 && data.size() > selectedRow) {
					Research item = data.get(selectedRow);
					detail.setItem(ResearchDetail.EditorMode.VIEW, item);
					detail.center();
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
		setCell(0, 2, "Submission Time", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 3, "Status", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 4, "Reduced Risk", HasHorizontalAlignment.ALIGN_CENTER);

		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	public void update() {
		nanosim.beginLoading();
		researchService.getIncompleteResearch(nanosim.Group.getGroupId(),
				new AsyncCallback<List<Research>>() {

					@Override
					public void onSuccess(List<Research> result) {
						if (result == null) {
							nanosim.endLoadingFailure();
							return;
						}
						nanosim.endLoadingSuccess();

						data = result;
						int length = result.size();
						if (length == 0) {
							clearTable();
						}
						for (int i = 0; i < length; ++i) {
							Research item = result.get(i);
							setCell(i + 1, 0, "" + item.getResearchId(),
									HasHorizontalAlignment.ALIGN_CENTER);
							setCell(i + 1, 1, item.getResearchTypeTitle(),
									HasHorizontalAlignment.ALIGN_LEFT);
							setCell(i + 1, 2, "" + item.getSubmitted(),
									HasHorizontalAlignment.ALIGN_CENTER);
							// if($time_left <= 0) {
							// if($failed == "y")
							// print
							// "	<TD CLASS=\"rejectedCell\" WIDTH=\"40%\" ALIGN=\"CENTER\">Failed: $failed_message</TD>\n";
							// else
							// print
							// "	<TD CLASS=\"acceptedCell\" WIDTH=\"40%\" ALIGN=\"CENTER\">Completed Successfully.</TD>\n";
							// }
							// else
							// print
							// "	<TD CLASS=\"buildingCell\" WIDTH=\"40%\" ALIGN=\"CENTER\">$time_left minutes left.</TD>\n";
							setCell(i + 1, 3, "",
									HasHorizontalAlignment.ALIGN_CENTER);

							setCell(i + 1, 4, item.getRiskReducion(),
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
