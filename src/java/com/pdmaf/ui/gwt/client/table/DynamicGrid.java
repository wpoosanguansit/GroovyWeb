package com.pdmaf.ui.gwt.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.dom.client.ScrollEvent;

public class DynamicGrid extends Composite implements ScrollHandler {
   
	private static final int SCROLLBAR_SIZE = 20;
	
	private final ScrollableGridModel model;
	
	private AbsolutePanel root, headerContainer, header, fullSize;
	private Widget[][] rowWidgets;
	private int idxOfTopRow = 0;
	private ScrollPanel scroller;
	
	private ColumnModel[] columns;
	private int allColumnsWidth;
	private int gridWidth;
	private boolean horizScrolling;
	private int visibleRows;
	private int rowHeight;
	private int[] columnPos;
	
	private List headerWidgets = new ArrayList();
	
	private boolean updating = false;
	
	public DynamicGrid(ScrollableGridModel model) {
		this.model = model;
		model.init(this);
		initGfx();
		initWidget(root);
	}

    public int getHorizontalSize() {
		return gridWidth - SCROLLBAR_SIZE;
	}
	
	public void refreshAll() {
		reloadMetaData();
		redrawHeaders();
		redrawData();
	}
	
	public void refreshCells() {
		redrawCells();
	}
	
	public void refreshRow(int row) {
		int i = row - idxOfTopRow;
		if ( i < 0 || i >= visibleRows ) return;
		
		for ( int k = 0 ; k < columns.length ; k++ ) {
			Widget existing = rowWidgets[i][k];
			rowWidgets[i][k] = columns[k].widget(row, existing);
			if ( existing != null && existing != rowWidgets[i][k] ) fullSize.remove(existing);
			rowWidgets[i][k].setPixelSize(columns[k].width(), rowHeight);
			fullSize.add(rowWidgets[i][k], columnPos[k], (i + idxOfTopRow) * rowHeight);
		}
	}
	
	private void initGfx() {
		headerContainer = new AbsolutePanel();
		header = new AbsolutePanel();
		headerContainer.add(header, 0, 0);
		fullSize = new AbsolutePanel();
		scroller = new ScrollPanel(fullSize);
		scroller.addScrollHandler(this);
		
		root = new AbsolutePanel();
		root.add(headerContainer, 0, 0);
		root.add(scroller, 0, model.headerHeight());
		
		refreshAll();
	}
	
	private void redrawHeaders() {
		headerContainer.setPixelSize(gridWidth, model.headerHeight());
		header.setPixelSize(allColumnsWidth, model.headerHeight());
		
		for ( int i = 0 ; i < columns.length ; i++ ) {
			Widget existing = (headerWidgets.size() > i) ? (Widget)headerWidgets.get(i) : null;
			Widget widget = columns[i].headerWidget(existing);
			if ( headerWidgets.size() > i ) {
                headerWidgets.set(i, widget);
            }
			else headerWidgets.add(widget);
			header.add(widget, columnPos[i], 0);
			widget.setPixelSize(columns[i].width(), model.headerHeight());
		}
		
		while ( headerWidgets.size() > columns.length ) headerWidgets.remove(headerWidgets.size() -1);
		
		int hPos = scroller.getHorizontalScrollPosition();
		if ( hPos < 0 ) hPos = 0;
		
		headerContainer.setWidgetPosition(header, -hPos, 0);
	}
	
	private void redrawData() {
		int visibleRows = model.visibleRows();
		int rowCount = model.rowCount();
		if ( rowCount < visibleRows ) visibleRows = rowCount;
		int scrollerVertSize = visibleRows * model.rowHeight() + (horizScrolling ? SCROLLBAR_SIZE : 0);
		
		fullSize.setPixelSize(allColumnsWidth, rowCount * model.rowHeight());
		scroller.setPixelSize(gridWidth, scrollerVertSize);
		root.setWidgetPosition(scroller, 0, model.headerHeight());
		root.setPixelSize(gridWidth, model.headerHeight() + scrollerVertSize);
		
		redrawCells();
	}
	
	private void redrawCells() {
		if ( scroller == null ) return;
		
		updating = true;
		
		for ( int i = 0 ; i < visibleRows ; i++ ) if ( rowWidgets[i][0] != null ) model.hidden(i + idxOfTopRow);
		
		idxOfTopRow = scroller.getScrollPosition() / model.rowHeight();
		int rowCount = model.rowCount();
		for ( int i = 0 ; i < visibleRows ; i++ ) {
			int idx = idxOfTopRow + i;
			if ( idx >= rowCount ) break;
			for ( int k = 0 ; k < columns.length ; k++ ) {
				Widget existing = rowWidgets[i][k];
				rowWidgets[i][k] = columns[k].widget(idx, existing);
				if ( existing != null && existing != rowWidgets[i][k] ) fullSize.remove(existing);
				rowWidgets[i][k].setPixelSize(columns[k].width(), rowHeight);
				fullSize.add(rowWidgets[i][k], columnPos[k], (i + idxOfTopRow) * rowHeight);
			}
			model.visible(idx);
		}
		
		updating = false;
	}
	
