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

public class TodoAdapter extends ArrayAdapter<TodoItem> {

	final Context mContext;
	final List<TodoItem> mItems;
	
	public TodoAdapter(Context context, List<TodoItem> objects) {
		super(context, R.layout.list_todo, objects);
		mContext = context;
		mItems = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView != null) {
			rowView = convertView;
		} else {
			rowView = inflater.inflate(R.layout.list_todo,  parent, false);
		}
		CheckBox check = (CheckBox) rowView.findViewById(R.id.check_todo);
		
		TodoItem item = mItems.get(position);
		check.setOnCheckedChangeListener(new TodoCheckboxListener(position));
		check.setChecked(item.getCompleted());
		check.setText(item.getText());
		
		return rowView;
	}

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
