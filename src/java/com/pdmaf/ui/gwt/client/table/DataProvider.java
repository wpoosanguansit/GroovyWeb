package com.pdmaf.ui.gwt.client.table;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Retrieves the next page. Pass the AsyncCallback provided to a RPC call or
 * call it manually with a new List to update the table.
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
public interface DataProvider {
	/**
	 *
	 * Since javascript is asynchronous, after the Results object is create the
	 * updateTableCallback needs to be executed. Usually you will retrieve
	 * results via a remote servlet, so you can call your service with the
	 * callback.
	 *
	 * Ex. service.find(parameters, callback);
	 *
	 * @param parameters
	 * @param callback
	 * @return
	 */
	public abstract void update(PaginationParameters parameters,
			AsyncCallback updateTableCallback);
}