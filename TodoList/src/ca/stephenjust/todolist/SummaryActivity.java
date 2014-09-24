package ca.stephenjust.todolist;

import ca.stephenjust.todolist.data.TodoContainer;
import ca.stephenjust.todolist.data.TodoList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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
		
		mCurrentSummaryText = (TextView) findViewById(R.id.summary_current);
		mArchiveSummaryText = (TextView) findViewById(R.id.summary_archived);
		mTodoContainer = TodoContainer.getInstance();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loadLists();
		updateSummary();
	}

	private void loadLists() {
		mCurrentList = mTodoContainer.getList(getApplication(), TodoContainer.TODO_CURRENT);
		mArchiveList = mTodoContainer.getList(getApplication(), TodoContainer.TODO_ARCHIVE);
	}
	
	private void updateSummary() {
		mCurrentSummaryText.setText(String.format(getString(R.string.summary_text), mCurrentList.numChecked(),
				mCurrentList.numUnchecked(), mCurrentList.size()));
		mArchiveSummaryText.setText(String.format(getString(R.string.summary_text), mArchiveList.numChecked(),
				mArchiveList.numUnchecked(), mArchiveList.size()));
	}
}
