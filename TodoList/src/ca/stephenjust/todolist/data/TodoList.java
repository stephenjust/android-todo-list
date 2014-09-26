/* The MIT License (MIT)
 * 
 * Copyright (c) 2014 Stephen Just
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ca.stephenjust.todolist.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.util.SparseBooleanArray;


/**
 * List of To-Do Items
 * @author Stephen Just
 *
 * This list represents any list of to-do items.
 */
public class TodoList extends ArrayList<TodoItem> implements Serializable {

	private static final long serialVersionUID = 1678410371420859707L;

	public TodoList() {
		super();
	}

	public TodoList(int capacity) {
		super(capacity);
	}

	public TodoList(Collection<? extends TodoItem> collection) {
		super(collection);
	}

	/**
	 * Get number of checked items
	 * @return Number of checked items
	 */
	public Long numChecked() {
		Long count = 0L;
		Iterator<TodoItem> itr = iterator();
		while(itr.hasNext()) {
			TodoItem item = itr.next();
			if (item.getCompleted()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Get number of unchecked items
	 * @return Number of unchecked items
	 */
	public Long numUnchecked() {
		return size() - numChecked();
	}

	/**
	 * Delete list items with values of 'true'
	 * @param items
	 */
	public void deleteItems(SparseBooleanArray items) {
		for (int i = size() - 1; i >= 0; i--) {
			if (items.get(i) == true) {
				this.remove(i);
			}
		}
	}

	/**
	 * Move list items to a different list.
	 * @param items
	 * @param targetList
	 */
	public void moveItems(SparseBooleanArray items, TodoList targetList) {
		for (int i = 0; i < size(); i++) {
			if (items.get(i) == true) {
				targetList.add(get(i));
			}
		}
		deleteItems(items);
	}

	/**
	 * Get sub-list of items specified by SparseBooleanArray.
	 * @param items
	 * @return
	 */
	public TodoList getSubList(SparseBooleanArray items) {
		TodoList list = new TodoList();
		for (int i = 0; i < size(); i++) {
			if (items.get(i) == true) {
				list.add(this.get(i));
			}
		}
		return list;
	}

	@Override
	public String toString() {
		String s = "";
		for (TodoItem item: this) {
			s += item.toString() + "\n";
		}
		return s;
	}

}
