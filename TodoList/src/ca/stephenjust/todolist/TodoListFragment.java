package ca.stephenjust.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.view.View;
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

	private OnFragmentInteractionListener mListener;

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
		// TODO: Serialize data here.
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
		return new TodoListLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<TodoList> loader, TodoList data) {
		m_list = data;
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

		public TodoListLoader(Context context) {
			super(context);
		}

		@Override
		public TodoList loadInBackground() {
			TodoList list = new TodoList();
			list.add(new TodoItem("Foo"));
			list.add(new TodoItem("Bar"));
			return list;
		}

	}
	
}
