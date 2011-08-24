package com.pdmaf.ui.gwt.client.components.eventhandlers;

import com.google.gwt.user.client.Event;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 20, 2009
 * Time: 9:24:40 AM
 */
public interface EventPopupListener {
	/**
	 *
	 * @param autoClosed
	 * @param mousedownEvent if autoClosed is true, this will contain
	 * the event that caused the close.
	 */
	void onPopupClosed(boolean autoClosed, Event mousedownEvent);
}
