package com.nanosim.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.model.Person;

public class HomeScreen extends Composite {

	public static String CUR_THEME = "chrome";

	private final GroupServiceAsync groupService = GroupService.Util
			.getInstance();

	private Person person;
	private Group group;
	private GroupType groupType;

	public RightPanel rightPanel;
	public TopPanel topPanel;
	public LeftPanel leftPanel;
	String width = new String("80%");

	public HomeScreen(Person person) {
		this.person = person;
		// final String initToken = History.getToken();

		final DockPanel outer = new DockPanel();
		outer.setWidth("100%");
		outer.setSpacing(4);

		topPanel = new TopPanel();
		outer.add(topPanel, DockPanel.NORTH);

		leftPanel = new LeftPanel();
		outer.add(leftPanel, DockPanel.WEST);

		rightPanel = new RightPanel();
		outer.add(rightPanel, DockPanel.CENTER);
		// outer.setCellWidth(rightPanel, "100%");

		groupService.getGroup(person.getGroupId(), new AsyncCallback<Group>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Group result) {
				group = result;
				groupService.getGroupType(group.getGroupTypeId(),
						new AsyncCallback<GroupType>() {

							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(GroupType result) {
								groupType = result;
								initChildWidgets(outer);
							}
						});
			}
		});
		initWidget(outer);
	}

	private void initChildWidgets(DockPanel outer) {
		topPanel.setHeader(person, group);
		leftPanel.loadShortcuts(groupType);
	}
}
