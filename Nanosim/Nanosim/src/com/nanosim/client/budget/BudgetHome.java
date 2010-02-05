package com.nanosim.client.budget;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.nanosim.client.Nanosim;
import com.nanosim.client.CenterPanel;
import com.nanosim.client.StackContentBase;
import com.nanosim.client.icons.NanosimImages;

/**
 * Composite that represents a collection of <code>Task</code> items.
 */
public class BudgetHome extends StackContentBase {

	private BudgetTransfer budgetTransfer;
	private BudgetHistory budgetHistory;

	public BudgetHome() {
		singleton = this;
	}

	@Override
	public void loadShortcuts(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		NanosimImages images = Nanosim.images;

		budgetTransfer = new BudgetTransfer();
		uiHelper.addImageItem(tree, "Transfer Money", images.inbox());
		
		budgetHistory = new BudgetHistory();
		uiHelper.addImageItem(tree, "Transaction History", images.inbox());
	}
	
	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		String title = event.getSelectedItem().getTitle();
		if (title.equals("Transfer Money")) {
			centerPanel.clear();
			centerPanel.add(budgetTransfer);
		}
		else if (title.equals("Transaction History")) {
			centerPanel.clear();
			centerPanel.add(budgetHistory);
		}
	}

	public void onLoadRightPanel(TreeItem item) {
		centerPanel.clear();
		centerPanel.add(budgetTransfer);
		centerPanel.add(budgetHistory);
		item.setSelected(true);
	}
}