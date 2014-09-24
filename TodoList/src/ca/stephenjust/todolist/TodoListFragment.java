package ca.stephenjust.todolist;

import ca.stephenjust.todolist.data.TodoContainer;
import ca.stephenjust.todolist.data.TodoList;
import android.os.Bundle;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TodoListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<TodoList> {

	private static final String ARG_LIST = "list";
	private static final String ARG_ARCHIVE_LIST = "archive_list";

	// File names for serialized lists
	private String m_listName;
	private String m_archiveListName;

	private TodoList m_list;

	private ActionMode mActionMode;
	private MultiChoiceModeListener m_actionModeListener;

	/**
	 * Create list fragment.
	 * 
	 * If archive_list is null, then the user will not be able to archive
	 * items in that list.
	 * @param list
	 * @param archive_list
	 * @return
	 */
	public static TodoListFragment newInstance(String list, String archive_list) {
		TodoListFragment fragment = new TodoListFragment();
		Bundle args = new Bundle();
		args.putString(ARG_LIST, list);
		args.putString(ARG_ARCHIVE_LIST, archive_list);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public TodoListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			m_listName = getArguments().getString(ARG_LIST);
			m_archiveListName = getArguments().getString(ARG_ARCHIVE_LIST);
		}

		m_actionModeListener = new TodoSelectListener();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(m_actionModeListener);
		listView.clearChoices();
		setListShown(false);
		getLoaderManager().initLoader(0, null, this).forceLoad();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		finishActionMode();
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(Bundle bundle);
	}

	public TodoList getTodoList() {
		return m_list;
	}

	@Override
	public Loader<TodoList> onCreateLoader(int id, Bundle args) {
		return new TodoListLoader(getActivity(), m_listName);
	}

	@Override
	public void onLoadFinished(Loader<TodoList> loader, TodoList data) {
		m_list = data;
		Log.d("TodoLoader", "Loaded " + m_list.size() + " items.");
		TodoAdapter adapter = TodoContainer.getInstance().getAdapter(getActivity(), m_listName);
		setListAdapter(adapter);
		
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
        
	}

	@Override
	public void onLoaderReset(Loader<TodoList> loader) {
		setListAdapter(null);
	}
	
	public void finishActionMode() {
		if (mActionMode != null) {
			mActionMode.finish();
		}
	}
	
	private static class TodoListLoader extends AsyncTaskLoader<TodoList> {

		Context mContext;
		String mListFile;
		
		public TodoListLoader(Context context, String listFile) {
			super(context);
			mContext = context;
			mListFile = listFile;
		}

		@Override
		public TodoList loadInBackground() {
			return TodoContainer.getInstance().getList(mContext, mListFile);
		}

	}
	
	class TodoSelectListener implements MultiChoiceModeListener {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			if (mActionMode != null) return false;
			mActionMode = mode;
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.todo_context_menu, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			SparseBooleanArray selectedItems = getListView().getCheckedItemPositions();
			switch (item.getItemId()) {
				case R.id.item_delete:
					m_list.deleteItems(selectedItems);
					TodoContainer.getInstance().saveLists(getActivity());
					mode.finish();
					break;
				case R.id.item_email:
					TodoList list = m_list.getSubList(selectedItems);
					TodoEmailer e = new TodoEmailer(getActivity(), list);
					e.send();
					mode.finish();
					break;
				case R.id.item_archive:
					TodoList targetList = TodoContainer.getInstance().getList(getActivity(), m_archiveListName);
					m_list.moveItems(selectedItems, targetList);
					TodoContainer.getInstance().saveLists(getActivity());
					mode.finish();
					break;
				default:
					return false;
			}
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
		}
		
	}
}
