package com.nanosim.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.Widget;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.model.GroupType;

/**
 * A composite that contains the shortcut stack panel on the left side. The
 * mailbox tree and shortcut lists don't actually do anything, but serve to show
 * how you can construct an interface using
 * {@link com.google.gwt.user.client.ui.StackPanel},
 * {@link com.google.gwt.user.client.ui.Tree}, and other custom widgets.
 */
public class LeftPanel extends Composite {
	private int nextHeaderIndex = 0;
	private static DecoratedStackPanel stackPanel = new DecoratedStackPanel();
	private SendFund sendFund;
	private Mailboxes mailboxes;

	/**
	 * Constructs a new shortcuts widget using the specified images.
	 * 
	 * @param images
	 *            a bundle that provides the images for this widget
	 */
	public LeftPanel(GroupType groupType) {
		// Create the groups within the stack panel.
		NanosimImages images = Nanosim.images;
		mailboxes = new Mailboxes();
		add(mailboxes, images.mail(), "Mail");
		add(new Contacts(), images.group(), "Group Contacts");
		add(new Proposals(), images.proposal(), "Proposals");
		add(new Research(), images.research(), "Research");
		if (groupType.getHasPatents())
			add(new Patents(), images.patent(), "Patents");
		sendFund = new SendFund();
		add(sendFund, images.transfer(), "Transfers");

		initWidget(stackPanel);
		// stackPanel.
	}

	@Override
	protected void onLoad() {
		// Show the mailboxes group by default.
		stackPanel.showStack(0);
		mailboxes.loadRightPanel();
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (DOM.eventGetType(event) == Event.ONCLICK) {
			Element target = DOM.eventGetTarget(event);
			int index = findDividerIndex(target);
			if (index != -1) {
				stackPanel.showStack(index);

			}
		}
		super.onBrowserEvent(event);
		if (stackPanel.getSelectedIndex() == 5)
			sendFund.loadRightPanel();
		else if (stackPanel.getSelectedIndex() == 0)
			mailboxes.loadRightPanel();

	}

	private int findDividerIndex(Element elem) {
		while (elem != getElement()) {
			String expando = DOM.getElementProperty(elem, "__index");
			if (expando != null) {
				// Make sure it belongs to me!
				int ownerHash = DOM.getElementPropertyInt(elem, "__owner");
				if (ownerHash == hashCode()) {
					// Yes, it's mine.
					return Integer.parseInt(expando);
				} else {
					// It must belong to some nested StackPanel.
					return -1;
				}
			}
			elem = DOM.getParent(elem);
		}
		return -1;
	}

	public static int whichItemChosen() {
		return stackPanel.getSelectedIndex();
	}

	private void add(Widget widget, AbstractImagePrototype imageProto,
			String caption) {
		widget.addStyleName("mail-StackContent");
		stackPanel.add(widget, createHeaderHTML(imageProto, caption), true);
	}

	/**
	 * Creates an HTML fragment that places an image & caption together, for use
	 * in a group header.
	 * 
	 * @param imageProto
	 *            an image prototype for an image
	 * @param caption
	 *            the group caption
	 * @return the header HTML fragment
	 */
	private String createHeaderHTML(AbstractImagePrototype imageProto,
			String caption) {
		nextHeaderIndex++;

		String captionHTML = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ imageProto.getHTML()
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>"
				+ caption + "</b></td></tr></table>";
		return captionHTML;
	}

}