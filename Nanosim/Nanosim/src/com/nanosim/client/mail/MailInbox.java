package com.nanosim.client.mail;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.rpc.MailService;
import com.nanosim.client.rpc.MailServiceAsync;
import com.nanosim.model.Mail;

public class MailInbox extends ListContentBase {

	private List<Mail> data;
	private MailDetail detail;
	private final MailServiceAsync mailService = MailService.Util.getInstance();

	private static final int VISIBLE_EMAIL_COUNT = 10;

	private HTML countLabel = new HTML();
	private HTML newerButton = new HTML("<a href='javascript:;'>&lt; newer</a>", true);
	private HTML olderButton = new HTML("<a href='javascript:;'>older &gt;</a>", true);

	private HTML body = new HTML();

	private int startIndex, selectedRow, count = -1;
	private HorizontalPanel navBar = new HorizontalPanel();
	private HorizontalPanel btnBar = new HorizontalPanel();

	public MailInbox() {
		detail = new MailDetail(this);

		Button btnCompose = new Button("Compose");
		btnCompose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				detail.setItem(MailDetail.EditorMode.NEW, null);
				detail.center();
			}
		});
		btnBar.add(btnCompose);

		Button btnView = new Button("View");
		btnView.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (selectedRow >= 0 && data.size() > selectedRow) {
					Mail item = data.get(startIndex + selectedRow);
					detail.setItem(MailDetail.EditorMode.VIEW, item);
					detail.center();
				}
			}
		});
		btnBar.add(btnView);

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

		body.setWordWrap(true);
		body.setStyleName("mail-DetailBody");
		text.add(body);	 
		wrapper3.setStyleName("mail-Detail");
	}

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == olderButton) {
			// Move forward a page.
			startIndex += VISIBLE_EMAIL_COUNT;
			if (startIndex >= count) {
				startIndex -= VISIBLE_EMAIL_COUNT;
			} else {
				styleRow(selectedRow, false);
				selectedRow = -1;
				update();
			}
		} else if (sender == newerButton) {
			// Move back a page.
			startIndex -= VISIBLE_EMAIL_COUNT;
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
				}
			}
		}
	}

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "#");
		table.setText(0, 1, "From Group");
		table.setText(0, 2, "Subject");
		table.setText(0, 3, "Sent");
		table.setText(0, 4, "Unread");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");

		// Create the header row.
		header.setText(0, 0, "Message:");
		header.setText(1, 0, " Subject:");
		header.setText(2, 0, " From:   ");
		header.setText(3, 0, " Date:   ");
		header.getRowFormatter().setStyleName(0, "mail-ListHeader");
		header.getRowFormatter().setStyleName(1, "mail-Detail");
		header.getRowFormatter().setStyleName(2, "mail-Detail");
		header.getRowFormatter().setStyleName(3, "mail-Detail");
	}

	@Override
	public void update() {
		int groupId = nanosim.Group.getGroupId();

		mailService.getMail(groupId, "inbox", new AsyncCallback<List<Mail>>() {

			@Override
			public void onSuccess(List<Mail> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				data = result;

				setItem(data.get(startIndex));

				int length = result.size();

				// Update the older/newer buttons & label.
				count = length;
				int max = startIndex + VISIBLE_EMAIL_COUNT;
				if (max > count) {
					max = count;
				}

				newerButton.setVisible(startIndex != 0);
				olderButton.setVisible(startIndex + VISIBLE_EMAIL_COUNT < count);
				countLabel
				.setText("" + (startIndex + 1) + " - " + max + " of " + count);

				int i = 0;
				/*				for (; i < length; ++i) {
					Mail item = result.get(i);
					table.setText(i + 1, 0, "" + item.getMailId());
					table.setText(i + 1, 1, "" + item.getFromGroupName());
					table.setText(i + 1, 2, "" + item.getSubject());
					table.setText(i + 1, 3, "" + item.getSent());
					table.setText(i + 1, 4, "" + item.getUnread());
				}*/

				//				DateTimeFormat fmt = DateTimeFormat.getMediumDateTimeFormat();
				//				String formatted = "";

				for (; i < VISIBLE_EMAIL_COUNT; ++i) {
					// Don't read past the end.
					if (startIndex + i >= count) {
						break;
					}

					Mail item = result.get(startIndex + i);
					//					formatted = fmt.format(item.getSent());
					table.setText(i + 1, 0, "" + item.getMailId());
					table.setText(i + 1, 1, "" + item.getFromGroupName());
					table.setText(i + 1, 2, "" + item.getSubject());
					table.setText(i + 1, 3, "" + item.getSent());
					table.setText(i + 1, 4, "" + item.getUnread());
				}

				// Clear any remaining slots.
				for (; i < VISIBLE_EMAIL_COUNT; ++i) {
					table.setText(i + 1, 0, "");
					table.setText(i + 1, 1, "");
					table.setText(i + 1, 2, "");
					table.setText(i + 1, 3, "");
					table.setText(i + 1, 4, "");
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
		Mail item = data.get(startIndex + row);
		if (item == null) {
			return;
		}

		styleRow(selectedRow, false);
		styleRow(row, true);

		selectedRow = row;
		setItem(item);		
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

	public void setItem(Mail item) {
		header.setText(1, 1, ".");
		header.setText(1, 1, "" + item.getSubject());
		header.setText(2, 1, "" + item.getFromGroupName());
		header.setText(3, 1, "" + item.getSent());
		body.setHTML("" + item.getMessage());

		if (item.getUnread().equals("y"))
		{
			item.setUnread("n");

			mailService.updateUnread(item, new AsyncCallback<Integer>() {
				@Override
				public void onSuccess(Integer result) {
					if (result != -1)
						nanosim.endLoadingSuccess();
					else
						nanosim.endLoadingFailure();
				}

				@Override
				public void onFailure(Throwable caught) {
					nanosim.endLoadingFailure();
				}
			});
			update();
		}
	}

}