package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 1:32:34 PM
 *
 */
public class CustomTextArea extends TextBoxBase {
    
	private CustomTextArea(Element element) {
		super(element);
		TextAreaElement.as(element);
//		setStyleName("mom-TextArea");
	}
  /**
   * Creates a MomTextArea widget that wraps an existing &lt;textarea&gt;
   * element.
   *
   * This element must already be attached to the document. If the element is
   * removed from the document, you must call
   * {@link RootPanel#detachNow(Widget)}.
   *
   * @param element the element to be wrapped
   */
	public static CustomTextArea wrap(Element element) {
		// Assert that the element is attached.
		assert Document.get().getBody().isOrHasChild(element);

		CustomTextArea textArea = new CustomTextArea(element);

	    textArea.onAttach();
	    RootPanel.detachOnWindowClose(textArea);

	    return textArea;
	}
}
