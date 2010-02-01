package com.nanosim.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * About dialog box.
 */
public class AboutDialog extends DialogBox {

	public AboutDialog() {
		
	    setAnimationEnabled(true);
	    //setGlassEnabled(true);
	    
		VerticalPanel outer = new VerticalPanel();
		
		setText("About NanoSim");
		HTML text = new HTML("NanoSim is a Role play simularion of the NNI <br /> "
				+ "developed by: Yina Arenas <br />" 
				+ "For the use in STS2500 Societal Dimensions of Nanotechnology <br />" 
				+ "Prof. Michael Gorman, Prof. Nathan Swami<br />" 
				+ "2009");
		text.setStyleName("mail-DetailBody");
		
		outer.add(Nanosim.images.logo().createImage());
		outer.add(text);
		outer.add(new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		}));

		setWidget(outer);
	}

	@Override
	public boolean onKeyDownPreview(char key, int modifiers) {
		switch (key) {
		case KeyCodes.KEY_ENTER:
		case KeyCodes.KEY_ESCAPE:
			hide();
			break;
		}
		return true;
	}
}
