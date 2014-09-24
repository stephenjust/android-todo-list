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

	private TodoContainer() {
		mLists = new HashMap<String, TodoList>();
		mAdapters = new HashMap<String, TodoAdapter>();
	}
	
	public static TodoContainer getInstance() {
		if (mInstance != null) return mInstance;
		mInstance = new TodoContainer();
		return mInstance;
	}

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
	
	private TodoList loadList(Context context, String name) {
		ITodoListReaderWriter rw = new SerializedTodoListReaderWriter(context);
		return rw.read(name);
	}
	
	public void saveLists(Context context) {
		ITodoListReaderWriter rw = new SerializedTodoListReaderWriter(context);
		Set<String> keys = mLists.keySet();
		for (String key: keys) {
			rw.write(key, mLists.get(key));
			mAdapters.get(key).notifyDataSetChanged();
		}
	}

}
