package com.pdmaf.ui.gwt.client.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li>.oddRow { }</li>
 * <li>.evenRow { }</li>
 * <li>.oddCell { }</li>
 * <li>.evenCell { }</li>
 * <li>.headerRow { }</li>
 * <li>.headerCell { }</li>
 * </ul>
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
abstract public class SortablePaginationBehavior {

	private String sortAscImage = GWT.getModuleBaseURL() + "images/asc.gif";

	private String sortDescImage = GWT.getModuleBaseURL() + "images/desc.gif";

	private FlexTable table;

	private PaginationBehavior pagination;

	private Column[] columns;

	/**
	 * PaginationTable builds upon Pagination to provide a row of column headers
	 * that control sorting and a row with page controls to control paging.
	 *
	 * @param table
	 * @param resultsPerPage
	 */
	public SortablePaginationBehavior(FlexTable table, int resultsPerPage) {
		this.table = table;

		this.pagination = new PaginationBehavior(table, resultsPerPage) {

			protected DataProvider getDataProvider() {
				return SortablePaginationBehavior.this.getDataProvider();
			}

			protected RowRenderer getRowRenderer() {
				return SortablePaginationBehavior.this.getRowRenderer();
			}

			protected void onUpdateSuccess(Object result) {
				insertColumnHeaders();
				SortablePaginationBehavior.this.onUpdateSuccess(result);
			}

			protected void onUpdateFailure(Throwable caught) {
				SortablePaginationBehavior.this.onUpdateFailure(caught);
			}

		};

		this.columns = getColumns();
		insertColumnHeaders();
	}

	abstract protected DataProvider getDataProvider();

	abstract protected RowRenderer getRowRenderer();

	abstract protected Column[] getColumns();

	public String getSortAscImage() {
		return sortAscImage;
	}

	public String getSortDescImage() {
		return sortDescImage;
	}

	public FlexTable getTable() {
		return table;
	}

	public void setSortAscImage(String sortAscImage) {
		this.sortAscImage = sortAscImage;
	}

	public void setSortDescImage(String sortDescImage) {
		this.sortDescImage = sortDescImage;
	}

	private void insertColumnHeaders() {
		table.insertRow(0);

		for (int i = 0; i < columns.length; i++) {
			addColumn(0, i, columns[i]);
		}

		// Set style of row
		DOM.setElementAttribute(getTable().getRowFormatter().getElement(0),
				"className", "headerRow");
	}

	private void addColumn(int row, int col, final Column column) {

		if (column.isSortable()) {
			Hyperlink link = new Hyperlink();
			link.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (pagination.getParameters() == null) {
                        return;
                    }
                    boolean isAscending = pagination.getParameters()
							.isAscending();
					if (column.getParameter().equals(
							pagination.getParameters().getParameter())) {
						isAscending = !isAscending;
					}

					pagination.showPage(1, column.getParameter(), isAscending);
                }
            });
			updateColumn(column, link);
			table.setWidget(row, col, link);
		} else {
			Label label = new Label();
			updateColumn(column, label);
			table.setWidget(row, col, label);
		}

		// Set style of cell
		DOM.setElementAttribute(getTable().getCellFormatter().getElement(row, col),
				"className", "headerCell");
		getTable().getCellFormatter().setVerticalAlignment(row, col,
				HasVerticalAlignment.ALIGN_MIDDLE);
	}

	private void updateColumn(Column column, Widget widget) {
		if (column.isSortable()) {
			Hyperlink link = (Hyperlink) widget;

			link.setText(column.getTitle());

			if (pagination.getParameters() != null
					&& column.getParameter().equals(
							pagination.getParameters().getParameter())) {

				String imageLocation = null;
				if (pagination.getParameters().isAscending()) {
					imageLocation = getSortAscImage();
				} else {
					imageLocation = getSortDescImage();
				}

				// Append an arrow to show the direction
				link.setHTML(link.getText() + "&nbsp;<img border=\"0\" src=\""
						+ imageLocation + "\" />");
			}
		} else {
			Label label = (Label) widget;
			label.setText(column.getTitle());
		}

	}

	public void showPage(int pageNumber) {
		showPage(pageNumber, getParameters().getParameter(), getParameters()
				.isAscending());
	}

	public void showPage(int pageNumber, String parameter, boolean isAscending) {
		pagination.showPage(pageNumber, parameter, isAscending);
	}

	public void setCell(int row, int column, Widget widget) {
		pagination.setCell(row, column, widget);
	}

	protected void onUpdateSuccess(Object result) {

	}

	protected void onUpdateFailure(Throwable caught) {
		//throw new RuntimeException(caught);
	}

	public int getRowCount() {
		return pagination.getRowCount();
	}

	public PaginationParameters getParameters() {
		return pagination.getParameters();
	}

	public int getResultsPerPage() {
		return pagination.getResultsPerPage();
	}

	public int getPage() {
		return pagination.getPage();
	}

	public Results getResults() {
		return pagination.getResults();
	}

}
