package ca.stephenjust.todolist.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


import android.content.Context;
import android.util.Log;

/**
 * Implementation of TodoListReaderWriter, where data is stored as serialized objects
 * @author Stephen
 *
 */
public class SerializedTodoListReaderWriter implements ITodoListReaderWriter {

	Context mContext;

	public SerializedTodoListReaderWriter(Context context) {
		mContext = context;
	}

	/**
	 * Serialize a TodoList
	 */
	public void write(String listName, TodoList list) {
		OutputStream os;
		ObjectOutput oo;
		try {
			os = new BufferedOutputStream(mContext.openFileOutput(listName, Context.MODE_PRIVATE));
			oo = new ObjectOutputStream(os);
			try {
				oo.writeObject(list);
				Log.d("writeTodoList", "Saved list to file " + listName);
			} finally {
				oo.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserialize a TodoList
	 */
	public TodoList read(String listName) {
		InputStream is;
		ObjectInput oi;
		try {
			try {
				is = new BufferedInputStream(mContext.openFileInput(listName));
			} catch (FileNotFoundException e) {
				Log.d("readTodoList", "No list found. Returning new one.");
				return new TodoList();
			}
			oi = new ObjectInputStream(is);
			try {
				TodoList list = (TodoList) oi.readObject();
				if (list == null) {
					Log.e("readTodoList", "Loaded list was null. Returning new list.");
					return new TodoList();
				} else {
					return list;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				oi.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e("readTodoList", "Failed to load TodoList");
		return null;
	}
}
