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


public class TodoEditFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_ITEM = "todo_item";

	// TODO: Rename and change types of parameters
	private String m_item;

	private OnFragmentInteractionListener mListener;

	private EditText m_editText;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TodoEditFragment.
	 */
	// TODO: Rename and change types and number of parameters
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
		if (getArguments() != null) {
			m_item = getArguments().getString(ARG_ITEM);
		}
		setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_todo_edit, container,
				false);
		m_editText = (EditText) view.findViewById(R.id.editItemText);
		m_editText.setText(m_item);
		Button b = (Button) view.findViewById(R.id.editItemSave);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					Bundle bundle = new Bundle();
					bundle.putString("fragment", "edit");
					bundle.putString("value", m_editText.getText().toString());
					mListener.onFragmentInteraction(bundle);
				}
				dismiss();
			}
			
		});
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
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
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(Bundle bundle);
	}

}
