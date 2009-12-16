package com.nanosim.client.research;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.ContentListBase;
import com.nanosim.client.Nanosim;
import com.nanosim.client.UIHelper;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;

/**
 * A composite for displaying the details of an email message.
 */
public class ProposalDetail extends DialogBox {

	public enum EditorMode {
		NEW
	}

	private ScrollPanel scroller;
	private List<ResearchType> researchTitles;
	private Nanosim nanosim;
	private ListBox lstTopics;
	private TextArea txtProposal;
	private TextArea txtSources;
	private ListBox lstRisks;

	private ContentListBase m_ListView;
	private Research m_research;
	private EditorMode m_mode;
	private UIHelper uiHelper = new UIHelper();

	private final ResearchServiceAsync researchService = ResearchService.Util
			.getInstance();
	private Button btnSubmit;
	private Button btnCancel;
	private FlowPanel dockPanel;

	public ProposalDetail(ContentListBase listView) {
		nanosim = Nanosim.getInstance();
		m_ListView = listView;

		final FlexTable layout = new FlexTable();
		setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Research Topics:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		lstTopics = new ListBox(false);
		lstTopics.setWidth("400px");
		layout.setWidget(0, 1, lstTopics);

		layout.setHTML(1, 0, "Proposal:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtProposal = new TextArea();
		txtProposal.setPixelSize(400, 150);
		layout.setWidget(1, 1, txtProposal);

		layout.setHTML(2, 0, "Sources:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtSources = new TextArea();
		txtSources.setPixelSize(400, 50);
		layout.setWidget(2, 1, txtSources);

		layout.setHTML(3, 0, "Risk Reduction:");
		cellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		lstTopics = new ListBox(false);
		lstTopics.setWidth("400px");
		layout.setWidget(3, 1, lstRisks);

		layout.setHTML(4, 0, "");
		layout.setHTML(4, 1, "");

		dockPanel = new FlowPanel();
		layout.setWidget(5, 0, dockPanel);
		cellFormatter.setColSpan(5, 0, 2);
		cellFormatter.setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnSubmit = new Button("Submit");
		// dockPanel.add(btnSubmit);
		btnSubmit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getInnerItem();
//				researchService.submitResearch(m_research,
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

	public void getInnerItem() {
		m_research.setGroupId(nanosim.Group.getGroupId());

		int index = lstTopics.getSelectedIndex();
		if (index > -1) {
//			m_research.setResearchTypeId(Integer.parseInt(lstTopics
//					.getValue(index)));
//			m_research.setResearchTitle(lstTopics.getItemText(index));
		}
		m_research.setResearchProposal(txtProposal.getText());
		m_research.setResearchSources(txtSources.getText());
	}

	public void setItem(final EditorMode mode, final Research item) {
		if (researchTitles == null) {
			nanosim.beginLoading();
			ResearchServiceAsync researchService = ResearchService.Util
					.getInstance();
			researchService.getResearchTypeTitles(nanosim.Group.getGroupId(),
					new AsyncCallback<List<ResearchType>>() {

						@Override
						public void onSuccess(List<ResearchType> result) {
							if (result == null || result.size() == 0) {
								nanosim.endLoadingFailure();
								return;
							}
							nanosim.endLoadingSuccess();
							// load data
							researchTitles = result;
							for (ResearchType researchType : result) {
								lstTopics.addItem(researchType.getTitle(), ""
										+ researchType.getResearchTypeId());
							}
							setInnerItem(mode, item);
						}

						@Override
						public void onFailure(Throwable caught) {
							nanosim.endLoadingFailure();
						}
					});

		} else
			setInnerItem(mode, item);
	}

	public void setInnerItem(EditorMode mode, Research item) {
		m_research = item;
		m_mode = mode;

		switch (mode) {
		case NEW:
			dockPanel.clear();
			dockPanel.add(btnSubmit);
			dockPanel.add(btnCancel);

			setText("Research*");
			m_research = new Research();
			//m_research.setApproved("n");
			lstTopics.setItemSelected(0, true);
			txtProposal.setText("");
			txtSources.setText("");
			lstRisks.setItemSelected(0, true);
			break;
		default:
			break;
		}
	}
}
