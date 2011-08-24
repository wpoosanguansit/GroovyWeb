package com.pdmaf.ui.gwt.client.components;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 20, 2009
 * Time: 9:27:07 AM
 */
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.pdmaf.ui.gwt.client.utils.ButtonStyler;

public class GenericDialog extends FlashSafePopupPanel {
	public static interface ButtonClickListener {
		void buttonClicked(int buttonNo);
	}

	/**
	 * Dialog closes on button click
	 * @param imageProto Null for no image
	 * @param title
	 * @param text
	 * @param listener Null for no listener
	 */
	public static void showOKDialog(AbstractImagePrototype imageProto, String title, String text, ButtonClickListener listener) {
		GenericDialog gd = new GenericDialog(imageProto, title, text, new String[] { "OK" }, listener);
		gd.center();
	}
	/**
	 * Dialog closes on button click.
	 * @param imageProto Null for no image
	 * @param title
	 * @param text
	 * @param buttons
	 * @param listener null for no listener
	 */
	public static void showMultiButtonDialog(AbstractImagePrototype imageProto, String title, String text, String[] buttons, ButtonClickListener listener) {
		GenericDialog gd = new GenericDialog(imageProto, title, text, buttons, listener);
		gd.center();
	}


	protected FlowPanel mainPanel;
	private ButtonClickListener listener;

	protected GenericDialog(AbstractImagePrototype imageProto, String title, String[] buttons, ButtonClickListener listener, boolean closeOnClick) {
		super(false, true);
		this.listener = listener;
		setWidth("340px");
		addStyleName("pdmaf-GenericDialog");
		HTML topPanel = new HTML();
		topPanel.setStyleName("topPanel");
		if (imageProto != null)
			topPanel.setHTML(imageProto.getHTML() + title);
		else
			topPanel.setHTML(title);
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("mainPanel");
		FlowPanel bottomPanel = new FlowPanel();
		bottomPanel.setStyleName("bottomPanel");
		for (int i = buttons.length - 1; i >= 0; i--)
			bottomPanel.add(makeButton(buttons[i], i, closeOnClick));
		FlowPanel container = new FlowPanel();
		container.add(topPanel);
		container.add(mainPanel);
		container.add(bottomPanel);
		setWidget(container);
	}
	/**
	 * Used to create a generic dialog that shows multiple buttons. usuapdmafy "do something" or cancel.
	 * @param imageProto
	 * @param title
	 * @param text
	 * @param buttons
	 * @param listener
	 */
	protected GenericDialog(AbstractImagePrototype imageProto, String title, String text, String[] buttons, ButtonClickListener listener) {
		this(imageProto, title, buttons, listener, true);
		mainPanel.add(new Label(text));
	}
	protected void setListener(ButtonClickListener listener) {
		this.listener = listener;
	}
	private PushButton makeButton(String text, final int number, final boolean closeOnClick) {
		return ButtonStyler.submit(new PushButton(text, new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (listener != null)
					listener.buttonClicked(number);
				if (closeOnClick)
					fadeThenHide();
			}}));
	}
}
