package ca.stephenjust.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
	private TodoListReaderWriter m_listReaderWriter;

	private OnFragmentInteractionListener mListener;
	
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
		m_listReaderWriter = new TodoListReaderWriter(getActivity().getApplication());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListShown(false);
		getLoaderManager().initLoader(0, null, this).forceLoad();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		m_listReaderWriter.write(m_listName,  m_list);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		OnItemLongClickListener clickListener = new TodoClickListener();
		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(m_actionModeListener);
		listView.setOnItemLongClickListener(clickListener);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mListener
					.onFragmentInteraction(m_list.get(position).getText());
		}
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
		// TODO: Update argument type and name
		public void onFragmentInteraction(String text);
	}

	public TodoList getTodoList() {
		return m_list;
	}

	@Override
	public Loader<TodoList> onCreateLoader(int id, Bundle args) {
		return new TodoListLoader(getActivity(), m_listName, m_listReaderWriter);
	}

	@Override
	public void onLoadFinished(Loader<TodoList> loader, TodoList data) {
		m_list = data;
		if (data == null) {
			Log.wtf("TodoLoader", "Data loaded was null??");
		}
		Log.d("TodoLoader", "Loaded " + data.size() + " items.");
		TodoAdapter adapter = new TodoAdapter(getActivity(), data);
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
	
	private static class TodoListLoader extends AsyncTaskLoader<TodoList> {

		String m_listFile;
		TodoListReaderWriter m_readerWriter;
		
		public TodoListLoader(Context context, String listFile, TodoListReaderWriter listRW) {
			super(context);
			m_listFile = listFile;
			m_readerWriter = listRW;
		}

		@Override
		public TodoList loadInBackground() {
			return m_readerWriter.read(m_listFile);
		}

	}

	class TodoClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if (mActionMode != null) {
				return false;
			}
			mActionMode = getActivity().startActionMode(m_actionModeListener);
			view.setSelected(true);
			return true;
		}
		
	}
	
	class TodoSelectListener implements MultiChoiceModeListener {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.todo_context_menu, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			Log.w("Foo", "Item " + id + " state changed.");
			// TODO Auto-generated method stub
			
		}
		
	}
	
/*	@Override
	public boolean onLongClick(View v) {
		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		TodoList selectedItems = new TodoList();
		v.setSelected(!v.isSelected());
		Log.w("TodoListFragment", "Selected view.");
		for (int i = 0; i < checked.size(); i++) {
			int position = checked.keyAt(i);
			if (checked.valueAt(i))
				selectedItems.add(m_list.get(i));
		}
		return true;
	}*/
	
}
