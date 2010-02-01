package com.nanosim.client.patent;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

public class PatentHome extends StackContentBase {

	private GroupPatentList groupPatentList;
	private ApprovedPatentList approvedList;
	private NewPatentList newPatentList;

	public PatentHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		this.groupPatentList = new GroupPatentList();
		this.approvedList = new ApprovedPatentList();
		this.newPatentList = new NewPatentList();

		if (nanosim.GroupType.getHasPatentsGroupPatents()) {
			uiHelper.addImageItem(tree, "Group Patents", images.inbox());
		}
		if (nanosim.GroupType.getHasPatentsAllApproved()) {
			uiHelper.addImageItem(tree, "All Approved", images.inbox());
		}
		if (nanosim.GroupType.getHasPatentsNewSubmission()) {
			uiHelper.addImageItem(tree, "New Submission", images.inbox());
		}
	}

	@Override
	public void displayItem(Object item) {
		// patentDetail.setItem((Patent) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Group Patents")) {
			centerPanel.clear();
			centerPanel.add(groupPatentList);
		} else if (title.equals("All Approved")) {
			centerPanel.clear();
			centerPanel.add(approvedList);
		} else if (title.equals("New Submission")) {
			centerPanel.clear();
			centerPanel.add(newPatentList);
		}
	}
}
