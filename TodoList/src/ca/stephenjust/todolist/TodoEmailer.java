package ca.stephenjust.todolist;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import ca.stephenjust.todolist.data.TodoList;

public class TodoEmailer {

	private Context mContext;
	private TodoList mItems;

	/**
	 * Constructor
	 * @param context
	 * @param items
	 */
	public TodoEmailer(Context context, TodoList items) {
		mContext = context;
		mItems = items;
	}

	/**
	 * Send an "email" intent containing the to-do list.
	 */
	public void send() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.email_subject));
		i.putExtra(Intent.EXTRA_TEXT, mItems.toString());
		try {
		    mContext.startActivity(
		    		Intent.createChooser(i, mContext.getString(R.string.email_dialog_title)));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(mContext, R.string.error_no_email, Toast.LENGTH_SHORT).show();
		}
	}
}
