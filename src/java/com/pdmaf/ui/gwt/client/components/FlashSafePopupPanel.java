package com.pdmaf.ui.gwt.client.components;


import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;
import com.pdmaf.ui.gwt.client.components.eventhandlers.EventPopupListener;
import com.pdmaf.ui.gwt.client.fx.Effect;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 20, 2009
 * Time: 9:21:53 AM
 */

public class FlashSafePopupPanel extends PopupPanel {

	private static boolean pageContainsFlash = false;
	private EventPopupListener popupListener;
	private Event lastMouseDownEvent;

	public static void setPageContainsFlash(boolean contains) {
		pageContainsFlash = contains;
	}

	private String width = null;
	private int bottom = -1, top = -1, left = -1;
	private IFrameElement iframe;
	private boolean showing = false;
	private Timer timer;

	public FlashSafePopupPanel(boolean autoHide) {
		super(autoHide);
	}
	public FlashSafePopupPanel(boolean autoHide, boolean modal) {
		super(autoHide, modal);
	}
	public void setPopupBottom(int pixels) {
		getElement().getStyle().setPropertyPx("bottom", pixels);
		bottom = pixels;
		positionAndSizeIFrame();
	}
	public void setPopupTop(int pixels) {
		DOM.setStyleAttribute(getElement(), "top", pixels + "px");
		top = pixels;
		positionAndSizeIFrame();
	}
	public void setPopupLeft(int pixels) {
		DOM.setStyleAttribute(getElement(), "left", pixels + "px");
		left = pixels;
		positionAndSizeIFrame();
	}
	public void setPopupListener(EventPopupListener listener) {
		this.popupListener = listener;
	}
	protected static native boolean isLinux() /*-{
		return (navigator.userAgent.indexOf("Linux") != -1);
	}-*/;
	public void fadeThenHide() {
		Effect.fx(Effect.FADEOUT, Effect.SPEED_FAST, this,
			new Command() {
			public void execute() {
				hide();
			}});
	}
	@Override
	public void hide(boolean autoClosed) {
		super.hide(autoClosed);
		if (!showing)
			return;
		if (popupListener != null)
			popupListener.onPopupClosed(autoClosed,
				autoClosed ? lastMouseDownEvent : null);
		showing = false;
		if (iframe != null) {
			iframe.getParentElement().removeChild(iframe);
			iframe = null;
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void show() {
		super.show();
		if (showing)
			return;
		showing = true;
		if (pageContainsFlash && isLinux()) {
			iframe = Document.get().createIFrameElement();
			iframe.setFrameBorder(0);
			iframe.setScrolling("no");
			iframe.getStyle().setProperty("position", "absolute");
			positionAndSizeIFrame();
			Document.get().getBody().insertBefore(iframe, getElement());
			timer = new Timer() {
				@Override
				public void run() {
					if (iframe != null) {
						iframe.getStyle().setPropertyPx(
							"height",
							FlashSafePopupPanel.this.getOffsetHeight());
					}
				}};
			timer.scheduleRepeating(250);
		}
	}

	private void positionAndSizeIFrame() {
		if (iframe != null) {
			Style s = iframe.getStyle();
			s.setProperty("width", width);
			s.setPropertyPx("left", left);
			if (top != -1)
				s.setPropertyPx("top", top);
			if (bottom != -1)
				s.setPropertyPx("bottom", bottom);
		}
	}

	@Override
	public void setPopupPosition(int left, int top) {
		super.setPopupPosition(left, top);
		this.left = left;
		this.top = top;
		positionAndSizeIFrame();
	}
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		this.width = width;
		positionAndSizeIFrame();
	}
	@Override
	public boolean onEventPreview(Event event) {
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
			lastMouseDownEvent = event;
		return super.onEventPreview(event);
	}
}
