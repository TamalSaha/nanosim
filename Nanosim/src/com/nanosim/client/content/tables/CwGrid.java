package com.nanosim.client.content.tables;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.nanosim.client.ContentWidget;
import com.nanosim.client.Nanosim;

/**
 * Example file.
 */
public class CwGrid extends ContentWidget {
	/**
	 * The constants used in this Content Widget.
	 */

	public static interface CwConstants extends Constants,
			ContentWidget.CwConstants {
		String cwGridDescription();

		String cwGridName();
	}

	/**
	 * An instance of the constants.
	 */
	
	private CwConstants constants;

	/**
	 * Constructor.
	 * 
	 * @param constants
	 *            the constants
	 */
	public CwGrid(CwConstants constants) {
		super(constants);
		this.constants = constants;
	}

	@Override
	public String getDescription() {
		return constants.cwGridDescription();
	}

	@Override
	public String getName() {
		return constants.cwGridName();
	}

	@Override
	public boolean hasStyle() {
		return false;
	}

	/**
	 * Initialize this example.
	 */

	@Override
	public Widget onInitialize() {
		// Create a grid
		Grid grid = new Grid(4, 4);

		// Add images to the grid
		int numRows = grid.getRowCount();
		int numColumns = grid.getColumnCount();
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				grid.setWidget(row, col, Nanosim.images.logo().createImage());
			}
		}

		// Return the panel
		grid.ensureDebugId("cwGrid");
		return grid;
	}
}
