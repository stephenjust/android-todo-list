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
package ca.stephenjust.todolist;

import java.util.List;

import ca.stephenjust.todolist.data.TodoItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * Adapter between TodoList and ListView.
 * @author Stephen Just
 *
 * Provides a view with a check-box and the label of the corresponding to-do item.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {

	final Context mContext;
	final List<TodoItem> mItems;

	/**
	 * Constructor
	 * @param context
	 * @param objects
	 */
	public TodoAdapter(Context context, List<TodoItem> objects) {
		super(context, R.layout.list_todo, R.id.todo_label, objects);
		mContext = context;
		mItems = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		// Check if the recycled view is available. If not, inflate from XML.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_todo, parent, false);
		}
		// Get view elements.
		CheckBox check = (CheckBox) v.findViewById(R.id.todo_check);
		TextView text = (TextView) v.findViewById(R.id.todo_label);

		// Populate view.
		TodoItem item = mItems.get(position);
		check.setOnCheckedChangeListener(new TodoCheckboxListener(position));
		check.setChecked(item.getCompleted());
		text.setText(item.getText());

		return v;
	}

	/**
	 * Listener to item state when checkbox is clicked.
	 * @author Stephen Just
	 */
	private class TodoCheckboxListener implements OnCheckedChangeListener {

		int mPosition;

		public TodoCheckboxListener(int position) {
			mPosition = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			mItems.get(mPosition).setCompleted(isChecked);
		}
	}
}
