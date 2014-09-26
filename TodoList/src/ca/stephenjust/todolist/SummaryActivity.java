package ca.stephenjust.todolist;

import ca.stephenjust.todolist.data.TodoContainer;
import ca.stephenjust.todolist.data.TodoList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity to displaly summary of checked and unchecked items.
 * @author Stephen Just
 *
 */
public class SummaryActivity extends Activity {

	TextView mCurrentSummaryText;
	TextView mArchiveSummaryText;
	TodoContainer mTodoContainer;
	TodoList mCurrentList;
	TodoList mArchiveList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);

		// Populate instance variables.
		mCurrentSummaryText = (TextView) findViewById(R.id.summary_current);
		mArchiveSummaryText = (TextView) findViewById(R.id.summary_archived);
		mTodoContainer = TodoContainer.getInstance();
	}

	/**
	 * When the activity is started, update the visible summary.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		loadLists();
		updateSummary();
	}

	/**
	 * Populate instance variables with lists for shorter reference.
	 */
	private void loadLists() {
		mCurrentList = mTodoContainer.getList(getApplication(), TodoContainer.TODO_CURRENT);
		mArchiveList = mTodoContainer.getList(getApplication(), TodoContainer.TODO_ARCHIVE);
	}

	/**
	 * Set the activity text to display the summary of each of current and archived items.
	 */
	private void updateSummary() {
		mCurrentSummaryText.setText(String.format(getString(R.string.summary_text), mCurrentList.numChecked(),
				mCurrentList.numUnchecked(), mCurrentList.size()));
		mArchiveSummaryText.setText(String.format(getString(R.string.summary_text), mArchiveList.numChecked(),
				mArchiveList.numUnchecked(), mArchiveList.size()));
	}
}
