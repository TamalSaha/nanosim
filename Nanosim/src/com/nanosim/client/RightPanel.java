package com.nanosim.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RightPanel extends Composite {

	private static VerticalPanel rightPanel = new VerticalPanel();

	public RightPanel(Widget widget) {
		add(widget);
		initWidget(rightPanel);
	}

	public RightPanel() {
		initWidget(rightPanel);
	}

	@Override
	protected void onLoad() {

	}

	public static void add(Widget widget) {
		widget.addStyleName("mail-StackContent");
		rightPanel.add(widget);
	}

	public static void clear() {
		// widget.addStyleName("mail-StackContent");
		rightPanel.clear();
	}

}
