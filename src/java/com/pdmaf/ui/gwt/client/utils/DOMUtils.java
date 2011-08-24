package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 2:03:07 PM
 * 
 */
public class DOMUtils {
	public static native void addScript(String url, boolean useWnd)/*-{
		var wnd = useWnd ? $wnd : window;
		var elem = wnd.document.createElement("script");
		elem.setAttribute("type", "text/javascript");
		elem.setAttribute("charset", "utf-8");
		elem.setAttribute("src", url);
		wnd.document.getElementsByTagName("body")[0].appendChild(elem);
	}-*/;
	public static void setVisible(Element elem, boolean visible) {
		elem.getStyle().setProperty("visibility", visible ? "visible" : "hidden");
	}
	/**
	 * Either we will end up at the top or bottom of the document, or
	 * y will end up in the middle of the screen.
	 * @param y
	 * @param onFinished Can be null.
	 */
	public static void animateMidScrollTo(int y, final Command onFinished) {
		animateScrollTo(y - Window.getClientHeight() / 2, onFinished);
	}
	public static void animateScrollTo(int y, final Command onFinished) {
		final int begY = Window.getScrollTop();
		final int endY =
			Math.min(RootPanel.get().getOffsetHeight() - Window.getClientHeight(),
					Math.max(0, y));
		Animation scrollAnim = new Animation() {
			@Override
			protected void onUpdate(double progress) {
				scrollTo(begY + (int)((endY - begY) * progress));
			}

			@Override
			protected void onComplete() {
				super.onComplete();
				if (onFinished != null)
					onFinished.execute();
			}
		};
		scrollAnim.run(Math.min(500, Math.abs(endY - begY)));
	}
	public static native void scrollTo(int y) /*-{
		$wnd.scrollTo(0, y);
	}-*/;
	public static String escapeHtml(String maybeHtml) {
	      final Element div = DOM.createDiv();
	      div.setInnerText(maybeHtml);
	      return div.getInnerHTML();
	   }

}
