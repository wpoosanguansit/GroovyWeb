package com.pdmaf.ui.gwt.client.utils;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:19:31 PM
 */
public class PagingHelper {
	private static final int PAGES_SHOWN = 5;

	public static int[] pages(int curPageNo, int totalNumPages) {

		int[] pages = new int[(totalNumPages < PAGES_SHOWN ? totalNumPages : PAGES_SHOWN) + 2];

		pages[0] = (curPageNo == 1) ? -1 : curPageNo - 1;
		pages[pages.length - 1] = (curPageNo == totalNumPages) ? -1 : curPageNo + 1;

		int startPage;

		if (totalNumPages <= PAGES_SHOWN || (curPageNo - PAGES_SHOWN / 2) < 1)
			startPage = 1;
		else if ((curPageNo + PAGES_SHOWN/2) > totalNumPages)
			startPage = totalNumPages - PAGES_SHOWN + 1;
		else
			startPage = curPageNo - PAGES_SHOWN / 2;

		for (int i = 1; i < pages.length - 1; i++){
			pages[i] = startPage++;
		}
		return pages;
	}

}
