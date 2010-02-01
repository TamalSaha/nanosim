package com.nanosim.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class CenterPanel extends Composite {

	private ScrollPanel centerPanel = new ScrollPanel();

	public CenterPanel(Widget widget) {
		centerPanel.setStylePrimaryName("nanosim-CenterPanel");
		centerPanel.setWidth("100%");
		centerPanel.setHeight("100%");
		add(widget);
		initWidget(centerPanel);
	}

	public CenterPanel() {
		centerPanel.setStylePrimaryName("nanosim-CenterPanel");
		centerPanel.setWidth("100%");
		centerPanel.setHeight("100%");
		initWidget(centerPanel);
	}

	public void add(Widget widget) {
		centerPanel.add(widget);
	}

	public void clear() {
		centerPanel.clear();
	}
}
