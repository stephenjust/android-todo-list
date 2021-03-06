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

import java.util.HashMap;
import java.util.Set;

import ca.stephenjust.todolist.TodoAdapter;

import android.content.Context;

/**
 * Singleton class to manage all to-do lists across the application.
 * @author Stephen Just
 *
 */
public class TodoContainer {

	static TodoContainer mInstance = null;

	public static final String TODO_CURRENT = "todolist.ser";
	public static final String TODO_ARCHIVE = "archive.ser";
	
	private HashMap<String, TodoList> mLists;
	private HashMap<String, TodoAdapter> mAdapters;

	/**
	 * Private constructor
	 */
	private TodoContainer() {
		mLists = new HashMap<String, TodoList>();
		mAdapters = new HashMap<String, TodoAdapter>();
	}
	
	public static TodoContainer getInstance() {
		if (mInstance != null) return mInstance;
		mInstance = new TodoContainer();
		return mInstance;
	}

	/**
	 * Get a list adapter
	 * @param context
	 * @param name list name
	 * @return
	 */
	public TodoAdapter getAdapter(Context context, String name) {
		if (mAdapters.containsKey(name)) {
			if (mAdapters.get(name) == null) {
				mAdapters.put(name, new TodoAdapter(context, getList(context, name)));
			}
			return mAdapters.get(name);
		} else {
			mAdapters.put(name, new TodoAdapter(context, getList(context, name)));
			return mAdapters.get(name);
		}
	}

	/**
	 * Get a list of items
	 * @param context
	 * @param name list name
	 * @return
	 */
	public TodoList getList(Context context, String name) {
		if (mLists.containsKey(name)) {
			if (mLists.get(name) == null) {
				mLists.put(name, new TodoList());
			}
			return mLists.get(name);
		} else {
			mLists.put(name, loadList(context, name));
			return mLists.get(name);
		}
	}

	/**
	 * Load a list from the saved state
	 * @param context
	 * @param name
	 * @return
	 */
	private TodoList loadList(Context context, String name) {
		ITodoListReaderWriter rw = new SerializedTodoListReaderWriter(context);
		return rw.read(name);
	}

	/**
	 * Save all lists
	 * @param context
	 */
	public void saveLists(Context context) {
		ITodoListReaderWriter rw = new SerializedTodoListReaderWriter(context);
		Set<String> keys = mLists.keySet();
		for (String key: keys) {
			rw.write(key, mLists.get(key));
			TodoAdapter adapter = mAdapters.get(key);
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		}
	}

}
