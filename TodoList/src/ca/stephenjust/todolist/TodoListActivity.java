package ca.stephenjust.todolist;

import ca.stephenjust.todolist.data.TodoItem;
import ca.stephenjust.todolist.data.TodoList;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class TodoListActivity extends Activity implements TodoListFragment.OnFragmentInteractionListener, TodoEditFragment.OnFragmentInteractionListener {

	TodoListFragment m_fragment = null;
	TodoListFragment m_fragment_archived = null;
	ViewSwitcher mViewSwitcher = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupTabs();

		setContentView(R.layout.activity_todo_list);
		if (savedInstanceState == null) {
			m_fragment = TodoListFragment.newInstance("todolist.ser", "archive.ser");
			m_fragment_archived = TodoListFragment.newInstance("archive.ser", null);
			getFragmentManager().beginTransaction()
					.add(R.id.container, m_fragment)
					.add(R.id.container_archived, m_fragment_archived).commit();
			mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
		}
	}
	
	public void setupTabs() {
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	        	if (mViewSwitcher != null) {
	        		mViewSwitcher.setDisplayedChild(tab.getPosition());
	        	}
	        }

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // hide the given tab
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // probably ignore this event
	        }
	    };

        actionBar.addTab(
                actionBar.newTab()
                        .setText(R.string.tab_current)
                        .setTabListener(tabListener));
        actionBar.addTab(
                actionBar.newTab()
                        .setText(R.string.tab_archived)
                        .setTabListener(tabListener));
		
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
