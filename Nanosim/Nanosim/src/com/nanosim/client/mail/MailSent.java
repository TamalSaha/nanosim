package com.nanosim.client.mail;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.rpc.MailService;
import com.nanosim.client.rpc.MailServiceAsync;
import com.nanosim.model.Mail;

public class MailSent extends ListContentBase {

	private List<Mail> data;
	private MailDetail detail;
	private final MailServiceAsync mailService = MailService.Util.getInstance();

	public MailSent() {
		detail = new MailDetail(this);
		Button btnView = new Button("View");
		btnView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (selectedRow >= 0 && data.size() > selectedRow) {
					Mail item = data.get(selectedRow);
					detail.setItem(MailDetail.EditorMode.VIEW, item);
					detail.center();
				}
			}
		});
		toolbar.add(btnView);
	}

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "#");
		table.setText(0, 1, "To Group");
		table.setText(0, 2, "Subject");
		table.setText(0, 3, "Sent");
		table.setText(0, 4, "Unread");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	public void update() {
		int groupId = nanosim.Group.getGroupId();

		mailService.getMail(groupId, "sent", new AsyncCallback<List<Mail>>() {

			@Override
			public void onSuccess(List<Mail> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				data = result;
				int length = result.size();

				for (int i = 0; i < length; ++i) {
					Mail item = result.get(i);
					table.setText(i + 1, 0, "" + item.getMailId());
					table.setText(i + 1, 1, "" + item.getToGroupName());
					table.setText(i + 1, 2, "" + item.getSubject());
					table.setText(i + 1, 3, "" + item.getSent());
					table.setText(i + 1, 4, "" + item.getUnread());
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