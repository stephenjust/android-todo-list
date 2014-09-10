package ca.stephenjust.todolist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

	final Context m_context;
	final List<TodoItem> m_items;
	
	public TodoAdapter(Context context, List<TodoItem> objects) {
		super(context, R.layout.list_todo, objects);
		m_context = context;
		m_items = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;
		LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView != null) {
			rowView = convertView;
		} else {
			rowView = inflater.inflate(R.layout.list_todo,  parent, false);
		}
		CheckBox check = (CheckBox) rowView.findViewById(R.id.check_todo);
		TextView text = (TextView) rowView.findViewById(R.id.text_todo);
		
		TodoItem item = m_items.get(position);
		check.setOnCheckedChangeListener(null); // Clear listener before setting value
		check.setChecked(item.getCompleted());
		check.setOnCheckedChangeListener(new TodoCheckboxListener(position));
		check.setText(item.getText());
		text.setText(item.getText());
		text.setVisibility(View.GONE);
		
		return rowView;
	}

	private class TodoCheckboxListener implements OnCheckedChangeListener {

		int m_position;

		public TodoCheckboxListener(int position) {
			m_position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			m_items.get(m_position).setCompleted(isChecked);
		}
	}
}
