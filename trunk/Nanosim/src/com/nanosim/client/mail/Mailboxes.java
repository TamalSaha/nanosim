package com.nanosim.client.mail;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.UIHelper;
import com.nanosim.client.icons.NanosimImages;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes extends Composite {

	private static Mailboxes singleton;

	private MailList mailList;
	private MailDetail mailDetail = new MailDetail();
	private String width = new String("100%");
	private UIHelper uiHelper = new UIHelper();

	public static Mailboxes get() {
		return singleton;
	}

	/**
	 * Displays the specified item.
	 * 
	 * @param item
	 */
	public void displayItem(MailItem item) {
		mailDetail.setItem(item);
	}

	private Tree tree;

	/**
	 * Constructs a new mailboxes widget with a bundle of images.
	 * 
	 * @param images
	 *            a bundle that provides the images for this widget
	 */
	public Mailboxes() {
		singleton = this;
		NanosimImages images = Nanosim.images;
		tree = new Tree(images);
		TreeItem root = new TreeItem(uiHelper.imageItemHTML(images.home(),
				"user@nanosim.com"));
		tree.addItem(root);

		uiHelper.addImageItem(root, "Inbox", images.inbox());
		uiHelper.addImageItem(root, "Drafts", images.drafts());
		uiHelper.addImageItem(root, "Sent", images.sent());

		// MailList uses Mail.get() in its constructor, so initialize it after
		// 'singleton'.
		/*
		 * mailList = new MailList(); // // // Create the right panel,
		 * containing the email list & details. RightPanel.add(mailList);
		 * RightPanel.add(mailDetail); mailList.setWidth(width);
		 * mailDetail.setWidth(width);
		 */

		root.setState(true);
		initWidget(tree);
	}

	public void loadRightPanel() {
		RightPanel.clear();
		mailList = new MailList();
		//
		// // Create the right panel, containing the email list & details.
		RightPanel.add(mailList);
		RightPanel.add(mailDetail);
		mailList.setWidth(width);
		mailDetail.setWidth(width);
	}
}
