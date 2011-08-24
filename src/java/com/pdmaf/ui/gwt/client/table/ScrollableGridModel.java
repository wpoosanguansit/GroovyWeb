package com.pdmaf.ui.gwt.client.table;

public interface ScrollableGridModel {
	/**
	 * Called prior to the grid being drawn or any other methods being called.
	 * You can safely do nothing, but if you ever want to order a redraw or some such
	 * from inside your GridModel you'll need to store a reference to the grid.
	 */
	public abstract void init(DynamicGrid grid);
	
	/** Each ordinary row in the grid will have this height. */
	public abstract int rowHeight();
	
	/** The header 'row' can have a different height compared to ordinary rows. */
	public abstract int headerHeight();
	
	/**
	 * The width of the entire grid will not exceed this width.
	 * Just return -1 if the grid is to size itself to the sum of all columns, otherwise
	 * it will use a horizontal scrollbar if the columns add up to a width larger than this number.
	 * 
	 * Note that 20 pixels are added to this number to make room for the vertical scroll bar.
	 */
	public abstract int totalWidth();
	
	/**
	 * The number of rows to draw.
	 * In essence the vertical height will be header height + 'this' * row height, though
	 * an additional 20 pixels might be added if neccessary to accommodate a horizontal scroll bar.
	 */
	public abstract int visibleRows();
	
	/** How many columns this grid needs to draw. */
	public abstract int columnCount();
	
	/** Number of entries (rows) in this grid. */
	public abstract int rowCount();
	
	/** Called whenever a drawn row goes out of visibility range.
	 *  No garbage collection needed, so ordinarily you don't need to do anything. */
	public abstract void hidden(int row);
	
	/**
	 * Called whenever a not currently drawn row rolls into view.
	 * The 'widget' method on all column models is called BEFORE this method, but all calls
	 * occur in the same JS engine loop. This is useful, because if you are missing data for
	 * a given row, the relevant widgets are already drawn and attached, so you can update them
	 * in this method.
	 */
	public abstract void visible(int row);
	
	/** Each column needs a ColumnModel object to provide widgets and the like. */
	public abstract ColumnModel column(int idx);

    public abstract void sort(int columnIndex);
}
