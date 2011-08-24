package com.pdmaf.ui.gwt.client.table;

import com.google.gwt.user.client.ui.Widget;

/**
 * The headerWidget and widget methods supply an 'existing widget' parameter,
 * which can be 'null' but usually its a widget supplied by that exact method
 * at an earlier time, possibly for a different row. You don't have to return it,
 * but you can; updating an existing widget is usually much faster and less heavy
 * on the browser's garbage collector compared to creating a new one. Either way,
 * it's up to you.
 */
public interface ColumnModel {
	public abstract Widget headerWidget(Widget existing);
	public abstract Widget widget(int row, Widget existing);
	public abstract int width();
}
