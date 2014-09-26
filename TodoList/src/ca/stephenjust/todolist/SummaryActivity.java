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
