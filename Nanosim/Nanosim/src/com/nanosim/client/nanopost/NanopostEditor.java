package com.nanosim.client.nanopost;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.rpc.NanopostService;
import com.nanosim.client.rpc.NanopostServiceAsync;
import com.nanosim.model.Nanopost;

public class NanopostEditor extends ListContentBase {

	private List<Nanopost> data;
	private NanopostDetail detail;
	private final NanopostServiceAsync nanopostService = NanopostService.Util.getInstance();

	private static final int VISIBLE_COUNT = 5;

	private HTML countLabel = new HTML();
	private HTML newerButton = new HTML("<a href='javascript:;'>&lt; newer</a>", true);
	private HTML olderButton = new HTML("<a href='javascript:;'>older &gt;</a>", true);

	private int startIndex, selectedRow, count = -1;
	private HorizontalPanel navBar = new HorizontalPanel();
	private HorizontalPanel btnBar = new HorizontalPanel();

	public NanopostEditor() {
		detail = new NanopostDetail(this);

		Button btnNew = new Button("New Article");
		btnNew.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				detail.setItem(NanopostDetail.EditorMode.NEW, null);
				detail.center();
			}
		});
		btnBar.add(btnNew);
		toolbar.add(btnBar);

		// Hook up events.
		newerButton.addClickHandler(this);
		olderButton.addClickHandler(this);

		// Create the 'navigation' bar at the upper-right.
		HorizontalPanel innerNavBar = new HorizontalPanel();
		navBar.setStyleName("mail-ListNavBar");
		innerNavBar.add(newerButton);
		innerNavBar.add(countLabel);
		innerNavBar.add(olderButton);

		navBar.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		navBar.add(innerNavBar);
		navBar.setWidth("100%");

		toolbar.add(navBar);
	}

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == olderButton) {
			// Move forward a page.
			startIndex += VISIBLE_COUNT;
			if (startIndex >= count) {
				startIndex -= VISIBLE_COUNT;
			} else {
				styleRow(selectedRow, false);
				selectedRow = -1;
				update();
			}
		} else if (sender == newerButton) {
			// Move back a page.
			startIndex -= VISIBLE_COUNT;
			if (startIndex < 0) {
				startIndex = 0;
			} else {
				styleRow(selectedRow, false);
				selectedRow = -1;
				update();
			}
		} else if (sender == table) {
			// Select the row that was clicked (-1 to account for header row).
			Cell cell = table.getCellForEvent(event);
			if (cell != null) {
				int row = cell.getRowIndex();
				if (row > 0) {
					selectRow(row - 1);
					Nanopost item = data.get(selectedRow);
					detail.setItem(NanopostDetail.EditorMode.EDIT, item);
					detail.center();
				}
			}
		}
	}

	@Override
	protected void initTable() {
		// Create the header row.
		table.setHTML(0, 0, "Latest News");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
		cellFormatter = table.getFlexCellFormatter();
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
	}

	@Override
	public void update() {
		nanopostService.getPosts(new AsyncCallback<List<Nanopost>>() {

			@Override
			public void onSuccess(List<Nanopost> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				data = result;

				int length = result.size();

				// Update the older/newer buttons & label.
				count = length;
				int max = startIndex + VISIBLE_COUNT;
				if (max > count) {
					max = count;
				}

				newerButton.setVisible(startIndex != 0);
				olderButton.setVisible(startIndex + VISIBLE_COUNT < count);
				countLabel
				.setText("" + (startIndex + 1) + " - " + max + " of " + count);

				int i = 0;

				for (; i < VISIBLE_COUNT; ++i) {
					// Don't read past the end.
					if (startIndex + i >= count) {
						break;
					}

					Nanopost item = result.get(startIndex + i);
					table.setHTML(i + 1, 0, "" 
							+ "<h4>" +item.getPostDate() + " - " + item.getSubject() + "</h4>"
							+ "" + item.getBody() + "<br>"
							+ "<br>Posted by: " + "<i>" + item.getWriter() + "</i><br><br><br>");
					detail.getComments(item.getNanopostId());
				}

				// Clear any remaining slots.
				for (; i < VISIBLE_COUNT; ++i) {
					table.setText(i + 1, 0, "");
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
		// When a row (other than the first one, which is used as a header) is
		// selected, display its associated MailItem.
		Nanopost item = data.get(startIndex + row);
		if (item == null) {
			return;
		}

		styleRow(selectedRow, false);
		styleRow(row, true);

		selectedRow = row;
		//setItem(item);		
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