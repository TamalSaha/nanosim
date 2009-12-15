package com.nanosim.client.profile;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class ProfileHome extends StackContentBase {

	private Objective objective;

	public ProfileHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		if (nanosim.GroupType.getHasProfileMission()) {
			objective = new Objective();
			uiHelper.addImageItem(tree, "Objective", images.inbox());
		}
	}

	@Override
	public void loadRightPanel() {
		if (tree.getItemCount() > 0) {
			rightPanel.clear();
			rightPanel.add(objective);
			tree.getItem(0).setSelected(true);
		}
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Objective")) {
			rightPanel.clear();
			rightPanel.add(objective);
		}
	}
}
