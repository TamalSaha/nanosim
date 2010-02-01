package com.nanosim.client.nanopost;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.Nanosim;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class NanopostHome extends StackContentBase {
	private NanopostEditor nanopostEditor;
	private NanopostView nanopostView;

	public NanopostHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		nanopostView = new NanopostView();
		uiHelper.addImageItem(tree, "Nanopost View", images.inbox());
		
		if (nanosim.GroupType.getHasProfileMission()) {
			nanopostEditor = new NanopostEditor();
			uiHelper.addImageItem(tree, "Nanopost Editor", images.inbox());
		}
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Nanopost View")) {
			centerPanel.clear();
			centerPanel.add(nanopostView);
		}
		if (title.equals("Nnanopost Editor")) {
			centerPanel.clear();
			centerPanel.add(nanopostEditor);
		}
	}
}