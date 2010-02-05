package com.nanosim.client.budget;

import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.rpc.BudgetService;
import com.nanosim.client.rpc.BudgetServiceAsync;
import com.nanosim.model.Budget;

public class BudgetHistory extends ListContentBase {

	//private MailDetail detail;
	private final BudgetServiceAsync budgetService = BudgetService.Util.getInstance();
	private FlexCellFormatter cellFormatter = table.getFlexCellFormatter();

	public BudgetHistory() {
//		toolbar.add(btnView);
	}

	@Override
	protected void initTable() {
		// Create the header row.
		table.setText(0, 0, "Time");
		table.setText(0, 1, "Description");
		table.setText(0, 2, "Debit");
		table.setText(0, 3, "Credit");
		table.setText(0, 4, "Balance");
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
	}

	@Override
	public void update() {
		int groupId = nanosim.Group.getGroupId();

		budgetService.getTransactions(groupId, new AsyncCallback<List<Budget>>() {

			@Override
			public void onSuccess(List<Budget> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				int length = result.size();

				for (int i = 0; i < length; ++i) {
					Budget item = result.get(i);
					table.setText(i + 1, 0, "" + item.getTime());
					table.setText(i + 1, 1, "" + item.getPurpose());
					
					cellFormatter.setHorizontalAlignment(i + 1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
					cellFormatter.setHorizontalAlignment(i + 1, 3, HasHorizontalAlignment.ALIGN_RIGHT);
					cellFormatter.setHorizontalAlignment(i + 1, 4, HasHorizontalAlignment.ALIGN_RIGHT);
					
					table.setText(i + 1, 2, "-");
					table.setText(i + 1, 3, "-");
					
					NumberFormat fmt = NumberFormat.getCurrencyFormat();
					String formatted = fmt.format(item.getCredit());
					
					
					if (item.getCredit() < 0){
						table.setText(i + 1, 2, "" + formatted);
					}
					else if (item.getCredit() > 0){
						table.setText(i + 1, 3, "" + formatted);
					}
					
					formatted = fmt.format(item.getTotal());
					table.setText(i + 1, 4, "" + formatted);
				}
				// Select the first row if none is selected.
				if (selectedRow == -1) {
					selectRow(0);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();
			}
		});
	}

	private void selectRow(int row) {
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			if (selected) {
				table.getRowFormatter().addStyleName(row + 1,
						"mail-SelectedRow");
			} else {
				table.getRowFormatter().removeStyleName(row + 1,
						"mail-SelectedRow");
			}
		}
	}
}
