package com.nanosim.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.nanosim.client.icons.NanosimImages;
import com.nanosim.client.internal.NanosimConstants;
import com.nanosim.client.internal.StyleSheetLoader;
import com.nanosim.model.Person;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HomeScreen extends Composite implements ClickHandler,
		ResizeHandler, HasSelectionHandlers<TreeItem> {

	/**
	 * The current style theme.
	 */
	public static String CUR_THEME = NanosimConstants.STYLE_THEME;

	/**
	 * A mapping of history tokens to their associated menu items.
	 */
	private Map<String, TreeItem> itemTokens = new HashMap<String, TreeItem>();

	/**
	 * A mapping of menu items to the widget display when the item is selected.
	 */
	private Map<TreeItem, ContentWidget> itemWidgets = new HashMap<TreeItem, ContentWidget>();

	private NanosimImages images;
	private NanosimConstants constants;

	public HomeScreen(Person person) {

		images = Nanosim.images;
		constants = Nanosim.constants;

		// Swap out the style sheets for the RTL versions if needed.
		updateStyleSheets();

		// Create the application
		setupTitlePanel(constants);
		setupLinks(constants);
		setupOptionsPanel();
		// setupMainMenu(constants);

		// Setup a history handler to reselect the associate menu item
		final ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				TreeItem item = itemTokens.get(event.getValue());
				if (item == null) {
					item = getMainMenu().getItem(0).getChild(0);
				}

				// Select the associated TreeItem
				getMainMenu().setSelectedItem(item, false);
				getMainMenu().ensureSelectedItemVisible();

				// Show the associated ContentWidget
				displayContentWidget(itemWidgets.get(item));
			}
		};
		History.addValueChangeHandler(historyHandler);

		// Add a handler that sets the content widget when a menu item is
		// selected
		addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				ContentWidget content = itemWidgets.get(item);
				if (content != null && !content.equals(getContent())) {
					History.newItem(getContentWidgetToken(content));
				}
			}
		});

		// Show the initial example
		if (History.getToken().length() > 0) {
			History.fireCurrentHistoryState();
		} else {
			// Use the first token available
			TreeItem firstItem = getMainMenu().getItem(0).getChild(0);
			getMainMenu().setSelectedItem(firstItem, false);
			getMainMenu().ensureSelectedItemVisible();
			displayContentWidget(itemWidgets.get(firstItem));
		}
	}

	/**
	 * Set the content to the {@link ContentWidget}.
	 * 
	 * @param content
	 *            the {@link ContentWidget} to display
	 */
	private void displayContentWidget(ContentWidget content) {
		if (content != null) {
			setContent(content);
			setContentTitle(content.getTabBar());
		}
	}

	/**
	 * Get the token for a given content widget.
	 * 
	 * @return the content widget token.
	 */
	private String getContentWidgetToken(ContentWidget content) {
		String className = content.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className;
	}

	/**
	 * Create the links at the top of the application.
	 * 
	 */
	HTML signOutLink = new HTML("<a href='javascript:;'>Sign Out</a>");
	HTML aboutLink = new HTML("<a href='javascript:;'>About</a>");

	private void setupLinks(NanosimConstants constants) {
		// Link to About
		addLink(aboutLink);
		aboutLink.addClickHandler(this);

		// Link to SignOut
		addLink(signOutLink);
		signOutLink.addClickHandler(this);
	}

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == signOutLink) {
			// Nanosim.getInstance().Logout();
		} else if (sender == aboutLink) {
			DialogBox dlg = AboutBox();
			dlg.show();
			dlg.center();
		}
	}

	/**
	 * Create the options that appear next to the title.
	 */
	private void setupOptionsPanel() {
		// Get the title from the internationalized constants
		// TODO add user name
		String pageTitle = constants.welcome() + "user";
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.getElement().setAttribute("align", "right");
		setOptionsWidget(vPanel);
		vPanel.add(new HTML(pageTitle));
	}

	/**
	 * Create the title bar at the top of the application.
	 * 
	 * @param constants
	 *            the constant values to use
	 */
	private void setupTitlePanel(NanosimConstants constants) {
		// Get the title from the internationalized constants
		String pageTitle = "<h1>" + constants.mainTitle() + "</h1><h2>"
				+ constants.mainSubTitle() + "</h2>";

		// Add the title and some images to the title bar
		VerticalPanel titlePanel = new VerticalPanel();
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		titlePanel.add(images.logo().createImage());
		titlePanel.add(new HTML(pageTitle));
		setTitleWidget(titlePanel);
	}

	/**
	 * Update the style sheets to reflect the current theme and direction.
	 */
	private void updateStyleSheets() {
		// Generate the name of the style sheet to include
		String gwtStyleSheet = "gwt/" + CUR_THEME + "/" + CUR_THEME + ".css";
		String NanosimStyleSheet = CUR_THEME + "/Nanosim.css";

		// Load the GWT theme style sheet
		String modulePath = GWT.getModuleBaseURL();
		Command callback = new Command() {
			private int numStyleSheetsLoaded = 0;

			public void execute() {
				// Wait until all style sheets have loaded before re-attaching
				// the app
				numStyleSheetsLoaded++;
				if (numStyleSheetsLoaded < 2) {
					return;
				}
				RootPanel.getBodyElement().getStyle().setProperty("display",
						"none");
				RootPanel.getBodyElement().getStyle()
						.setProperty("display", "");
				// RootPanel.get().add(app);
			}
		};

		StyleSheetLoader.loadStyleSheet(modulePath + gwtStyleSheet, "gwt"
				+ "-Reference-" + CUR_THEME, callback);
		// Load the Nanosim specific style sheet after the GWT theme style sheet
		// so
		// that custom styles supercede the theme styles.
		StyleSheetLoader.loadStyleSheet(modulePath + NanosimStyleSheet,
				"Application" + "-Reference-" + CUR_THEME, callback);
	}

	private DialogBox AboutBox() {
		// Create a dialog box and set the caption text
		final DialogBox aboutBox = new DialogBox();
		aboutBox.setText(constants.cwAboutBoxTitle());

		// Create a VerticalPanel to contain the 'about' label and the 'OK'
		// button.
		VerticalPanel outer = new VerticalPanel();
		outer.add(new HTML(constants.cwAboutBoxMessage()));
		outer.add(new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				aboutBox.hide();
			}
		}));
		aboutBox.setWidget(outer);
		// Return the about box
		return aboutBox;
	}

	/**
	 * The base style name.
	 */
	public static final String DEFAULT_STYLE_NAME = "Application";

	/**
	 * The panel that contains the menu and content.
	 */
	private HorizontalPanel bottomPanel;

	/**
	 * The decorator around the content.
	 */
	private DecoratorPanel contentDecorator;

	/**
	 * The main wrapper around the content and content title.
	 */
	private Grid contentLayout;

	/**
	 * The wrapper around the content.
	 */
	private SimplePanel contentWrapper;

	/**
	 * The panel that holds the main links.
	 */
	private HorizontalPanel linksPanel;

	/**
	 * The main menu.
	 */
	private Tree mainMenu;

	/**
	 * The last known width of the window.
	 */
	private int windowWidth = -1;

	/**
	 * The panel that contains the title widget and links.
	 */
	private FlexTable topPanel;

	/**
	 * Add a link to the top of the page.
	 * 
	 * @param link
	 *            the widget to add to the mainLinks
	 */
	public void addLink(Widget link) {
		if (linksPanel.getWidgetCount() > 0) {
			linksPanel.add(new HTML("&nbsp;|&nbsp;"));
		}
		linksPanel.add(link);
	}

	public HandlerRegistration addSelectionHandler(
			SelectionHandler<TreeItem> handler) {
		return mainMenu.addSelectionHandler(handler);
	}

	/**
	 * @return the {@link Widget} in the content area
	 */
	public Widget getContent() {
		return contentWrapper.getWidget();
	}

	/**
	 * @return the content title widget
	 */
	public Widget getContentTitle() {
		return contentLayout.getWidget(0, 0);
	}

	/**
	 * @return the main menu.
	 */
	public Tree getMainMenu() {
		return mainMenu;
	}

	/**
	 * @return the {@link Widget} used as the title
	 */
	public Widget getTitleWidget() {
		return topPanel.getWidget(0, 0);
	}

	public void onResize(ResizeEvent event) {
		onWindowResized(event.getWidth(), event.getHeight());
	}

	public void onWindowResized(int width, int height) {
		if (width == windowWidth || width < 1) {
			return;
		}
		windowWidth = width;
		onWindowResizedImpl(width);
	}

	/**
	 * Set the {@link Widget} to display in the content area.
	 * 
	 * @param content
	 *            the content widget
	 */
	public void setContent(Widget content) {
		if (content == null) {
			contentWrapper.setWidget(new HTML("&nbsp;"));
		} else {
			contentWrapper.setWidget(content);
		}
	}

	/**
	 * Set the title of the content area.
	 * 
	 * @param title
	 *            the content area title
	 */
	public void setContentTitle(Widget title) {
		contentLayout.setWidget(0, 0, title);
	}

	/**
	 * Set the {@link Widget} to use as options, which appear to the right of
	 * the title bar.
	 * 
	 * @param options
	 *            the options widget
	 */
	public void setOptionsWidget(Widget options) {
		topPanel.setWidget(1, 1, options);
	}

	/**
	 * Set the {@link Widget} to use as the title bar.
	 * 
	 * @param title
	 *            the title widget
	 */
	public void setTitleWidget(Widget title) {
		topPanel.setWidget(1, 0, title);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		onWindowResized(Window.getClientWidth(), Window.getClientHeight());
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		windowWidth = -1;
	}

	protected void onWindowResizedImpl(int width) {
		int menuWidth = mainMenu.getOffsetWidth();
		int contentWidth = Math.max(width - menuWidth - 30, 1);
		int contentWidthInner = Math.max(contentWidth - 10, 1);
		bottomPanel.setCellWidth(mainMenu, menuWidth + "px");
		bottomPanel.setCellWidth(contentDecorator, contentWidth + "px");
		contentLayout.getCellFormatter().setWidth(0, 0,
				contentWidthInner + "px");
		contentLayout.getCellFormatter().setWidth(1, 0,
				contentWidthInner + "px");
	}
}
