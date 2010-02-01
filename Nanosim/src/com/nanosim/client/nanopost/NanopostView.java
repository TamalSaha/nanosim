package com.nanosim.client.nanopost;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.NanopostService;
import com.nanosim.client.rpc.NanopostServiceAsync;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

public class NanopostView extends Composite implements ClickHandler {
	private final NanopostServiceAsync nanopostService = NanopostService.Util
		.getInstance();
	private Nanosim nanosim;
//	private List<Nanopost> nanopostData;
	private List<NanopostComment> commentsData;
//	private int selectedRow;
	private HashMap <Integer, List<NanopostComment>> commentHash = new HashMap<Integer, List<NanopostComment>>();
	private FlexTable table = new FlexTable();
	private InlineHTML readComments = new InlineHTML("<a href='javascript:;'>Read Comments</a>");
	private InlineHTML writeComment = new InlineHTML("<a href='javascript:;'>Leave Comment</a>");
	private FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
	
	public NanopostView() {
		nanosim = Nanosim.getInstance();
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");

		FlowPanel wrapper1 = new FlowPanel();
		wrapper1.setStylePrimaryName("gwt-StackPanel");

		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setHeight("30px");
		toolbar.setWidth("100%");
		toolbar.setStylePrimaryName("gwt-StackPanelItem");
		wrapper1.add(toolbar);
		vPanel.add(wrapper1);

		HTML title = new HTML("Nanopost", true);
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolbar.add(title);	
		
		FlowPanel wrapper2 = new FlowPanel();
		wrapper2.setStylePrimaryName("gwt-StackPanelItem");

		// Setup the table
		table.addStyleName("newsTable");
		table.setCellSpacing(0);
		table.setCellPadding(2);
		table.setWidth("100%");
		wrapper2.add(table);
		vPanel.add(wrapper2);

		// Create a table to layout the form options
		table.setWidth("100%");
		table.setCellSpacing(2);

		// Add a title to the form
		table.setText(0, 0, "Latest News");
		//table.getFlexCellFormatter().setColSpan(0, 0, 4);
		table.getRowFormatter().setStyleName(0, "mail-ListHeader");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		//table.setWidget(1, 0, );
		// Hook up events.
		readComments.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Object sender = event.getSource();
				if (sender == readComments) {
					System.out.println("Entro read");
				} else if (sender == writeComment) {
				} else if (sender == table) {
				}
			}
		});
		
		writeComment.addClickHandler(this);
		
		initWidget(vPanel);
	}
	
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == readComments) {
			System.out.println("Entro");
		} else if (sender == writeComment) {
			System.out.println("Entro write");
		} else if (sender == table) {
		}
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
		update();
	}
	
	public void update() {
		nanopostService.getNews(new AsyncCallback<List<Nanopost>>() {

			@Override
			public void onSuccess(List<Nanopost> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				
//				nanopostData = result;
				
				int length = result.size();

				int i = 0;
				for (; i < length; ++i) {
					int j = i * 4 + 1;
					Nanopost item = result.get(i);
					table.setHTML(j, 0, "<br><br><h4>" +item.getPostDate() + "   " + item.getSubject() + "</h4>");
					table.getRowFormatter().setStyleName(j, "news-ListHeader");
					cellFormatter.setHorizontalAlignment(j, 0, HasHorizontalAlignment.ALIGN_LEFT);
					table.setHTML(j + 1, 0, "" + item.getBody());
					table.setHTML(j + 2, 0, "Posted by: " + "<i>" + item.getWriter() + "</i>");
					table.setHTML(j + 3, 0,	"" + writeComment );
					cellFormatter.setHorizontalAlignment(j + 2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					cellFormatter.setHorizontalAlignment(j + 3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					table.getRowFormatter().setStyleName(i + 1, "newsTable");
					getComments(item.getNanopostId(), j);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();
			}			
		});
	}
	
	public void getComments (final int nanopostId, final int j){
		nanopostService.getComments(nanopostId, new AsyncCallback<List<NanopostComment>>() {
					
			@Override
			public void onSuccess(List<NanopostComment> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();
				
				commentsData = result;
				
				commentHash.put(nanopostId, commentsData);
				
				if (!(result.size()==0)){
					readComments.setHTML("<a href='javascript:;'>Read Comments" + " (" + result.size() + ")</a>");
					table.setHTML(j + 3, 0, "" + readComments + "   " + writeComment);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();				
			}
		});
	}
}
