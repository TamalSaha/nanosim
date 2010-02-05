package com.nanosim.client.mail;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.nanosim.client.ListContentBase;
import com.nanosim.client.Nanosim;
import com.nanosim.client.UIHelper;
import com.nanosim.client.rpc.GroupService;
import com.nanosim.client.rpc.GroupServiceAsync;
import com.nanosim.client.rpc.MailService;
import com.nanosim.client.rpc.MailServiceAsync;
import com.nanosim.model.Mail;
import com.nanosim.model.Group;

/**
 * A composite for displaying the details of an email message.
 */
public class MailDetail extends DialogBox {

	public enum EditorMode {
		NEW, VIEW
	}

	private ScrollPanel scroller;
	private List<Group> groupNames;
	private Nanosim nanosim;
	private ListBox lstTitles;
	private TextBox txtSubject;
	private TextArea txtMessage;

	private ListContentBase m_ListView;
	private Mail m_mail;

	private final MailServiceAsync mailService = MailService.Util.getInstance();
	private Button btnSend;
	private Button btnCancel;
	private Button btnOk;
	private FlowPanel dockPanel;
	private UIHelper uiHelper = new UIHelper();

	public MailDetail(ListContentBase listView) {
		nanosim = Nanosim.getInstance();
		m_ListView = listView;

		final FlexTable layout = new FlexTable();
		setWidth("500px");
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Group:");
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		lstTitles = new ListBox(false);
		lstTitles.setWidth("400px");
		layout.setWidget(0, 1, lstTitles);

		layout.setHTML(1, 0, "Subject:");
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtSubject = new TextBox();
		txtSubject.setWidth("400px");
		layout.setWidget(1, 1, txtSubject);

		layout.setHTML(2, 0, "Message:");
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		txtMessage = new TextArea();
		txtMessage.setPixelSize(400, 150);
		layout.setWidget(2, 1, txtMessage);

		layout.setHTML(3, 0, "");
		layout.setHTML(3, 1, "");

		dockPanel = new FlowPanel();
		layout.setWidget(4, 0, dockPanel);
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnSend = new Button("Send");
		// dockPanel.add(btnSend);
		btnSend.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nanosim.beginLoading();
				getInnerItem();
				mailService.sendMail(m_mail, new AsyncCallback<Integer>() {

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

		btnCancel = new Button("Cancel");
		dockPanel.add(btnCancel);
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				dockPanel.clear();
			}
		});
		
		btnOk = new Button("Ok");
		dockPanel.add(btnOk);
		btnOk.addClickHandler(new ClickHandler() {

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
		m_mail.setFromGroup(nanosim.Group.getGroupId());

		int index = lstTitles.getSelectedIndex();
		if (index > -1) {
			m_mail.setToGroup(Integer.parseInt(lstTitles.getValue(index)));
			m_mail.setToGroupName(lstTitles.getItemText(index));
		}
		m_mail.setSubject(txtSubject.getText());
		m_mail.setMessage(txtMessage.getText());
	}

	public void setItem(final EditorMode mode, final Mail item) {
		if (groupNames == null) {
			nanosim.beginLoading();
			GroupServiceAsync groupService = GroupService.Util.getInstance();

			groupService.getGroups(new AsyncCallback<List<Group>>() {

				@Override
				public void onSuccess(List<Group> result) {
					if (result == null || result.size() == 0) {
						nanosim.endLoadingFailure();
						return;
					}
					nanosim.endLoadingSuccess();
					// load data
					groupNames = result;
					for (Group group : result) {
						lstTitles.addItem(group.getName(), ""
								+ group.getGroupId());
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

	public void setInnerItem(EditorMode mode, Mail item) {
		
		m_mail = item;
	
		switch (mode) {
		case NEW:
			dockPanel.clear();
			dockPanel.add(btnSend);
			dockPanel.add(btnCancel);

			setText("Mail*");
			m_mail = new Mail();
			m_mail.setUnread("y");
			lstTitles.setItemSelected(0, true);
			lstTitles.setEnabled(true);
			txtSubject.setText("");
			txtMessage.setText("");
			break;
		case VIEW:
			dockPanel.clear();
			dockPanel.add(btnOk);

			setText("Mail # " + item.getMailId());
			m_mail.setUnread("n");
			uiHelper.setSelectedValue(lstTitles, item.getFromGroup());
			lstTitles.setEnabled(false);
			txtSubject.setText(item.getSubject());
			txtMessage.setText(item.getMessage());
			break;
		default:
			break;
		}
	}
}