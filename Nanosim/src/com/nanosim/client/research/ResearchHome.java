package com.nanosim.client.research;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class ResearchHome extends StackContentBase {

	private GroupCompletedResearchList completedResearchList;
	private GroupIncompleteResearchList incompleteResearchList;
	private AllCurrentResearchList currentResearchList;

	private TechnologyTreeView techTreeView;

	public ResearchHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		this.completedResearchList = new GroupCompletedResearchList();
		this.incompleteResearchList = new GroupIncompleteResearchList();
		this.currentResearchList = new AllCurrentResearchList();

		this.techTreeView = new TechnologyTreeView();

		if (nanosim.GroupType.getHasResearchCompleted()) {
			uiHelper.addImageItem(tree, "Completed Research", images.inbox());
		}
		if (nanosim.GroupType.getHasResearchIncomplete()) {
			uiHelper.addImageItem(tree, "Incomplete Research", images.inbox());
		}
		if (nanosim.GroupType.getHasResearchAllCurrent()) {
			uiHelper.addImageItem(tree, "All Current Research", images.inbox());
		}
		uiHelper.addImageItem(tree, "Technology Tree", images.inbox());
	}

	@Override
	public void displayItem(Object item) {
		// patentDetail.setItem((Patent) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Completed Research")) {
			centerPanel.clear();
			centerPanel.add(completedResearchList);
		} else if (title.equals("Incomplete Research")) {
			centerPanel.clear();
			centerPanel.add(incompleteResearchList);
		} else if (title.equals("All Current Research")) {
			centerPanel.clear();
			centerPanel.add(currentResearchList);
		} else if (title.equals("Technology Tree")) {
			centerPanel.clear();
			centerPanel.add(techTreeView);
		}
	}
}
