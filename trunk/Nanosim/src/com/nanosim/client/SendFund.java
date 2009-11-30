package com.nanosim.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.ImageBundle.Resource;
import com.nanosim.client.LeftPanel.Images;
import com.nanosim.client.event.ILoginHandler;
import com.nanosim.client.internal.EventHandlerCollection;
import com.nanosim.client.rpc.SigninService;
import com.nanosim.client.rpc.SigninServiceAsync;
import com.nanosim.client.rpc.TransferService;
import com.nanosim.client.rpc.TransferServiceAsync;
import com.nanosim.model.Budget;
import com.nanosim.model.Person;

/**
 * Composite that represents a collection of <code>Task</code> items.
 */
public class SendFund extends Composite {
	private static SendFund singleton;
	private SendFundScreen sendFundScreen;
	String width = new String("70%");
	
	/*private final TransferServiceAsync transferService = TransferService.Util
			.getInstance();*/
	

	/*public void insertDatabase(Double credit) {
		Date now = new Date();
		System.out.println(now.getTime());
		Budget b[] = new Budget[1];
		b[0] = new Budget(now, credit);

		transferService.insertBudget(b[0], new AsyncCallback<Budget>() {
			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Fail");
			}

			@Override
			public void onSuccess(Budget result) {
				System.out.println("Success!");
				// do stuff on success with GUI, like load the next GUI element
			}
		});
	}*/
	
	//----------------------------
	private EventHandlerCollection<ILoginHandler> loginHandlerColl = new EventHandlerCollection<ILoginHandler>();

	private final SigninServiceAsync loginService = SigninService.Util
			.getInstance();
	/**
	 * Specifies the images that will be bundled for this Composite and specify
	 * that tree's images should also be included in the same bundle.
	 */
	public interface Images extends ImageBundle, TreeImages {
		AbstractImagePrototype drafts();

		AbstractImagePrototype home();
		
		AbstractImagePrototype transactions();

		AbstractImagePrototype inbox();

		AbstractImagePrototype sent();

		@Resource("noimage.png")
		AbstractImagePrototype treeLeaf();
	}

	private Tree tree;

	public SendFund(Images images) {
		/*SimplePanel panel = new SimplePanel();
		VerticalPanel list = new VerticalPanel();
		panel.setWidget(list);
		list.add(new CheckBox("See the status"));
		list.add(new CheckBox("Send Fund"));
		initWidget(panel);
		setStyleName("nanosim-Research");*/
		//
		singleton = this;
		tree = new Tree(images);
		TreeItem root = new TreeItem(imageItemHTML(images.transactions(),
				"Transactions"));
		tree.addItem(root);

		addImageItem(root, "Send Fund", images.sent());
		addImageItem(root, "View history", images.drafts());
		//addImageItem(root, "Sent", images.sent());

		// MailList uses Mail.get() in its constructor, so initialize it after
		// 'singleton'.
		sendFundScreen = new SendFundScreen();
		//
		// // Create the right panel, containing the email list & details.
//		System.out.println(LeftPanel.whichItemChosen() +" here!");

		root.setState(true);
		try {
			initWidget(tree);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public void loadRightPanel(){
		RightPanel.clear();
		RightPanel.add(sendFundScreen);
		//RightPanel.add(mailDetail);
		sendFundScreen.setWidth(width);
		//mailDetail.setWidth(width);
		
	}
	
	/**
	 * A helper method to simplify adding tree items that have attached images.
	 * {@link #addImageItem(TreeItem, String, AbstractImagePrototype) code}
	 * 
	 * @param root the tree item to which the new item will be added.
	 * @param title the text associated with this item.
	 */
	private TreeItem addImageItem(TreeItem root, String title,
			AbstractImagePrototype imageProto) {
		TreeItem item = new TreeItem(imageItemHTML(imageProto, title));
		root.addItem(item);
		return item;
	}

	/**
	 * Generates HTML for a tree item with an attached icon.
	 * 
	 * @param imageProto the image prototype to use
	 * @param title the title of the item
	 * @return the resultant HTML
	 */
	private String imageItemHTML(AbstractImagePrototype imageProto, String title) {
		return imageProto.getHTML() + " " + title;
	}
}