package ca.stephenjust.todolist.data;

import java.util.HashMap;
import java.util.Set;

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

	private TodoContainer() {
		mLists = new HashMap<String, TodoList>();
	}
	
	public static TodoContainer getInstance() {
		if (mInstance != null) return mInstance;
		return new TodoContainer();
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
		}
	}

}
