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
package ca.stephenjust.todolist.data;

import java.io.Serializable;

/**
 * Model for a to-do item.
 * @author Stephen Just
 *
 */
public class TodoItem implements Serializable {

	String mText;
	Boolean mCompleted;

	private static final long serialVersionUID = 2256913946973350575L;

	public TodoItem(String text) {
		setText(text);
		setCompleted(Boolean.FALSE);
	}

	/**
	 * Set a to-do's text
	 * @param text To-do text
	 */
	public void setText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text may not be null.");
		}
		String trimmedText = text.trim();
		if (trimmedText.length() == 0) {
			throw new IllegalArgumentException("Text may not be empty.");
		}
		mText = trimmedText;
	}

	/**
	 * Get a to-do's text
	 * @return To-do's text
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Mark a to-do as completed
	 * @param completed
	 */
	public void setCompleted(Boolean completed) {
		mCompleted = completed;
	}

	/**
	 * Get a to-do's completion status
	 * @return Completion status
	 */
	public Boolean getCompleted() {
		return mCompleted;
	}

	@Override
	public String toString() {
		if (getCompleted()) {
			return "[x] " + getText();
		} else {
			return "[ ] " + getText();
		}
	}

	@Override
	public int hashCode() {
		return getText().hashCode() ^ (31 * getCompleted().hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (hashCode() != obj.hashCode()) return false;
		if (!(obj instanceof TodoItem)) return false;
		TodoItem item = (TodoItem) obj;
		return getText().equals(item.getText()) &&
				getCompleted().equals(item.getCompleted());
	}

}
