package ca.stephenjust.todolist;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class TodoListActivity extends Activity {

	static TodoList m_list = null;
	TodoListFragment m_fragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		if (savedInstanceState == null) {
			m_fragment = new TodoListFragment();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			if (m_list != null) {
				m_list.add(new TodoItem("bat"));
				TodoAdapter la = (TodoAdapter) m_fragment.getListAdapter();
				la.notifyDataSetChanged();
			}
			return true;
		} else if (id == R.id.action_about) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class TodoListFragment extends ListFragment {
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			m_list = new TodoList();
			m_list.add(new TodoItem("Foo"));
			m_list.add(new TodoItem("Bar"));
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			
			setListAdapter(new TodoAdapter(getActivity(), m_list));
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();
			
			setListAdapter(null);
		}
	}
}
