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
