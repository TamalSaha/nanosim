package com.nanosim.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.UIHelper;
import com.nanosim.client.mail.MailItem;
import com.nanosim.model.GroupType;

public abstract class StackContentBase extends Composite {

	protected static StackContentBase singleton;

	public static StackContentBase get() {
		return singleton;
	}

	protected RightPanel rightPanel;
	protected Tree tree;
	protected UIHelper uiHelper = new UIHelper();

	public StackContentBase() {
		tree = new Tree(Nanosim.images);
		initWidget(tree);
	}

	public abstract void loadShortcuts(RightPanel rightPanel,
			GroupType groupType);

	public abstract void loadRightPanel();

	public void displayItem(MailItem item) {
	}
}
