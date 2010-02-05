package com.nanosim.client.nanopost;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.Nanosim;
import com.nanosim.client.rpc.NanopostService;
import com.nanosim.client.rpc.NanopostServiceAsync;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

/**
 * A composite for displaying the comments of an article in Nanopost.
 */
public class NanopostDetail extends DialogBox {

	//private List<NanopostComment> commentsData;
	//private HashMap <Integer, List<NanopostComment>> commentHash = new HashMap<Integer, List<NanopostComment>>();
	private HashMap <Integer, String> commentHash = new HashMap<Integer, String>();

	public enum EditorMode {
		NEW, VIEW, EDIT
	}
	private EditorMode in_mode;
	private ScrollPanel scroller = new ScrollPanel();
	private Nanosim nanosim;

	private ListContentBase m_ListView;
	private NanopostComment n_comment;
	private Nanopost n_nanopost;

	private final NanopostServiceAsync nanopostService = NanopostService.Util.getInstance();
	private Button btnSaveComment;
	private Button btnCancel;
	private Button btnSaveArticle;
	private Button btnDeleteArticle;
	private Button btnEditArticle;
	private FlowPanel dockPanel;
	private String comments = "";
	final FlexTable layout = new FlexTable();
	private int id = 0;
	private TextBox txtSubject = new TextBox();
	private TextArea txtArticle = new TextArea();
	private TextArea txtComment = new TextArea();

