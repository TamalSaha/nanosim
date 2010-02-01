package com.nanosim.client.profile;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class ProfileHome extends StackContentBase {

	private ProfileObjective objective;
	private ProfileStatus status;
	private ProfilePassword password;
	private ProfileNotes notes;

	public ProfileHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		status = new ProfileStatus();
		uiHelper.addImageItem(tree, "Status", images.inbox());
		
		if (nanosim.GroupType.getHasProfileMission()) {
			objective = new ProfileObjective();
			uiHelper.addImageItem(tree, "Objective", images.inbox());
		}
		
		password = new ProfilePassword();
		uiHelper.addImageItem(tree, "Password", images.inbox());
		
		notes = new ProfileNotes();
		uiHelper.addImageItem(tree, "Notes", images.inbox());
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Status")) {
			centerPanel.clear();
			centerPanel.add(status);
		}
		if (title.equals("Objective")) {
			centerPanel.clear();
			centerPanel.add(objective);
		}
		if (title.equals("Password")) {
			centerPanel.clear();
			centerPanel.add(password);
		}
		if (title.equals("Notes")) {
			centerPanel.clear();
			centerPanel.add(notes);
		}		
	}
}
