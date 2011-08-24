package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 21, 2009
 * Time: 7:21:08 PM
 */

public class ButtonStyler {
	private static final String GWTPUSHBUTTONCLASS = "gwt-PushButton";
	public static final String WHITELINKCLASS = "pdmaf-WhiteLinkButton";
	public static final String PLAINWHITELINKCLASS = "pdmaf-PWLinkButton";
	public static final String LINKCLASS = "pdmaf-LinkButton";

	public static <T extends Widget> T link(T button) {
		button.setStylePrimaryName(LINKCLASS);
		button.addStyleName(GWTPUSHBUTTONCLASS);
		return button;
	}
	public static PushButton whiteLink(PushButton button) {
		button.setStylePrimaryName(WHITELINKCLASS);
		button.addStyleName(GWTPUSHBUTTONCLASS);
		return button;
	}
	public static PushButton plainWhiteLink(PushButton button) {
		button.setStylePrimaryName(PLAINWHITELINKCLASS);
		button.addStyleName(GWTPUSHBUTTONCLASS);
		return button;
	}
	public static PushButton submit(PushButton button) {
		button.addStyleName("pdmaf-SubmitButton");
		return button;
	}

}