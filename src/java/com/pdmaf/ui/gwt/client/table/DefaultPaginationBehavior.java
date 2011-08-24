package com.pdmaf.ui.gwt.client.table;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Default Pagination Behavior
 *
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li>.oddRow { }</li>
 * <li>.evenRow { }</li>
 * <li>.oddCell { }</li>
 * <li>.evenCell { }</li>
 * <li>.headerRow { }</li>
 * <li>.headerCell { }</li>
 * <li>.pagingRow { }</li>
 * <li>.noPreviousPageCell { }</li>
 * <li>.previousPageCell { }</li>
 * <li>.pageCell { }</li>
 * <li>.currentPageCell { }</li>
 * <li>.noNextPageCell { }</li>
 * <li>.nextPageCell { }</li>
 * </ul>
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
abstract public class DefaultPaginationBehavior {

	private FlexTable[] pagingControls;

	private SortablePaginationBehavior pagination;

	private String nextPageText = "Next &#187;";

	private String previousPageText = "&#171; Previous";

	private int maxPageLinks = 11;

	public DefaultPaginationBehavior(FlexTable pagingControl,
			FlexTable resultsTable, int resultsPerPage) {
		this(new FlexTable[] { pagingControl }, resultsTable, resultsPerPage);
	}

	public DefaultPaginationBehavior(FlexTable[] pagingControls,
			FlexTable resultsTable, int resultsPerPage) {
		this.pagingControls = pagingControls;
		pagination = new SortablePaginationBehavior(resultsTable,
				resultsPerPage) {

			protected RowRenderer getRowRenderer() {
				return DefaultPaginationBehavior.this.getRowRenderer();
			}

			protected DataProvider getDataProvider() {
				return DefaultPaginationBehavior.this.getDataProvider();
			}

			protected Column[] getColumns() {
				return DefaultPaginationBehavior.this.getColumns();
			}

			protected void onUpdateSuccess(Object result) {
				updatePagingControls();
				DefaultPaginationBehavior.this.onUpdateSuccess(result);
			}

			protected void onUpdateFailure(Throwable caught) {
				DefaultPaginationBehavior.this.onUpdateFailure(caught);
			}

		};

		updatePagingControls();
	}

	private void updatePagingControls() {
		for (int i = 0; i < pagingControls.length; i++) {
			if (pagingControls[i].getRowCount() > 0) {
				pagingControls[i].removeRow(0);
			}
			pagingControls[i].insertRow(0);

			if (pagination.getResults() != null) {

				int totalPages = pagination.getResults().getSize()
						/ pagination.getResultsPerPage();
				if (pagination.getResults().getSize()
						% pagination.getResultsPerPage() != 0) {
					totalPages++;
				}

				// Previous Page
				Hyperlink previousPageLink = new Hyperlink(previousPageText,
						"previousPage");
				pagingControls[i].setWidget(0, 0, previousPageLink);
				if (pagination.getPage() == 1
						|| pagination.getResults().getSize() == 0) {
					pagingControls[i].getCellFormatter().addStyleName(0, 0,
							"noPreviousPageCell");
				} else {
					previousPageLink.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            pagination.showPage(pagination.getPage() - 1);
                        }
                    });
					pagingControls[i].getCellFormatter().addStyleName(0, 0,
							"previousPageCell");
				}

				// Pages
				int currentPageForCell = 1;
				int totalCells = totalPages;
				if (totalPages > maxPageLinks) {
					totalCells = maxPageLinks;

					if (pagination.getPage() > maxPageLinks / 2) {
						currentPageForCell = pagination.getPage()
								- (maxPageLinks / 2);
						if (pagination.getPage() >= totalPages
								- (maxPageLinks / 2)) {
							currentPageForCell = totalPages
									- (maxPageLinks - 1);
						}
					}
				}

				int cellNumber = 1;
				for (; cellNumber <= totalCells; cellNumber++) {
					Hyperlink pageLink = new Hyperlink(currentPageForCell + "",
							"page");
					pagingControls[i].setWidget(0, cellNumber, pageLink);
					final int tmp = currentPageForCell;
					if (pagination.getPage() != currentPageForCell) {
						pageLink.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                pagination.showPage(tmp);
                            }
                        });
						pagingControls[i].getCellFormatter().addStyleName(0,
								cellNumber, "pageCell");
					} else {
						pagingControls[i].getCellFormatter().addStyleName(0,
								cellNumber, "currentPageCell");
					}
					currentPageForCell++;
				}

				// Next Page
				Hyperlink nextPageLink = new Hyperlink(nextPageText, "nextPage");
				pagingControls[i].setWidget(0, cellNumber, nextPageLink);
				if (pagination.getPage() == totalPages
						|| pagination.getResults().getSize() == 0) {
					pagingControls[i].getCellFormatter().addStyleName(0,
							cellNumber, "noNextPageCell");
				} else {
					nextPageLink.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            pagination.showPage(pagination.getPage() + 1);
                        }
                    });

					pagingControls[i].getCellFormatter().addStyleName(0,
							cellNumber, "nextPageCell");
				}

			}
		}
	}

	public void setCell(int row, int column, Widget widget) {
		pagination.setCell(row, column, widget);
	}

	public void showPage(int pageNumber, String orderByProperty,
			boolean isAscending) {
		pagination.showPage(pageNumber, orderByProperty, isAscending);
	}

	abstract protected DataProvider getDataProvider();

	abstract protected RowRenderer getRowRenderer();

	abstract protected Column[] getColumns();

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

	public String getNextPageText() {
		return nextPageText;
	}

	public void setNextPageText(String nextPageText) {
		this.nextPageText = nextPageText;
	}

	public String getPreviousPageText() {
		return previousPageText;
	}

	public void setPreviousPageText(String previousPageText) {
		this.previousPageText = previousPageText;
	}

	public int getMaxPageLinks() {
		return maxPageLinks;
	}

	public void setMaxPageLinks(int maxPageLinks) {
		this.maxPageLinks = maxPageLinks;
	}

}
