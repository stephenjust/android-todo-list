package ca.stephenjust.todolist;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class TodoListActivity extends Activity implements TodoListFragment.OnFragmentInteractionListener, TodoEditFragment.OnFragmentInteractionListener {

	TodoListFragment m_fragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		if (savedInstanceState == null) {
			m_fragment = TodoListFragment.newInstance("todolist.ser", "archive.ser");
			getFragmentManager().beginTransaction()
					.add(R.id.container, m_fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list, menu);
		return true;
	}

	private void addTodoItem() {
		DialogFragment dlg = TodoEditFragment.newInstance("");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		dlg.show(ft, "dialog");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			addTodoItem();
			return true;
		} else if (id == R.id.action_about) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentInteraction(String text) {
	    return;
	}

	@Override
	public void onFragmentInteraction(Bundle bundle) {
		String fragment = bundle.getString("fragment");
		if (fragment.equals("edit")) {
			TodoList list = m_fragment.getTodoList();
			if (list != null) {
				try {
					list.add(new TodoItem(bundle.getString("value")));
					TodoAdapter la = (TodoAdapter) m_fragment.getListAdapter();
					la.notifyDataSetChanged();
				} catch (IllegalArgumentException ex) {
					Toast.makeText(this, R.string.invalid_todo_text, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
