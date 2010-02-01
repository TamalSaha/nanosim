package com.nanosim.client.mail;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

/**
 * A tree displaying a set of email folders.
 */
public class MailHome extends StackContentBase {

	private MailInbox mailInbox;
	private MailSent mailSent;
	
	public MailHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		NanosimImages images = Nanosim.images;
		this.centerPanel = centerPanel;

		this.mailInbox = new MailInbox();
		this.mailSent = new MailSent();

		this.mailInbox.setWidth("100%");
		this.mailSent.setWidth("100%");

		uiHelper.addImageItem(tree, "Inbox", images.inbox());
		uiHelper.addImageItem(tree, "Sent", images.sent());
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		centerPanel.clear();
		if (title.equals("Inbox")) {
			centerPanel.clear();
			centerPanel.add(mailInbox);
			//centerPanel.add(mailBody);	
		}	
		else if (title.equals("Sent")) {
			centerPanel.clear();
			centerPanel.add(mailSent);
		}
	}
}
