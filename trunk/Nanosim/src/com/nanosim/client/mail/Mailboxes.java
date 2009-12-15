package com.nanosim.client.mail;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes extends StackContentBase {

	private MailList mailList;
	private MailDetail mailDetail = new MailDetail();
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
		this.mailDetail = new MailDetail();

		this.mailList.setWidth("100%");
		this.mailDetail.setWidth("100%");
		this.mailInbox.setWidth("100%");

		//		TreeItem root = new TreeItem(uiHelper.imageItemHTML(images.home(),
		//				"user@nanosim.com"));
		//		tree.addItem(root);

		uiHelper.addImageItem(tree, "Inbox", images.inbox());
		uiHelper.addImageItem(tree, "Sent", images.sent());
		uiHelper.addImageItem(tree, "Compose", images.drafts());		
	}

	@Override
	public void loadRightPanel() {
		//		if (rightPanel != null) {
		//			rightPanel.clear();
		//			rightPanel.add(mailList);
		//			rightPanel.add(mailDetail);
		//		}
		if (tree.getItemCount() > 0) {
			TreeItem item = tree.getItem(0);
			String title = item.getTitle();

			rightPanel.clear();
			if (title.equals("Inbox")) {
				rightPanel.add(mailList);
				rightPanel.add(mailDetail);
			} else if (title.equals("Compose")) {
				//TODO mailNew
				rightPanel.add(mailInbox);
				//	rightPanel.add(mailNew);				
			} else if (title.equals("Sent")) {
				//TODO display sent items
				rightPanel.add(mailList);
				rightPanel.add(mailDetail);
			}
			item.setSelected(true);
		}
	}

	@Override
	public void displayItem(Object item) {
		mailDetail.setItem((MailItem) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		rightPanel.clear();
		//rightPanel.add(mailInbox);
		if (title.equals("Inbox")) {
			rightPanel.clear();
			rightPanel.add(mailList);
			rightPanel.add(mailDetail);
		} else if (title.equals("Compose")) {
			//TODO mailNew
			rightPanel.add(mailInbox);	
		} else if (title.equals("Sent")) {
			rightPanel.clear();
			//TODO display sent items
			rightPanel.add(mailList);
			rightPanel.add(mailDetail);
		}
	}
}
