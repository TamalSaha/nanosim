package com.nanosim.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.client.mail.Mailboxes;
import com.nanosim.client.patent.PatentHome;
import com.nanosim.client.profile.ProfileHome;
import com.nanosim.client.research.ResearchHome;
import com.nanosim.client.transfer.SendFundHome;

/**
 * A composite that contains the shortcut stack panel on the left side.
 * {@link com.google.gwt.user.client.ui.StackPanel},
 * {@link com.google.gwt.user.client.ui.Tree}, and other custom widgets.
 */
public class LeftPanel extends Composite {
	private StackPanel stackPanel = new StackPanel();
	private int stackIndex = -1;

	private Mailboxes mailboxes;
	private ResearchHome research;
	private PatentHome patent;
	private ProfileHome profile;
	private SendFundHome sendFund;

	public LeftPanel() {
		stackPanel.setWidth("225px");
		initWidget(stackPanel);
	}

	public void loadShortcuts(RightPanel rightPanel) {
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		mailboxes = new Mailboxes();
		mailboxes.loadShortcuts(rightPanel);
		add(mailboxes, images.mail(), "Mail");

		if (nanosim.GroupType.getHasResearch()) {
			research = new ResearchHome();
			research.loadShortcuts(rightPanel);
			add(research, images.group(), "Research");
		}

		if (nanosim.GroupType.getHasPatents()) {
			patent = new PatentHome();
			patent.loadShortcuts(rightPanel);
			add(patent, images.group(), "Patents");
		}

		if (nanosim.GroupType.getHasProfile()) {
			profile = new ProfileHome();
			profile.loadShortcuts(rightPanel);
			add(profile, images.group(), "Profile");
		}

		sendFund = new SendFundHome();
		sendFund.loadShortcuts(rightPanel);
		add(sendFund, images.group(), "Transactions");

		stackPanel.showStack(stackIndex = 0);
		mailboxes.loadRightPanel();
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONCLICK) {
			int index = stackPanel.getSelectedIndex();
			if (index >= 0 && stackIndex != index) {
				stackIndex = index;
				switch (index) {
				case 0:
					mailboxes.loadRightPanel();
					break;
				case 1:
					research.loadRightPanel();
					break;
				case 2:
					patent.loadRightPanel();
					break;
				case 3:
					profile.loadRightPanel();
					break;
				case 4:
					sendFund.loadRightPanel();
					break;
				default:
					break;
				}
			}
		}
	}

	private void add(Widget widget, AbstractImagePrototype imageProto,
			String caption) {
		stackPanel.add(widget, createHeaderHTML(imageProto, caption), true);
	}

	private String createHeaderHTML(AbstractImagePrototype imageProto,
			String caption) {
		String captionHTML = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ imageProto.getHTML()
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>&nbsp;"
				+ caption + "</b></td></tr></table>";
		return captionHTML;
	}
}