package com.nanosim.client.research;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.icons.TechnologyTreeImage;

/**
 * A composite for displaying the details of an email message.
 */
public class TechnologyTreeView extends Composite {

	public static final TechnologyTreeImage techTreeImage = (TechnologyTreeImage) GWT
			.create(TechnologyTreeImage.class);

	public TechnologyTreeView() {
		final FlexTable layout = new FlexTable();
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		layout.setWidth("100%");

		layout.setWidget(0, 0, techTreeImage.techTree().createImage());
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		initWidget(layout);
		setWidth("100%");

		// FlowPanel dockPanel = new FlowPanel();
		// dockPanel.add(techTreeImage.techTree().createImage());
		// initWidget(new ScrollPanel(dockPanel));
	}
}
