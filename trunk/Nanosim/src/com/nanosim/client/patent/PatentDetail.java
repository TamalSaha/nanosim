package com.nanosim.client.patent;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.UIHelper;
import com.nanosim.client.rpc.PatentService;
import com.nanosim.client.rpc.PatentServiceAsync;
import com.nanosim.client.rpc.ResearchService;
import com.nanosim.client.rpc.ResearchServiceAsync;
import com.nanosim.model.Patent;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;

/**
 * A composite for displaying the details of an email message.
 */
public class PatentDetail extends DialogBox {
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
		layout.setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Title:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		lstTitles = new ListBox(false);
		layout.setWidget(0, 1, lstTitles);
		cellFormatter.setColSpan(0, 1, 2);

		layout.setHTML(1, 0, "Patent Proposal:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		txtProposal = new TextArea();
		layout.setWidget(1, 1, txtProposal);
		cellFormatter.setColSpan(1, 1, 2);

		layout.setHTML(2, 0, "Response:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		txtResponse = new TextArea();
		layout.setWidget(2, 1, txtResponse);
		cellFormatter.setColSpan(2, 1, 2);

		layout.setHTML(3, 0, "");
		layout.setHTML(3, 1, "");
		layout.setHTML(3, 2, "");

		Button btnSubmit = new Button("Submit");
		layout.setWidget(4, 0, btnSubmit);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		btnSubmit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});

		Button btnApprove = new Button("Approve");
		layout.setWidget(4, 1, btnApprove);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		Button btnCancel = new Button("Cancel");
		layout.setWidget(4, 2, btnCancel);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
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

		uiHelper.setSelectedValue(lstTitles, item.getResearchTypeId());
		txtProposal.setText(item.getProposal());
		txtResponse.setText(item.getResponse());
	}

	public void getInnerItem() {
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
								lstTitles.addItem(researchType.getTitle(),
										new StringBuilder(researchType
												.getResearchTypeId())
												.toString());
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
