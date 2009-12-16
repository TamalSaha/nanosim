package com.nanosim.client.research;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class ResearchHome extends StackContentBase {

	private GroupCompletedResearchList completedResearchList;
	private GroupIncompleteResearchList incompleteResearchList;
	private AllCurrentResearchList currentResearchList;

	public ResearchHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		this.completedResearchList = new GroupCompletedResearchList();
		this.incompleteResearchList = new GroupIncompleteResearchList();
		this.currentResearchList = new AllCurrentResearchList();

		if (nanosim.GroupType.getHasResearchCompleted()) {
			uiHelper.addImageItem(tree, "Completed Research", images.inbox());
		}
		if (nanosim.GroupType.getHasResearchIncomplete()) {
			uiHelper.addImageItem(tree, "Incomplete Research", images.inbox());
		}
		if (nanosim.GroupType.getHasResearchAllCurrent()) {
			uiHelper.addImageItem(tree, "All Current Research", images.inbox());
		}
	}

	@Override
	public void displayItem(Object item) {
		// patentDetail.setItem((Patent) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Completed Research")) {
			rightPanel.clear();
			rightPanel.add(completedResearchList);
		} else if (title.equals("Incomplete Research")) {
			rightPanel.clear();
			rightPanel.add(incompleteResearchList);
		} else if (title.equals("All Current Research")) {
			rightPanel.clear();
			rightPanel.add(currentResearchList);
		}
	}
}
