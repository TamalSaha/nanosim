package com.nanosim.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RightPanel extends Composite {

	private VerticalPanel rightPanel = new VerticalPanel();

	public RightPanel(Widget widget) {
		rightPanel.setStylePrimaryName("nanosim-RightPanel");
		rightPanel.setWidth("100%");
		add(widget);
		initWidget(rightPanel);
	}

	public RightPanel() {
		rightPanel.setStylePrimaryName("nanosim-RightPanel");
		rightPanel.setWidth("100%");
		initWidget(rightPanel);
	}

	public void add(Widget widget) {
		rightPanel.add(widget);
	}

	public void clear() {
		rightPanel.clear();
	}
}
