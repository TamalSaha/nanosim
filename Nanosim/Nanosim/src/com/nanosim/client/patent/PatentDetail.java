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
import com.nanosim.client.ListContentBase;
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

	public enum EditorMode {
		NEW, APPROVE, VIEW
	}

	private ScrollPanel scroller;
	private List<ResearchType> researchTitles;
	private Nanosim nanosim;
	private ListBox lstTitles;
	private TextArea txtProposal;
	private TextArea txtResponse;

	private ListContentBase m_ListView;
	private Patent m_patent;
	private UIHelper uiHelper = new UIHelper();

	private final PatentServiceAsync patentService = PatentService.Util
			.getInstance();
	private Button btnSubmit;
	private Button btnApprove;
	private Button btnCancel;
	private FlowPanel dockPanel;
	final FlexTable layout = new FlexTable();
	
	public PatentDetail(ListContentBase listView) {
		nanosim = Nanosim.getInstance();
		m_ListView = listView;
		
		setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "");
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		
		layout.setHTML(1, 0, "Title:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		lstTitles = new ListBox(false);
		lstTitles.setWidth("400px");
		layout.setWidget(1, 1, lstTitles);


		layout.setHTML(2, 0, "Patent Proposal:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtProposal = new TextArea();
		txtProposal.setPixelSize(400, 150);
		layout.setWidget(2, 1, txtProposal);

		layout.setHTML(3, 0, "Response:");
		cellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtResponse = new TextArea();
		txtResponse.setPixelSize(400, 150);
		layout.setWidget(3, 1, txtResponse);

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
								m_ListView.update();
								nanosim.endLoadingSuccess();
								hide();
							}
						});
			}
		});

		btnApprove = new Button("Approve");
		// dockPanel.add(btnApprove);
		btnApprove.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getInnerItem();
				m_patent.setApproved("y");
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

	public void setItem(final EditorMode mode, final Patent item) {
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
								lstTitles.addItem(researchType.getTitle(), ""
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

	public void setInnerItem(EditorMode mode, Patent item) {
		m_patent = item;
		txtResponse.setVisible(true);
		
		switch (mode) {
		case NEW:
			layout.setHTML(0, 0, "Fill out the patent form below. Note that there is a fee for applying for a patent, regardless of whether the patent is granted. Make sure you are patenting an actual use for this technology. Include sources in your proposal.");
			dockPanel.clear();			
			dockPanel.add(btnSubmit);
			dockPanel.add(btnCancel);
			setText("Patent*");
			m_patent = new Patent();
			m_patent.setApproved("n");
			lstTitles.setItemSelected(0, true);
			txtProposal.setText("");
			txtResponse.setVisible(false);
			break;
		case VIEW:
			dockPanel.clear();
			dockPanel.add(btnCancel);
			setText("Patent # " + item.getPatentId());
			uiHelper.setSelectedValue(lstTitles, item.getResearchTypeId());
			txtProposal.setText(item.getProposal());
			txtResponse.setText(item.getResponse());
			txtResponse.setReadOnly(true);
			break;
		case APPROVE:
			dockPanel.clear();
			dockPanel.add(btnApprove);
			dockPanel.add(btnCancel);
			setText("Patent # " + item.getPatentId());
			uiHelper.setSelectedValue(lstTitles, item.getResearchTypeId());
			txtProposal.setText(item.getProposal());
			txtResponse.setReadOnly(false);
			txtResponse.setText(item.getResponse());
			break;
		default:
			break;
		}
	}
}
