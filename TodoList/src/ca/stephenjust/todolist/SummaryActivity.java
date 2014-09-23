package ca.stephenjust.todolist;

import ca.stephenjust.todolist.data.ITodoListReaderWriter;
import ca.stephenjust.todolist.data.SerializedTodoListReaderWriter;
import ca.stephenjust.todolist.data.TodoList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	TextView mCurrentSummaryText;
	TextView mArchiveSummaryText;
	ITodoListReaderWriter mTodoReader;
	TodoList mCurrentList;
	TodoList mArchiveList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		mCurrentSummaryText = (TextView) findViewById(R.id.summary_current);
		mArchiveSummaryText = (TextView) findViewById(R.id.summary_archived);
		mTodoReader = new SerializedTodoListReaderWriter(this.getApplication());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loadLists();
		updateSummary();
	}

	private void loadLists() {
		mCurrentList = mTodoReader.read(TodoListActivity.TODO_CURRENT_FILE);
		mArchiveList = mTodoReader.read(TodoListActivity.TODO_ARCHIVE_FILE);
	}
	
	private void updateSummary() {
		mCurrentSummaryText.setText(String.format(getString(R.string.summary_text), mCurrentList.numChecked(),
				mCurrentList.numUnchecked(), mCurrentList.size()));
		mArchiveSummaryText.setText(String.format(getString(R.string.summary_text), mArchiveList.numChecked(),
				mArchiveList.numUnchecked(), mArchiveList.size()));
	}
}
