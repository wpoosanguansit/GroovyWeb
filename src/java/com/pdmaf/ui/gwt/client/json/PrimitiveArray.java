package com.pdmaf.ui.gwt.client.json;

import java.util.Comparator;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:00:08 PM
 */

public class PrimitiveArray<E> extends JavaScriptObject {
	protected PrimitiveArray() {}
	public final native int length() /*-{ return this.length; }-*/;
	public final native E get(int i) /*-{ return this[i]; }-*/;
	public final native void push(E element)/*-{ this.push(element); }-*/;
	public static <T> int binarySearch(PrimitiveArray<T> array, T key, Comparator<T> comparator) {
		return binarySearch(array, key, comparator, 0, array.length() - 1);
	}
	private static <T> int binarySearch(PrimitiveArray<T> array, T key, Comparator<T> comparator, int min, int max) {
		while (min < max) {
			int mid = (min + max) / 2;
			if (comparator.compare(key, array.get(mid)) < 0)
				max = mid;
			else if (comparator.compare(key, array.get(mid)) > 0)
				min = mid + 1;
			else
				return mid;
		}
		return -(min + 1);
	}
}
