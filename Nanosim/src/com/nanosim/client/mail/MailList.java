package com.nanosim.client.mail;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.MailService;
import com.nanosim.client.rpc.MailServiceAsync;
import com.nanosim.model.Mail;

/**
 * A composite that displays a list of emails that can be selected.
 */
public class MailList extends Composite implements ClickHandler {

	protected Nanosim nanosim;
	private final MailServiceAsync mailService = MailService.Util
	.getInstance();
	
	private static final int VISIBLE_EMAIL_COUNT = 10;

	private HTML countLabel = new HTML();
	private HTML newerButton = new HTML(
			"<a href='javascript:;'>&lt; newer</a>", true);
	private HTML olderButton = new HTML(
			"<a href='javascript:;'>older &gt;</a>", true);
	private int startIndex, selectedRow, count = -1;
	private FlexTable table = new FlexTable();
	private HorizontalPanel navBar = new HorizontalPanel();
	private ArrayList <Mail> items = new ArrayList<Mail>();

	public MailList() {
		this.nanosim = Nanosim.getInstance();
		
		// Setup the table.
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("100%");

		// Hook up events.
		table.addClickHandler(this);
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

		initWidget(table);
		//setStyleName("mail-List");

		initTable();
		update();
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

	/**
	 * Initializes the table so that it contains enough rows for a full page of
	 * emails. Also creates the images that will be used as 'read' flags.
	 */
	private void initTable() {
		// Create the header row.
		table.setText(0, 0, "#");
		table.setText(0, 1, "From");
		table.setText(0, 2, "Sent");
		table.setText(0, 3, "Subject");
		table.setText(0, 4, "Unread");
		table.setWidget(0, 5, navBar);
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");

		// Initialize the rest of the rows.
		for (int i = 0; i < VISIBLE_EMAIL_COUNT; ++i) {
			table.setText(i + 1, 0, "");
			table.setText(i + 1, 1, "");
			table.setText(i + 1, 2, "");
			table.setText(i + 1, 3, "");
			table.setText(i + 1, 4, "");
			table.getCellFormatter().setWordWrap(i + 1, 0, false);
			table.getCellFormatter().setWordWrap(i + 1, 1, false);
			table.getCellFormatter().setWordWrap(i + 1, 2, false);
			table.getCellFormatter().setWordWrap(i + 1, 3, false);
			table.getCellFormatter().setWordWrap(i + 1, 4, false);
			table.getCellFormatter().setWordWrap(i + 1, 5, false);
			table.getFlexCellFormatter().setColSpan(i + 1, 2, 2);
			// table.setHeight("10");
		}
	}

	private void update() {
		// TODO fix groupId
		int groupId = 47;

		mailService.getMail(groupId, "inbox", new AsyncCallback<List<Mail>>() {

			@Override
			public void onSuccess(List<Mail> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();

				int length = result.size();
				items = (ArrayList<Mail>) result;
				
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

				// Show the selected emails.
				int i = 0;
				for (; i < VISIBLE_EMAIL_COUNT; ++i) {
					// Don't read past the end.
					if (startIndex + i >= count) {
						break;
					}
					
					Mail item = result.get(i);
					table.setText(i + 1, 0, "" + item.getMailId());
					table.setText(i + 1, 1, "" + item.getFromGroupName());
					table.setText(i + 1, 2, "" + item.getSent());
					table.setText(i + 1, 3, "" + item.getSubject());
					table.setText(i + 1, 4, "" + item.getUnread());
				}

				// Clear any remaining slots.
				for (; i < VISIBLE_EMAIL_COUNT; ++i) {
					table.setHTML(i + 1, 0, "&nbsp;");
					table.setHTML(i + 1, 1, "&nbsp;");
					table.setHTML(i + 1, 2, "&nbsp;");
					table.setHTML(i + 1, 3, "&nbsp;");
					table.setHTML(i + 1, 4, "&nbsp;");
				}

				// Select the first row if none is selected.
				if (selectedRow == -1) {
					selectRow(0);
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
	
	/**
	 * Selects the given row (relative to the current page).
	 * 
	 * @param row
	 *            the row to be selected
	 */
	private void selectRow(int row) {
		// When a row (other than the first one, which is used as a header) is
		// selected, display its associated MailItem.
		Mail item = items.get(startIndex + row);
		if (item == null) {
			return;
		}

		styleRow(selectedRow, false);
		styleRow(row, true);

		//item.read = true;
		selectedRow = row;
		Mailboxes.get().displayItem(item);
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
