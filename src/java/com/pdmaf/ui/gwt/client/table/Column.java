package com.pdmaf.ui.gwt.client.table;

/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Column to be added as a header to the table. If a property is provided, the
 * column will be sortable. The property can be any you will use to sort the
 * results when results from a new page are retrieved.
 *
 * @author Joe Toth (joetoth@gmail.com)
 *
 */
public class Column {

	String title;

	String parameter;

	boolean sortable;

	/**
	 * Use this constructor if you do not want the column to be sortable.
	 *
	 * @param title
	 */
	public Column(String title) {
		this.title = title;
	}

	/**
	 * Use this constructor if you want the column to be sortable.
	 *
	 * @param title
	 *            Column Header
	 * @param parameter
	 *            The underlying parameter is used to represent this column in
	 *            JDBC / EJB / Hibernate / etc. It can also be used to store and
	 *            send any extra data needed to perform the sort.
	 */
	public Column(String title, String parameter) {
		this(title);
		this.parameter = parameter;
		this.sortable = true;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
		this.sortable = true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSortable() {
		return sortable;
	}

}