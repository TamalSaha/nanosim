package com.nanosim.client.research;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.ListContentBase;
import com.nanosim.model.Research;

/**
 * A composite for displaying the details of an email message.
 */
public class ResearchDetail extends DialogBox {

	public enum EditorMode {
		PUBLIC, VIEW
	}

	private ScrollPanel scroller;
	//private Nanosim nanosim;
	private TextBox txtTitle;
	private TextBox txtGroup;
	private TextArea txtProposal;
	private TextArea txtSources;

	//private ListContentBase m_ListView;
	//private Research m_research;
	//private EditorMode m_mode;
	//private UIHelper uiHelper = new UIHelper();

	//private final ResearchServiceAsync researchService = ResearchService.Util
	//		.getInstance();
	private Button btnPublic;
	private Button btnCancel;
	private FlowPanel dockPanel;

	public ResearchDetail(ListContentBase listView) {
		//nanosim = Nanosim.getInstance();
		//m_ListView = listView;

		final FlexTable layout = new FlexTable();
		setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Title:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtTitle = new TextBox();
		txtTitle.setWidth("400px");
		layout.setWidget(0, 1, txtTitle);

		layout.setHTML(1, 0, "Group:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtGroup = new TextBox();
		txtGroup.setWidth("400px");
		layout.setWidget(1, 1, txtGroup);

		layout.setHTML(2, 0, "Proposal:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtProposal = new TextArea();
		txtProposal.setPixelSize(400, 150);
		layout.setWidget(2, 1, txtProposal);

		layout.setHTML(3, 0, "Sources:");
		cellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtSources = new TextArea();
		txtSources.setPixelSize(400, 50);
		layout.setWidget(3, 1, txtSources);

		layout.setHTML(4, 0, "");
		layout.setHTML(4, 1, "");

		dockPanel = new FlowPanel();
		layout.setWidget(5, 0, dockPanel);
		cellFormatter.setColSpan(5, 0, 2);
		cellFormatter.setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnPublic = new Button("Approve");
		btnPublic.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
//				nanosim.beginLoading();
//				getInnerItem();
//				m_research.setApproved("y");
//				researchService.approveResearch(m_research,
//						new AsyncCallback<Integer>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								nanosim.endLoadingFailure();
//							}
//
//							@Override
//							public void onSuccess(Integer result) {
//								if (result < 0) {
//									nanosim.endLoadingFailure();
//									return;
//								}
//								// update background
//								m_ListView.update();
//								nanosim.endLoadingSuccess();
//								hide();
//							}
//						});
			}
		});

		btnCancel = new Button("Cancel");
		dockPanel.add(btnCancel);
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				dockPanel.clear();
			}
		});

		scroller = new ScrollPanel(layout);
		setWidget(scroller);
	}

	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth - scroller.getAbsoluteLeft() - 9;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight - scroller.getAbsoluteTop() - 9;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		scroller.setPixelSize(scrollWidth, scrollHeight);
	}

	public void setItem(EditorMode mode, Research item) {
		//m_research = item;
		//m_mode = mode;

		switch (mode) {
		case VIEW:
			dockPanel.clear();
			dockPanel.add(btnCancel);

			setText("Research # " + item.getResearchId());
			txtTitle.setText(item.getResearchTypeTitle());
			txtGroup.setText(item.getGroupName());
			txtProposal.setText(item.getResearchProposal());
			txtSources.setText(item.getResearchSources());
			break;
		default:
			break;
		}
	}
}
