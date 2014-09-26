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

import ca.stephenjust.todolist.data.TodoContainer;
import ca.stephenjust.todolist.data.TodoItem;
import ca.stephenjust.todolist.data.TodoList;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewSwitcher;


/**
 * Activity for main view.
 * @author Stephen Just
 *
 * This is an activity with a set of tabs allowing the user to toggle between
 * lists of items.
 */
public class TodoListActivity extends Activity implements TodoListFragment.OnFragmentInteractionListener, TodoEditFragment.OnFragmentInteractionListener {

	TodoContainer mTodoContainer = null;
	
	TodoListFragment m_fragment = null;
	TodoListFragment m_fragment_archived = null;
	ViewSwitcher mViewSwitcher = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTodoContainer = TodoContainer.getInstance();
		setupTabs();

		// Set-up fragments.
		setContentView(R.layout.activity_todo_list);
		if (savedInstanceState == null) {
			m_fragment = TodoListFragment.newInstance(TodoContainer.TODO_CURRENT, TodoContainer.TODO_ARCHIVE);
			m_fragment_archived = TodoListFragment.newInstance(TodoContainer.TODO_ARCHIVE, TodoContainer.TODO_CURRENT);
			getFragmentManager().beginTransaction()
					.add(R.id.container, m_fragment, "MAIN_FRAGMENT")
					.add(R.id.container_archived, m_fragment_archived).commit();
			mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
		}
	}
	
	private void setupTabs() {
		// Add tab panel
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Create listener to handle tab actions
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	        	if (mViewSwitcher != null) {
	        		mViewSwitcher.setDisplayedChild(tab.getPosition());
	        	}
	        	// Hack to force selections to end when switching tabs.
	        	// There is likely a more flexible way to do this, but if it ain't broke...
	        	if (m_fragment != null) {
	        		m_fragment.finishActionMode();
	        	}
	        	if (m_fragment_archived != null) {
	        		m_fragment_archived.finishActionMode();
	        	}
	        }

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	        }
	    };

	    // Create tabs
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

	@Override
	protected void onPause() {
		super.onPause();
		// Save lists when activity is paused.
		mTodoContainer.saveLists(getApplication());
	}

	/**
	 * Create "add item" dialog.
	 */
	private void addTodoItem() {
		DialogFragment dlg = TodoEditFragment.newInstance("");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		dlg.show(ft, "dialog");
		mTodoContainer.saveLists(this);
	}

	/**
	 * Open SummaryActivity
	 */
	private void launchSummary() {
		Intent intent = new Intent(this, SummaryActivity.class);
		startActivity(intent);
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
		} else if (id == R.id.action_summary) {
			launchSummary();
			return true;
		} else if (id == R.id.action_email_all) {
			// Create a list of all items to send. A bit of a hack, but it works.
			TodoList current = mTodoContainer.getList(this, TodoContainer.TODO_CURRENT);
			TodoList archived = mTodoContainer.getList(this, TodoContainer.TODO_ARCHIVE);
			TodoList emailList = new TodoList();
			emailList.addAll(current);
			emailList.addAll(archived);
			TodoEmailer e = new TodoEmailer(this, emailList);
			e.send();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Handle responses from fragments.
	 * Only the new item fragment is specially handled here.
	 */
	@Override
	public void onFragmentInteraction(Bundle bundle) {
		String fragment = bundle.getString("fragment");
		if (m_fragment == null) {
			m_fragment = (TodoListFragment) getFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
		}
		if (fragment.equals("edit")) {
			TodoList list = m_fragment.getTodoList();
			if (list != null) {
				try {
					list.add(new TodoItem(bundle.getString("value")));
					TodoAdapter la = (TodoAdapter) m_fragment.getListAdapter();
					la.notifyDataSetChanged();
					Toast.makeText(this, R.string.todo_created, Toast.LENGTH_SHORT).show();
				} catch (IllegalArgumentException ex) {
					Toast.makeText(this, R.string.invalid_todo_text, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
