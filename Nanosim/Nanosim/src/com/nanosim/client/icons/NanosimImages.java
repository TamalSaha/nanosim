package com.nanosim.client.icons;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.TreeImages;

@SuppressWarnings("deprecation")
public interface NanosimImages extends ImageBundle, TreeImages {
	AbstractImagePrototype catOther();

	AbstractImagePrototype catTables();

	AbstractImagePrototype catMail();

	AbstractImagePrototype catGroup();

	AbstractImagePrototype catProposal();

	AbstractImagePrototype catResearch();

	AbstractImagePrototype catPatent();
	
	AbstractImagePrototype catNews();

	AbstractImagePrototype catBudget();

	AbstractImagePrototype logo();

	AbstractImagePrototype drafts();

	AbstractImagePrototype home();

	AbstractImagePrototype transactions();

	AbstractImagePrototype inbox();

	AbstractImagePrototype sent();

	@Resource("noimage.png")
	AbstractImagePrototype treeLeaf();

	AbstractImagePrototype mail();

	AbstractImagePrototype group();

	AbstractImagePrototype proposal();

	AbstractImagePrototype research();

	AbstractImagePrototype patent();
	
	AbstractImagePrototype news();

	AbstractImagePrototype budget();

	AbstractImagePrototype defaultPhoto();

	AbstractImagePrototype ajaxloader();
}
