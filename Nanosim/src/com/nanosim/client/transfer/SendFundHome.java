package com.nanosim.client.transfer;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

/**
 * Composite that represents a collection of <code>Task</code> items.
 */
public class SendFundHome extends StackContentBase {

	private SendFundScreen sendFundScreen;

	public SendFundHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		sendFundScreen = new SendFundScreen();
		uiHelper.addImageItem(tree, "Send Fund", images.inbox());

		// if (nanosim.GroupType.getHasProfileMission()) {
		// objective = new Objective();
		// uiHelper.addImageItem(tree, "Objective", images.inbox());
		// }
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Send Fund")) {
			rightPanel.clear();
			rightPanel.add(sendFundScreen);
		}
	}

	public void loadRightPanel() {
		if (tree.getItemCount() > 0) {
			rightPanel.clear();
			rightPanel.add(sendFundScreen);
			tree.getItem(0).setSelected(true);
		}
	}
}