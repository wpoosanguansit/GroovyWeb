package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.user.client.ui.Widget;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 1:44:01 PM
 * 
 */
public interface TooltipFactory {
	Tooltip show(Widget widget, String text, boolean isHTML);
}
