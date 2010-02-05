package com.nanosim.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import com.nanosim.client.budget.BudgetHome;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.client.mail.MailHome;
import com.nanosim.client.nanopost.NanopostHome;
import com.nanosim.client.patent.PatentHome;
import com.nanosim.client.profile.ProfileHome;
import com.nanosim.client.research.ResearchHome;

/**
 * A composite that contains the shortcut stack panel on the left side.
 * {@link com.google.gwt.user.client.ui.StackPanel},
 * {@link com.google.gwt.user.client.ui.Tree}, and other custom widgets.
 */
public class LeftPanel extends Composite {
	private StackPanel stackPanel = new StackPanel();
	private int stackIndex = -1;

	private ProfileHome profile;
	private MailHome mail;
	private ResearchHome research;
	private PatentHome patent;
	private BudgetHome budget;	
	private NanopostHome news;


	public LeftPanel() {
		stackPanel.setWidth("180px");
		stackPanel.setHeight("500px");
		initWidget(stackPanel);
	}

	public void loadShortcuts(CenterPanel rightPanel) {
		Nanosim nanosim = Nanosim.getInstance();
		NanosimImages images = Nanosim.images;

		profile = new ProfileHome();
		profile.loadShortcuts(rightPanel);
		add(profile, images.group(), "Profile");
		
		mail = new MailHome();
		mail.loadShortcuts(rightPanel);
		add(mail, images.mail(), "Mail");
		
		if (nanosim.GroupType.getHasResearch()) {
			research = new ResearchHome();
			research.loadShortcuts(rightPanel);
			add(research, images.research(), "Research");
		}

		if (nanosim.GroupType.getHasPatents()) {
			patent = new PatentHome();
			patent.loadShortcuts(rightPanel);
			add(patent, images.patent(), "Patents");
		}

		if (!nanosim.GroupType.getNewspaper()) {
			budget = new BudgetHome();
			budget.loadShortcuts(rightPanel);
			add(budget, images.budget(), "Budget");
		}
		
		//if (nanosim.GroupType.getNewspaper()) {
			news = new NanopostHome();
			news.loadShortcuts(rightPanel);
			add(news, images.news(), "News");
		//}
		
		stackPanel.showStack(stackIndex = 0);
		profile.loadCenterPanel();
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		Nanosim nanosim = Nanosim.getInstance();
		if (DOM.eventGetType(event) == Event.ONCLICK) {
			int index = stackPanel.getSelectedIndex();
			if (index >= 0 && stackIndex != index) {
				stackIndex = index;
				switch (index) {
				case 0:
					profile.loadCenterPanel();
					break;
				case 1:
					mail.loadCenterPanel();
					break;
				case 2:
					research.loadCenterPanel();
					break;
				case 3:
					patent.loadCenterPanel();
					break;
				case 4:
					if(!nanosim.GroupType.getNewspaper())
						budget.loadCenterPanel();	
					else
						news.loadCenterPanel();
					break;
				case 5:
					if(nanosim.GroupType.getNewspaper())
						break;
					else
						news.loadCenterPanel();
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