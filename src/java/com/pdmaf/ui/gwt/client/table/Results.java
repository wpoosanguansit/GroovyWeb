package com.pdmaf.ui.gwt.client.table;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 27, 2009
 * Time: 12:02:33 PM
 * This is to capture the list coming back from the server
 */

public class Results<T extends JavaScriptObject> {

    private int size;

    private List<T> list;

    public Results() {

    }

    public Results(int size, List<T> list) {
        this.size = size;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
