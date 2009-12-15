package com.nanosim.client.patent;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.RightPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.model.Patent;

public class PatentHome extends StackContentBase {

	private ApprovedPatentList approvedList;
	// private ApprovedPatentList patentList;
	// private ApprovedPatentList patentList;
	private PatentDetail patentDetail = new PatentDetail();

	public PatentHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		this.approvedList = new ApprovedPatentList();
		this.approvedList.setWidth("100%");

		this.patentDetail = new PatentDetail();
		this.patentDetail.setWidth("100%");

		if (nanosim.GroupType.getHasProfileMission()) {
			uiHelper.addImageItem(tree, "Group Patents", images.inbox());
		}
		if (nanosim.GroupType.getHasProfileMission()) {
			uiHelper.addImageItem(tree, "All Approved", images.inbox());
		}
		if (nanosim.GroupType.getHasProfileMission()) {
			uiHelper.addImageItem(tree, "New Submission", images.inbox());
		}
	}

	@Override
	public void loadRightPanel() {
		if (tree.getItemCount() > 0) {
			TreeItem item = tree.getItem(1);
			String title = item.getTitle();

			rightPanel.clear();
			if (title == "Group Patents") {
			} else if (title == "All Approved") {
				rightPanel.add(approvedList);
			} else if (title == "New Submission") {
			}
			item.setSelected(true);
		}
	}

	@Override
	public void displayItem(Object item) {
		patentDetail.setItem((Patent) item);
	}

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Group Patents")) {
			rightPanel.clear();
			// rightPanel.add(objective);
		} else if (title.equals("All Approved")) {
			rightPanel.clear();
			rightPanel.add(approvedList);
		} else if (title.equals("New Submission")) {
			rightPanel.clear();
			// rightPanel.add(objective);
		}
	}
}
