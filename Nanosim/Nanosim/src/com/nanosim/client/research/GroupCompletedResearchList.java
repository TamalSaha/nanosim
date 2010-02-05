package com.nanosim.client.research;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.model.Research;

public class GroupCompletedResearchList extends ListContentBase {

	private List<Research> data;
	private ResearchDetail researchDetail;
	private ProposalDetail proposalDetail;
	private final ResearchServiceAsync researchService = ResearchService.Util
			.getInstance();
	private HorizontalPanel btnBar = new HorizontalPanel();
	
	public GroupCompletedResearchList() {
		researchDetail = new ResearchDetail(this);
		proposalDetail = new ProposalDetail(this);

		Button btnNew = new Button("New Research Proposal");
		btnNew.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				proposalDetail.setItem(ProposalDetail.EditorMode.NEW, null);
				proposalDetail.center();
			}
		});
		btnBar.add(btnNew);

		Button btnView = new Button("View Research Detail");
		btnView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (selectedRow >= 0 && data.size() > selectedRow) {
					Research item = data.get(selectedRow);
					researchDetail
							.setItem(ResearchDetail.EditorMode.VIEW, item);
					researchDetail.center();
				}
			}
		});
		btnBar.add(btnView);
		
		toolbar.add(btnBar);
	}

	@Override
	protected void initTable() {
		// Create the header row.
		setCell(0, 0, "#", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 1, "Title", HasHorizontalAlignment.ALIGN_LEFT);
		setCell(0, 2, "Group", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 3, "Submission Time", HasHorizontalAlignment.ALIGN_CENTER);
		setCell(0, 4, "Reduced Risk", HasHorizontalAlignment.ALIGN_CENTER);

		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	public void update() {
		nanosim.beginLoading();
		researchService.getCompletedResearch(nanosim.Group.getGroupId(),
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
							setCell(i + 1, 2, item.getGroupName(),
									HasHorizontalAlignment.ALIGN_CENTER);
							setCell(i + 1, 3, "" + item.getSubmitted(),
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
