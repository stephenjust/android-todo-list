package ca.stephenjust.todolist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
		check.setChecked(item.getCompleted());
		check.setText(item.getText());
		text.setText(item.getText());
		text.setVisibility(View.GONE);
		
		return rowView;
	}


}