	public NanopostDetail(ListContentBase listView) {
		nanosim = Nanosim.getInstance();
		m_ListView = listView;

		setWidth("750px");
		setHeight("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Title:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_TOP);
		layout.setHTML(0, 1, "");
		txtSubject.setWidth("600px");
		layout.setWidget(0, 1, txtSubject);

		layout.setHTML(1, 0, "Article:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		layout.setHTML(1, 1, "");
		txtArticle.setPixelSize(600, 350);
		layout.setWidget(1, 1, txtArticle);


		layout.setHTML(2, 0, "");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(2, 0,
				HasVerticalAlignment.ALIGN_TOP);
		layout.setHTML(2, 1, "");

		layout.setHTML(3, 0, "");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(2, 0,
				HasVerticalAlignment.ALIGN_TOP);
		txtComment.setPixelSize(600, 50);
		layout.setWidget(3, 1, txtComment);

		dockPanel = new FlowPanel();
		layout.setWidget(4, 0, dockPanel);
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnSaveComment = new Button("Save");
		btnSaveComment.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				if(!(id == 0)){
					getCommentItem();
				}
				nanopostService.sendComment(n_comment, new AsyncCallback<Integer>() {

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

		btnSaveArticle = new Button("Save");
		dockPanel.add(btnSaveArticle);
		btnSaveArticle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getPostItem();

				nanopostService.sendPost(n_nanopost, new AsyncCallback<Integer>() {

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

		btnDeleteArticle = new Button("Delete");
		dockPanel.add(btnDeleteArticle);
		btnDeleteArticle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getIdItem();

				nanopostService.deletePost(n_nanopost, new AsyncCallback<Integer>() {

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

		btnEditArticle = new Button("Save");
		dockPanel.add(btnEditArticle);
		btnEditArticle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getPostItem();
				n_nanopost.setNanopostId(id);

				nanopostService.editPost(n_nanopost, new AsyncCallback<Integer>() {

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

		btnCancel = new Button("Close");
		dockPanel.add(btnCancel);
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				dockPanel.clear();
			}
		});

		scroller = new ScrollPanel(layout);
		scroller.setSize("750px", "500px");
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

	public void getCommentItem() {
		n_comment = new NanopostComment();
		n_comment.setNanopostId(id);
		n_comment.setWriter(nanosim.Person.getName());
		n_comment.setComment(txtComment.getText());
	}

	public void getPostItem() {
		n_nanopost = new Nanopost();
		n_nanopost.setClassId(91);
		n_nanopost.setWriter(nanosim.Person.getName());
		n_nanopost.setSubject(txtSubject.getText());
		n_nanopost.setBody(txtArticle.getText());
	}

	public void getIdItem() {
		n_nanopost = new Nanopost();
		n_nanopost.setNanopostId(id);
	}

	public void setItem(final EditorMode mode, final Nanopost item) {
		in_mode = mode;
		switch (in_mode) {
		case NEW:
			setText("New Article*");
			dockPanel.clear();
			layout.clear();
			txtSubject.setText("");
			txtArticle.setText("");
			layout.setWidget(0, 1, txtSubject);
			layout.setWidget(1, 1, txtArticle);
			layout.setHTML(2, 0, "");layout.setHTML(2, 1, "");
			layout.setHTML(3, 0, "");layout.setHTML(3, 1, "");
			layout.setWidget(4, 0, dockPanel);			
			dockPanel.add(btnSaveArticle);
			dockPanel.add(btnCancel);
			break;
		case VIEW:
			setText("NanopostId " + item.getNanopostId() + " " + item.getPostDate());
			dockPanel.clear();
			layout.clear();
			layout.setHTML(2, 0, "<br>Comments:");
			layout.setHTML(3, 0, "Write your Comment:");
			layout.setWidget(3, 1, txtComment);
			layout.setWidget(4, 0, dockPanel);
			txtComment.setText("");
			dockPanel.add(btnSaveComment);
			dockPanel.add(btnCancel);
			id = item.getNanopostId();
			layout.setHTML(0, 1, "<b>" + item.getSubject() + "</b>");
			layout.setHTML(1, 1, "" + item.getBody() + "<br>"
					+ "Posted by: " + "<i>" + item.getWriter() + "</i><br>");
			layout.setHTML(2, 1, " " + commentHash.get(item.getNanopostId()));
			break;
		case EDIT:
			setText("NanopostId " + item.getNanopostId() + " " + item.getPostDate());
			dockPanel.clear();
			layout.clear();
			layout.setWidget(0, 1, txtSubject);
			layout.setWidget(1, 1, txtArticle);
			layout.setHTML(2, 0, "<br>Comments:");
			//layout.setHTML(3, 0, "Write your Comment:");
			//layout.setWidget(3, 1, txtComment);
			layout.setWidget(4, 0, dockPanel);
			txtComment.setText("");
			dockPanel.add(btnEditArticle);
			dockPanel.add(btnDeleteArticle);
			dockPanel.add(btnCancel);
			id = item.getNanopostId();
			txtSubject.setText(item.getSubject());
			//layout.setHTML(0, 1, "<b>" + item.getSubject() + "</b>");
			txtArticle.setText(item.getBody());
			//layout.setHTML(1, 1, "" + item.getBody() + "<br>"
			//		+ "Posted by: " + "<i>" + item.getWriter() + "</i><br>");
			layout.setHTML(2, 1, " " + commentHash.get(item.getNanopostId()));
			break;
		default:
			break;
		}
	}

	public void getComments (final int nanopostId){
		nanopostService.getComments(nanopostId, new AsyncCallback<List<NanopostComment>>() {

			@Override
			public void onSuccess(List<NanopostComment> result) {
				if (result == null) {
					nanosim.endLoadingFailure();
					return;
				}
				nanosim.endLoadingSuccess();

				//commentHash.put(nanopostId, commentsData);

				int length = result.size();
				comments = "";

				for (int i = 0; i < length; ++i){
					NanopostComment item = result.get(i);
					comments = comments
					+ "<br><b>" + item.getPostDate() + "</b>  "
					+ "Posted by: " + "<i>" + item.getWriter() + "</i><br>"
					+ item.getComment() + "<br>";	
				}
				commentHash.put(nanopostId, comments);				
			}

			@Override
			public void onFailure(Throwable caught) {
				nanosim.endLoadingFailure();				
			}
		});
	}
}