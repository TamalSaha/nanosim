package com.nanosim.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.icons.NanosimImages;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes extends Composite {

	private static Mailboxes singleton;

	private MailList mailList;
	private MailDetail mailDetail = new MailDetail();
	String width = new String("70%");

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
		TreeItem root = new TreeItem(imageItemHTML(images.home(),
				"user@nanosim.com"));
		tree.addItem(root);

		addImageItem(root, "Inbox", images.inbox());
		addImageItem(root, "Drafts", images.drafts());
		addImageItem(root, "Sent", images.sent());

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

	/**
	 * A helper method to simplify adding tree items that have attached images.
	 * {@link #addImageItem(TreeItem, String, AbstractImagePrototype) code}
	 * 
	 * @param root
	 *            the tree item to which the new item will be added.
	 * @param title
	 *            the text associated with this item.
	 */
	private TreeItem addImageItem(TreeItem root, String title,
			AbstractImagePrototype imageProto) {
		TreeItem item = new TreeItem(imageItemHTML(imageProto, title));
		root.addItem(item);
		return item;
	}

	/**
	 * Generates HTML for a tree item with an attached icon.
	 * 
	 * @param imageProto
	 *            the image prototype to use
	 * @param title
	 *            the title of the item
	 * @return the resultant HTML
	 */
	private String imageItemHTML(AbstractImagePrototype imageProto, String title) {
		return imageProto.getHTML() + " " + title;
	}
}