	private void updateCells() {
		if ( scroller == null ) return;
		
		updating = true;
		
		int rowCount = model.rowCount();
		
		int newIdxOfTopRow = scroller.getScrollPosition() / model.rowHeight();
		
		if ( newIdxOfTopRow > rowCount - visibleRows ) newIdxOfTopRow = rowCount - visibleRows;
		
		if ( newIdxOfTopRow < idxOfTopRow ) {
			//scrolling up, so we take the widgets of the bottom row,
			//roll them through the column model, and move them up.
			int oldRowNr = idxOfTopRow + visibleRows -1;
			int rowsToUpdate = (idxOfTopRow - newIdxOfTopRow);
			if ( rowsToUpdate > visibleRows ) rowsToUpdate = visibleRows;
			for ( int i = rowsToUpdate -1 ; i >= 0 ; i-- ) {
				//First, notify the model we're about to nix one of the rows
				if ( rowWidgets[visibleRows -1][0] != null ) model.hidden(oldRowNr--);
				//Then, update the widgets
				Widget[] newRowWidgets = new Widget[columns.length];
				for ( int k = 0 ; k < columns.length ; k++ ) {
					Widget existing = rowWidgets[visibleRows -1][k];
					newRowWidgets[k] = columns[k].widget(newIdxOfTopRow + i, existing);
					if ( existing != null && newRowWidgets[k] != existing ) fullSize.remove(newRowWidgets[k]);
				}
				//Next, move the new row up to the top, moving the rest down:
				for ( int j = visibleRows -1 ; j > 0 ; j-- ) for ( int k = 0 ; k < columns.length ; k++ )
					rowWidgets[j][k] = rowWidgets[j -1][k];
				for ( int k = 0 ; k < columns.length ; k++ ) rowWidgets[0][k] = newRowWidgets[k];
				//Finally, move the widgets to their appropriate place, and set size.
				for ( int k = 0 ; k < columns.length ; k++ ) {
					rowWidgets[0][k].setPixelSize(columns[k].width(), rowHeight);
					fullSize.add(rowWidgets[0][k], columnPos[k], (i + newIdxOfTopRow) * rowHeight);
				}
				model.visible(newIdxOfTopRow + i);
			}
		} else if ( newIdxOfTopRow > idxOfTopRow ) {
			//scrolling down, so we take the widgets of the top row,
			//roll them through the column model, and move them down.
			int oldRowNr = idxOfTopRow;
			int rowsToUpdate = (newIdxOfTopRow - idxOfTopRow);
			if ( rowsToUpdate > visibleRows ) rowsToUpdate = visibleRows;
			int rowIdx = newIdxOfTopRow + visibleRows - rowsToUpdate;
			for ( int i = 0 ; i < rowsToUpdate ; i++ ) {
				//First, notify the model we're about to nix one of the rows
				if ( rowWidgets[0][0] != null ) model.hidden(oldRowNr++);
				//Then, update the widgets
				Widget[] newRowWidgets = new Widget[columns.length];
				for ( int k = 0 ; k < columns.length ; k++ ) {
					Widget existing = rowWidgets[0][k];
					newRowWidgets[k] = columns[k].widget(rowIdx, existing);
					if ( existing != null && newRowWidgets[k] != existing ) fullSize.remove(existing);
				}
				//Next, move the new row down to the bottom, moving the rest up:
				for ( int j = 1 ; j < visibleRows ; j++ ) for ( int k = 0 ; k < columns.length ; k++ )
					rowWidgets[j-1][k] = rowWidgets[j][k];
				for ( int k = 0 ; k < columns.length ; k++ ) rowWidgets[visibleRows -1][k] = newRowWidgets[k];
				//Finally, move the widgets to their appropriate place, and set size.
				for ( int k = 0 ; k < columns.length ; k++ ) {
					
					rowWidgets[visibleRows -1][k].setPixelSize(columns[k].width(), rowHeight);
					fullSize.add(rowWidgets[visibleRows -1][k], columnPos[k], rowIdx * rowHeight);
				}
				model.visible(rowIdx++);
			}
		}
		
		idxOfTopRow = newIdxOfTopRow;
		updating = false;
	}
	
	private void reloadMetaData() {
		columns = new ColumnModel[model.columnCount()];
		allColumnsWidth = 0;
		rowHeight = model.rowHeight();
		columnPos = new int[columns.length];
		
		for ( int i = 0 ; i < columns.length ; i++ ) {
			columns[i] = model.column(i);
			columnPos[i] = allColumnsWidth;
			allColumnsWidth += columns[i].width();
		}
		
		int modelTotalWidth = model.totalWidth();
		
		gridWidth = modelTotalWidth < 0 ? allColumnsWidth + SCROLLBAR_SIZE : modelTotalWidth;
		if ( gridWidth > allColumnsWidth + SCROLLBAR_SIZE ) gridWidth = allColumnsWidth + SCROLLBAR_SIZE;
		horizScrolling = gridWidth < allColumnsWidth;
		
		//Rows can 'hang' half-visible at the top or bottom, so we need to draw 1 more.
		visibleRows = model.visibleRows() + 1;
		
		if ( rowWidgets == null || rowWidgets.length != visibleRows || (rowWidgets.length > 0 && rowWidgets[0].length != columns.length) ) {
			if ( rowWidgets != null ) for ( int x = 0 ; x < rowWidgets.length ; x++ ) for ( int y = 0 ; y < rowWidgets[x].length ; y++ ) {
				Widget existing = rowWidgets[x][y];
				if ( existing != null ) fullSize.remove(existing);
			}
			rowWidgets = new Widget[visibleRows][columns.length];
		}
	}
	
	private void updateHScroll() {
		int hPos = scroller.getHorizontalScrollPosition();
		if ( hPos < 0 ) hPos = 0;
		headerContainer.setWidgetPosition(header, -hPos, 0);
	}

    @Override
    public void onScroll(ScrollEvent event) {
        if ( !updating ) {
			updateCells();
			updateHScroll();
			DeferredCommand.addCommand(new Command() { public void execute() {
				updateCells();
				updateHScroll();
			}});
		}
    }
}
