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
