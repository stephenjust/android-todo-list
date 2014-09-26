package ca.stephenjust.todolist;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Dialog that prompts for the text of a to-do item.
 * @author Stephen Just
 *
 */
public class TodoEditFragment extends DialogFragment {
	// Arguments
	private static final String ARG_ITEM = "todo_item";

	private String mItem;
	private OnFragmentInteractionListener mListener;
	private EditText mEditText;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param item To-do item text, may be blank.
	 * @return A new instance of fragment TodoEditFragment.
	 */
	public static TodoEditFragment newInstance(String item) {
		TodoEditFragment fragment = new TodoEditFragment();
		Bundle args = new Bundle();
		args.putString(ARG_ITEM, item);
		fragment.setArguments(args);
		return fragment;
	}

	public TodoEditFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pre-fill text if available.
		if (getArguments() != null) {
			mItem = getArguments().getString(ARG_ITEM);
		}
		// Set the dialog box to be white with no window decorations.
		setStyle(DialogFragment.STYLE_NO_TITLE,
				android.R.style.Theme_Holo_Light_Dialog_MinWidth);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_todo_edit, container,
				false);
		mEditText = (EditText) view.findViewById(R.id.editItemText);
		mEditText.setText(mItem);
		Button b = (Button) view.findViewById(R.id.editItemSave);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					// Send text back to fragment so that item can be added to list.
					Bundle bundle = new Bundle();
					bundle.putString("fragment", "edit");
					bundle.putString("value", mEditText.getText().toString());
					mListener.onFragmentInteraction(bundle);
				}
				dismiss();
			}

		});
		
		return view;
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
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 */
	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(Bundle bundle);
	}

}
