package com.pdmaf.ui.gwt.client.table;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTML;

/**
 * Pagination is a wrapper for FlexTable that allows easy support for paging
 * through data.
 *
 * NOTE: In case your wondering, Pagination takes a FlexTable as a parameter to
 * abstract its logic from the view. Pagination (the term in general) is more of
 * a logic implementation than a UI control, it controls a table, hence the
 * current implementation. You can keep your UI construction in a completely
 * separate class to keep the MVC paradigm architecture.
 *
 * h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li>.oddRow { }</li>
 * <li>.evenRow { }</li>
 * <li>.oddCell { }</li>
 * <li>.evenCell { }</li>
 * </ul>
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
abstract public class PaginationBehavior {

	PaginationParameters parameters;

	Results results;

	int rowCount;

	int resultsPerPage;

	int page;

	FlexTable table;

	public PaginationBehavior(FlexTable table, int resultsPerPage) {
		table.clear();
		this.table = table;
		this.resultsPerPage = resultsPerPage;
	}

	public void showPage(int page, String parameter, boolean isAscending) {
		this.page = page;
		this.parameters = new PaginationParameters();
		this.parameters.setAscending(isAscending);
		this.parameters.setMaxResults(resultsPerPage);
		this.parameters.setParameter(parameter);

		int firstResults = 0;
		if (page > 1) {
			firstResults = (page - 1) * resultsPerPage;
		}

        this.parameters.setOffset(firstResults);
        getDataProvider().update(this.parameters, new AsyncCallback() {
                public void onSuccess(Object result) {
                    if (!(result instanceof Results)) {
                        com.google.gwt.user.client.Window.alert("in fail here");
                        throw new RuntimeException(
                                "result from DataProvider must be of type Results");
                    }
                    com.google.gwt.user.client.Window.alert("in here");
                    results = (Results) result;

                    int rowCount = table.getRowCount();

                    for (int i = 1; i < rowCount; i++) {
                        table.removeRow(i);
                    }
                    
                    if (results == null || results.getSize() == 0) {
                        showNoResultFound();
                        return;
                    }

                    List resultsList = results.getList();

                    rowCount = resultsList.size();

                    if (rowCount > resultsPerPage) {
                        rowCount = resultsPerPage;
                    }

                    for (int i = 0; i < rowCount; i++) {
                        getRowRenderer().populateRow(PaginationBehavior.this, i, resultsList.get(i));
                                    // Set style of row
                        DOM.setElementAttribute(
                                getTable().getRowFormatter().getElement(i),
                                "className", (i % 2 == 1 ? "evenRow" : "oddRow"));
                    }

                    onUpdateSuccess(result);
                }

			    public void onFailure(Throwable caught) {
                    com.google.gwt.user.client.Window.alert("in fail here");
				    onUpdateFailure(caught);
                }
        });

	}

	public void setCell(int row, int column, Widget widget) {
		table.setWidget(row, column, widget);

		// Set style of cell
		DOM.setElementAttribute(getTable().getCellFormatter().getElement(row, column),
				"className", (row % 2 == 1 ? "evenCell" : "oddCell"));
	}
    
	public FlexTable getTable() {
		return table;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public PaginationParameters getParameters() {
		return parameters;
	}

	public int getPage() {
		return page;
	}

	public Results getResults() {
		return results;
	}
    
	protected void onUpdateSuccess(Object result) {

	}

	protected void onUpdateFailure(Throwable caught) {
		//throw new RuntimeException(caught);
	}

	abstract protected RowRenderer getRowRenderer();

	abstract protected DataProvider getDataProvider();

    private void showNoResultFound() {
        HTML noResultText = new HTML("Your search found no result.  Please try" +
                                                " another more specific search...");
        noResultText.setStyleName("error");
        table.setWidget(1, 0, noResultText);
    }

}
