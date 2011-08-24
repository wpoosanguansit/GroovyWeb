package com.pdmaf.ui.gwt.client.table;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 27, 2009
 * Time: 12:09:56 PM
 * This is to capture the parameters associated with pagination
 * 
 */
public class PaginationParameters {

	private int offset;

	private int maxResults;

	private String parameter;

	private boolean isAscending;

	public final int getOffset() {
		return offset;
	}

	public void setOffset(int firstResults) {
		this.offset = firstResults;
	}

	public final boolean isAscending() {
		return isAscending;
	}

	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}

	public final int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public final String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
