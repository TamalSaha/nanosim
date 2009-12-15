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

	public abstract void loadRightPanel();

	public abstract void onSelection(SelectionEvent<TreeItem> event);

	public void displayItem(MailItem item) {
	}
}
