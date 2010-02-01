package com.nanosim.client.profile;

import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.model.Person;

public class ProfileStatus extends ListContentBase {
	
	private final GroupServiceAsync groupService = GroupService.Util
	.getInstance();
	private Nanosim nanosim;
	private HTML title = new HTML();
	FlexTable table2 = new FlexTable();
	
	public ProfileStatus() {
		nanosim = Nanosim.getInstance();
		
		title = new HTML("Group's Current Status", true);
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolbar.add(title);		
	}

	@Override
	protected void initTable() {
		table.addStyleName("cw-FlexTable");
		//table.setCellSpacing(2);
		table.setText(0, 0, "Group Information");
		table.getFlexCellFormatter().setColSpan(0, 0, 4);
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
		
		for (int i = 0; i < 4; ++i){
			table.getCellFormatter().setStyleName(1, i, "cell");
			table.getCellFormatter().setStyleName(4, i, "cell");
		}
		
		table.setText(1, 0, "Group Name");
		table.setText(1, 1, "Group Type");
		table.setText(1, 2, "Current Budget");
		table.setText(1, 3, "Members");
		
		table.setText(3, 0, "Research in Progress");
		table.getFlexCellFormatter().setColSpan(3, 0, 4);
		table.getRowFormatter().setStyleName(3, "mail-ListHeader");
		
		table.setText(4, 0, "#");
		table.setText(4, 1, "Title");
		table.setText(4, 2, "Submission Time");
		table.setText(4, 3, "Status");
	}

	@Override
	public void update() {		
		NumberFormat fmt = NumberFormat.getCurrencyFormat();
		String formatted = fmt.format(nanosim.Group.getBudget());

		table.setText(2, 0, "" + nanosim.Group.getName());
		table.setText(2, 1, "" + nanosim.GroupType.getName());
		table.setText(2, 2, "" + formatted);
		
		groupService.getMembers(nanosim.Group.getGroupId(), new AsyncCallback<List<Person>>() {
			@Override
			public void onSuccess(List<Person> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				
				int length = result.size();
				String members = "";
				
				int i = 0;
				for (; i < length; ++i) {
					Person item = result.get(i);
					if (i==0)
						members = item.getName();
					else
						members = members + ", " + item.getName();
					table.setText(2, 3, "" + members);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();
			}
		});
	}
}