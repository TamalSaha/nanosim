package com.nanosim.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.UIHelper;
import com.nanosim.client.mail.MailItem;
import com.nanosim.model.GroupType;
import com.nanosim.model.Mail;

public abstract class StackContentBase extends Composite implements
		SelectionHandler<TreeItem> {

	protected static StackContentBase singleton;

	public static StackContentBase get() {
		return singleton;
	}

	protected RightPanel rightPanel;
	protected Tree tree;
	protected UIHelper uiHelper = new UIHelper();

	public StackContentBase() {
		tree = new Tree(Nanosim.images);
		tree.addSelectionHandler(this);
		initWidget(tree);
	}

	public abstract void loadShortcuts(RightPanel rightPanel);

	public void loadRightPanel() {
		if (tree.getItemCount() > 0) {
			TreeItem item = null;
			for (int i = tree.getItemCount(); i-- > 0;) {
				if (tree.getItem(i).isSelected())
					item = tree.getItem(i);
			}
			if (item == null)
				item = tree.getItem(0);
			tree.setSelectedItem(item, true);
		}
	}

	public abstract void onSelection(SelectionEvent<TreeItem> event);

	public void displayItem(Object item) {
	}

	public void displayItem(Mail item) {
	}
}
