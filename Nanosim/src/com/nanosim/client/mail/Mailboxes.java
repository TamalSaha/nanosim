package com.nanosim.client.mail;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.model.Mail;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes extends StackContentBase {

	private MailList mailList;
	private MailBody mailBody;
	private MailInbox mailInbox;

	public Mailboxes() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		NanosimImages images = Nanosim.images;
		this.rightPanel = rightPanel;

		this.mailInbox = new MailInbox();
		this.mailList = new MailList();
		this.mailBody = new MailBody();

		this.mailList.setWidth("100%");
		this.mailBody.setWidth("100%");
		this.mailInbox.setWidth("100%");

		uiHelper.addImageItem(tree, "Inbox", images.inbox());
		uiHelper.addImageItem(tree, "Sent", images.sent());
		//uiHelper.addImageItem(tree, "Compose", images.drafts());
	}

	@Override
	public void displayItem(Mail item) {
		mailBody.setItem(item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		rightPanel.clear();
		if (title.equals("Inbox")) {
			rightPanel.clear();
			rightPanel.add(mailInbox);
		} else if (title.equals("Sent")) {
			rightPanel.clear();
			// TODO display sent items
			rightPanel.add(mailInbox);
			//rightPanel.add(mailList);
			//rightPanel.add(mailBody);
			//rightPanel.add(mailDetail);
		}
	}
}
