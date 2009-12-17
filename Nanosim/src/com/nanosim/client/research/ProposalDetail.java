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
import com.nanosim.client.rpc.RiskCertificateService;
import com.nanosim.client.rpc.RiskCertificateServiceAsync;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.nanosim.model.RiskCertificate;

/**
 * A composite for displaying the details of an email message.
 */
public class ProposalDetail extends DialogBox {

	public enum EditorMode {
		NEW
	}

	private ScrollPanel scroller;
	private List<ResearchType> possibleResearch;
	private List<RiskCertificate> riskCertificates;
	private Nanosim nanosim;
	private ListBox lstTopics;
	private TextArea txtProposal;
	private TextArea txtSources;
	private ListBox lstRisks;

	private ContentListBase m_ListView;
	private Research m_proposal;
	private EditorMode m_mode;
	private UIHelper uiHelper = new UIHelper();

	private final ResearchServiceAsync researchService = ResearchService.Util
			.getInstance();
	private final RiskCertificateServiceAsync riskCertificateService = RiskCertificateService.Util
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
		txtProposal.setPixelSize(400, 200);
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
		lstRisks = new ListBox(false);
		lstRisks.setWidth("400px");
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
				researchService.submitResearchProposal(m_proposal,
						new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								nanosim.endLoadingFailure();
							}

							@Override
							public void onSuccess(String result) {
								if (!result.equals("")) {
									nanosim.endLoading(result);
									hide();
									return;
								}
								m_ListView.update();
								nanosim.endLoadingSuccess();
								hide();
							}
						});
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
		m_proposal.setGroupId(nanosim.Group.getGroupId());

		int index = lstTopics.getSelectedIndex();
		if (index > -1) {
			m_proposal.setResearchTypeId(Integer.parseInt(lstTopics
					.getValue(index)));
			m_proposal.setResearchTypeTitle(lstTopics.getItemText(index));
		}
		m_proposal.setGroupId(nanosim.Group.getGroupId());
		m_proposal.setResearchProposal(txtProposal.getText());
		m_proposal.setResearchSources(txtSources.getText());
	}

	public void setItem(final EditorMode mode, final Research item) {
		nanosim.beginLoading();
		researchService.getPossibleResearch(nanosim.Group.getGroupId(),
				new AsyncCallback<List<ResearchType>>() {

					@Override
					public void onFailure(Throwable caught) {
						nanosim.endLoadingFailure();
					}

					@Override
					public void onSuccess(List<ResearchType> result1) {
						if (result1 == null || result1.size() == 0) {
							nanosim.endLoadingFailure();
							return;
						}
						// load data
						possibleResearch = result1;
						for (ResearchType rt : result1) {
							StringBuilder sb = new StringBuilder();
							sb.append(rt.getTitle());
							sb.append(" - $");
							sb.append(rt.getCost());
							lstTopics.addItem(sb.toString(), ""
									+ rt.getResearchTypeId());
						}
						if (riskCertificates == null) {
							riskCertificateService
									.getRiskReductionOptions(new AsyncCallback<List<RiskCertificate>>() {

										@Override
										public void onSuccess(
												List<RiskCertificate> result2) {
											if (result2 == null
													|| result2.size() == 0) {
												nanosim.endLoadingFailure();
												return;
											}
											// load data
											riskCertificates = result2;
											lstRisks.addItem(
													"No Risk Mitigation", "0");
											for (RiskCertificate riskCertificate : result2) {
												StringBuilder sb = new StringBuilder();
												sb.append(riskCertificate
														.getGroupName());
												sb.append(" - ");
												sb.append(riskCertificate
														.getTitle());
												sb.append(" - ");
												sb.append(riskCertificate
														.getCost());
												lstRisks
														.addItem(
																sb.toString(),
																""
																		+ riskCertificate
																				.getCertificateId());
											}
											setInnerItem(mode, item);
											nanosim.endLoadingSuccess();
										}

										@Override
										public void onFailure(Throwable caught) {
											nanosim.endLoadingFailure();
										}
									});
						} else {
							setInnerItem(mode, item);
							nanosim.endLoadingSuccess();
						}
					}
				});
	}

	public void setInnerItem(EditorMode mode, Research item) {
		m_proposal = item;
		m_mode = mode;

		switch (mode) {
		case NEW:
			dockPanel.clear();
			dockPanel.add(btnSubmit);
			dockPanel.add(btnCancel);

			setText("Proposal*");
			m_proposal = new Research();
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
