/*
 * SortableTable Widget for GWT library of Google, Inc.
 * 
 * Copyright (c) 2006 Parvinder Thapar
 * http://psthapar.googlepages.com/
 * 
 * This library is free software; you can redistribute 
 * it and/or modify it under the terms of the GNU Lesser 
 * General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or 
 * (at your option) any later version. This library is 
 * distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY  or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNULesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General 
 * PublicLicense along with this library; if not, write to the 
 * Free Software Foundation, Inc.,  
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA  
 */

package com.pdmaf.ui.gwt.client.table;

import java.util.ArrayList;
import java.util.List;

/*
 * RowData defines one row in a Sortable Table
 */
public class RowData implements Comparable {
	
	// Maintains the list of the columns in the table
	List columnValues = new ArrayList();
	
	// Keeps the current column index being sorted
	int sortColIndex = 0;

	/*
	 * addColumnValue
	 * 
	 * Adds the Comparable Value in the List of columns
	 * 
	 * @param Comparable
	 */
	public void addColumnValue(Comparable value){
		this.columnValues.add(value);
	}
	
	/*
	 * addColumnValue
	 * 
	 * Adds the Comparable Value in the specific index in the
	 * List of columns
	 * 
	 * @param colIndex (int)
	 * @param Comparable
	 */
	public void addColumnValue(int index, Comparable value){
		if(index >= this.columnValues.size()){
			addNullColumns(index);
		}
		this.columnValues.set(index, value);
	}	

	/*
	 * getColumnValue
	 * 
	 * Retrieves the Comparable Object from the List of columns
	 * 
	 * @param colIndex (int)
	 * @return Object
	 */
	public Object getColumnValue(int index){
		return this.columnValues.get(index);
	}	
	
	/*
	 * addColumnValues
	 * 
	 * Retrieves the list of column values
	 * 
	 * @return List
	 */
	public List getColumnValues() {
		return columnValues;
	}

	/*
	 * setColumnValues
	 * 
	 * Sets the List to the List of column values
	 * 
	 * @param List
	 */
	public void setColumnValues(List columnValues) {
		this.columnValues = columnValues;
	}

	/*
	 * getSortColIndex
	 * 
	 * Returns the current column index being sorted
	 * 
	 * @return colIndex (int)
	 */
	public int getSortColIndex() {
		return sortColIndex;
	}

	/*
	 * setSortColIndex
	 * 
	 * Sets the current column index being sorted
	 * 
	 * @param colIndex (int)
	 */
	public void setSortColIndex(int sortColIndex) {
		this.sortColIndex = sortColIndex;
	}

	/*
	 * compareTo
	 * 
	 * Implementation of Interface Comparable 
	 * Returns the compare result to another RowData object
	 * 
	 * @param colIndex (int)
	 */
	public int compareTo(Object other) {
		if(null == other){
			return -1;
		}
		RowData otherRow = (RowData)other;
		Comparable obj1 = (Comparable)this.getColumnValue(this.sortColIndex);
		Comparable obj2 = (Comparable)otherRow.getColumnValue(this.sortColIndex);
		return obj1.compareTo(obj2);
	}
	
	/*
	 * addNullColumns
	 * 
	 * Adds the Null columns in the table row
	 *  
	 * @param colIndex (int)
	 * @deprecated
	 */
	private void addNullColumns(int index){
		for(int nullIndex=this.columnValues.size(); nullIndex<=index; nullIndex++){
			columnValues.add(null);
		}
	}
}
