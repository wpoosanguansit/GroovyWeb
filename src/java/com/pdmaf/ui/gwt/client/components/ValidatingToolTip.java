package com.pdmaf.ui.gwt.client.components;

import com.pdmaf.ui.gwt.client.utils.Tooltip;
import com.pdmaf.ui.gwt.client.utils.TooltipFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 2:57:23 PM
 * 
 */
public class ValidatingToolTip extends PopupPanel implements Tooltip {
	public static final TooltipFactory FACTORY = new Factory();

    private static class Factory implements TooltipFactory {
		public Tooltip show(final Widget widget, String text, boolean isHTML) {
			final ValidatingToolTip toolTip = new ValidatingToolTip(text, isHTML);
			toolTip.setPopupPositionAndShow(new PositionCallback() {
				public void setPosition(int offsetWidth, int offsetHeight) {
					toolTip.setPopupPosition(
						widget.getAbsoluteLeft() + widget.getOffsetWidth() / 3,
						widget.getAbsoluteTop() + widget.getOffsetHeight() - 3);
				}});
			return toolTip;
		}
	}
	public ValidatingToolTip(String text, boolean isHTML) {
		super(true);
		FlowPanel fp = new FlowPanel();
		FlowPanel topPanel = new FlowPanel();
		topPanel.addStyleName("pdmaf-ToolTip-top");
		topPanel.add(new HTML("&nbsp;"));
		fp.add(topPanel);
		if (isHTML) {
			HTML html = new HTML(text, true);
			fp.add(html);
			html.setStyleName("gwt-Label");
		} else
			fp.add(new Label(text, true));
		setWidget(fp);
		addStyleName("pdmaf-ToolTip");
	}
}