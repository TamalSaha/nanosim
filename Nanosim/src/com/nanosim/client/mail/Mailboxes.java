package com.nanosim.client.mail;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.model.GroupType;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes extends StackContentBase {

	private MailList mailList;
	private MailDetail mailDetail = new MailDetail();

	public Mailboxes() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		NanosimImages images = Nanosim.images;

		this.rightPanel = rightPanel;

		this.mailList = new MailList();
		this.mailList.setWidth("100%");

		this.mailDetail = new MailDetail();
		this.mailDetail.setWidth("100%");

		TreeItem root = new TreeItem(uiHelper.imageItemHTML(images.home(),
				"user@nanosim.com"));
		tree.addItem(root);

		uiHelper.addImageItem(root, "Inbox", images.inbox());
		uiHelper.addImageItem(root, "Drafts", images.drafts());
		uiHelper.addImageItem(root, "Sent", images.sent());
	}

	@Override
	public void loadRightPanel() {
		if (rightPanel != null) {
			rightPanel.clear();
			rightPanel.add(mailList);
			rightPanel.add(mailDetail);
		}
	}

	@Override
	public void displayItem(Object item) {
		mailDetail.setItem((MailItem) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		// TODO Auto-generated method stub

	}
}
