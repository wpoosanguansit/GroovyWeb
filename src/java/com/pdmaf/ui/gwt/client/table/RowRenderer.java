package com.pdmaf.ui.gwt.client.table;

/**
 * RowRenderer lets you dynamically set the widgets for the cells of the table.
 * populateRow is called once for every object on the Pagination page.
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
public interface RowRenderer {
	/**
	 * @param pagination
	 * @param row
	 *            The row the object should be populated on. NOTE: row starts
	 *            from 1 since 0 is the header row.
	 * @param object
	 */
	public void populateRow(PaginationBehavior pagination, int row, Object object);
}
