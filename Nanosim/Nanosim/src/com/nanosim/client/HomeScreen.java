package com.nanosim.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.model.Group;
import com.nanosim.model.GroupType;

public class HomeScreen extends Composite {

	public static String CUR_THEME = "chrome";

	private final GroupServiceAsync groupService = GroupService.Util
			.getInstance();

	public CenterPanel centerPanel;
	public TopPanel topPanel;
	public LeftPanel leftPanel;
	public RightPanel rightPanel;

	private Nanosim nanosim;

	public HomeScreen() {
		this.nanosim = Nanosim.getInstance();
		// final String initToken = History.getToken();

		final DockPanel outer = new DockPanel();
		outer.setWidth("100%");
		outer.setSpacing(4);

		topPanel = new TopPanel();
		outer.add(topPanel, DockPanel.NORTH);

		leftPanel = new LeftPanel();
		outer.add(leftPanel, DockPanel.WEST);
		
		rightPanel = new RightPanel();
		outer.add(rightPanel, DockPanel.EAST);

		centerPanel = new CenterPanel();
		outer.add(centerPanel, DockPanel.CENTER);
		outer.setCellWidth(centerPanel, "100%");

		groupService.getGroup(nanosim.Person.getGroupId(),
				new AsyncCallback<Group>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Group result) {
						nanosim.Group = result;
						groupService.getGroupType(nanosim.Group
								.getGroupTypeId(),
								new AsyncCallback<GroupType>() {

									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(GroupType result) {
										nanosim.GroupType = result;
										initChildWidgets(outer);
									}
								});
					}
				});
		initWidget(outer);
	}

	private void initChildWidgets(DockPanel outer) {
		topPanel.setHeader();
		leftPanel.loadShortcuts(centerPanel);
	}
}
