package com.pdmaf.ui.gwt.client.fx;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 20, 2009
 * Time: 9:23:14 AM
 */

public class Effect {
	public static final String SHOW = "show";
	public static final String HIDE = "hide";
	public static final String FADEIN = "fadeIn";
	public static final String FADEOUT = "fadeOut";
	public static final String SLIDEDOWN = "slideDown";
	public static final String SLIDEUP = "slideUp";

	public static final String SPEED_SLOW = "slow";
	public static final String SPEED_NORMAL = "normal";
	public static final String SPEED_FAST = "fast";

	public static void fx(String effect, Widget w, Command callback) {
		fx(effect, w.getElement(), callback);
	}
	public static void fx(String effect, String speed, Widget w, Command callback) {
		fx(effect, speed, w.getElement(), callback);
	}
	public static void fx(String effect, Element elem, Command callback) {
		fx(effect, SPEED_NORMAL, elem, callback);
	}
	public static native void fx(String effect, String speed, Element elem, Command callback)/*-{
		$wnd.$(elem)[effect](speed, function() { if (callback != null) callback.@com.google.gwt.user.client.Command::execute()(); });
	}-*/;
}
