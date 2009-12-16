package com.nanosim.client.patent;

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
import com.nanosim.client.Nanosim;
import com.nanosim.client.UIHelper;
import com.nanosim.client.rpc.PatentService;
import com.nanosim.client.rpc.PatentServiceAsync;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.model.Patent;
import com.nanosim.model.ResearchType;

/**
 * A composite for displaying the details of an email message.
 */
public class PatentDetail extends DialogBox {

	public enum Mode {
		NEW, APPROVE, VIEW, EDIT
	}

	private ScrollPanel scroller;
	private List<ResearchType> researchTitles;
	private Nanosim nanosim;
	private ListBox lstTitles;
	private TextArea txtProposal;
	private TextArea txtResponse;

	private Patent m_patent;
	private UIHelper uiHelper = new UIHelper();

	private final PatentServiceAsync patentService = PatentService.Util
			.getInstance();

	public PatentDetail() {
		nanosim = Nanosim.getInstance();

		final FlexTable layout = new FlexTable();
		setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Title:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		lstTitles = new ListBox(false);
		lstTitles.setWidth("400px");
		layout.setWidget(0, 1, lstTitles);

		layout.setHTML(1, 0, "Patent Proposal:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtProposal = new TextArea();
		txtProposal.setPixelSize(400, 150);
		layout.setWidget(1, 1, txtProposal);

		layout.setHTML(2, 0, "Response:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtResponse = new TextArea();
		txtResponse.setPixelSize(400, 150);
		layout.setWidget(2, 1, txtResponse);

		layout.setHTML(3, 0, "");
		layout.setHTML(3, 1, "");

		FlowPanel dockPanel = new FlowPanel();
		layout.setWidget(4, 0, dockPanel);
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		Button btnSubmit = new Button("Submit");
		dockPanel.add(btnSubmit);
		btnSubmit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getInnerItem();
				patentService.submitPatent(m_patent,
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								nanosim.endLoadingFailure();
							}

							@Override
							public void onSuccess(Integer result) {
								if (result < 0) {
									nanosim.endLoadingFailure();
									return;
								}
								// update background
								nanosim.endLoadingSuccess();
								hide();
							}
						});
			}
		});

		Button btnApprove = new Button("Approve");
		dockPanel.add(btnApprove);
		btnApprove.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getInnerItem();
				patentService.approvePatent(m_patent,
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								nanosim.endLoadingFailure();
							}

							@Override
							public void onSuccess(Integer result) {
								if (result < 0) {
									nanosim.endLoadingFailure();
									return;
								}
								// update background
								nanosim.endLoadingSuccess();
								hide();
							}
						});
			}
		});

		Button btnCancel = new Button("Cancel");
		dockPanel.add(btnCancel);
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
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

	public void setInnerItem(Patent item) {
		m_patent = item;

		setText("Patent # " + item.getPatentId());
		uiHelper.setSelectedValue(lstTitles, item.getResearchTypeId());
		txtProposal.setText(item.getProposal());
		txtResponse.setText(item.getResponse());
	}

	public void getInnerItem() {
		m_patent.setGroupId(nanosim.Group.getGroupId());
		m_patent.setClassId(91);

		int index = lstTitles.getSelectedIndex();
		if (index > -1) {
			m_patent.setResearchTypeId(Integer.parseInt(lstTitles
					.getValue(index)));
			m_patent.setResearchTitle(lstTitles.getItemText(index));
		}
		m_patent.setProposal(txtProposal.getText());
		m_patent.setResponse(txtResponse.getText());
	}

	/*
	 * final ListBox dropBox = new ListBox(false); String[] listTypes =
	 * constants.cwListBoxCategories(); for (int i = 0; i < listTypes.length;
	 * i++) { dropBox.addItem(listTypes[i]); }
	 */
	public void setItem(final Patent item) {
		if (researchTitles == null) {
			nanosim.beginLoading();
			ResearchServiceAsync researchService = ResearchService.Util
					.getInstance();
			researchService.getCompletedResearch(nanosim.Group.getGroupId(),
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
								lstTitles.addItem(researchType.getTitle(), ""
										+ researchType.getResearchTypeId());
							}
							setInnerItem(item);
						}

						@Override
						public void onFailure(Throwable caught) {
							nanosim.endLoadingFailure();
						}
					});

		} else
			setInnerItem(item);
	}
}
