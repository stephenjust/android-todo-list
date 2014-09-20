package ca.stephenjust.todolist.data;

import java.io.Serializable;

/**
 * Model for a to-do item.
 * @author Stephen Just
 *
 */
public class TodoItem implements Serializable {

	String m_text;
	Boolean m_completed;

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
		String trimmedText = text.trim();
		if (trimmedText.length() == 0) {
			throw new IllegalArgumentException("Text may not be empty.");
		}
		m_text = trimmedText;
	}
	
	/**
	 * Get a to-do's text
	 * @return To-do's text
	 */
	public String getText() {
		return m_text;
	}

	/**
	 * Mark a to-do as completed
	 * @param completed
	 */
	public void setCompleted(Boolean completed) {
		m_completed = completed;
	}
	
	/**
	 * Get a to-do's completion status
	 * @return Completion status
	 */
	public Boolean getCompleted() {
		return m_completed;
	}
	
	@Override
	public String toString() {
		if (getCompleted()) {
			return "[x] " + getText();
		} else {
			return "[ ] " + getText();
		}
	}
	
}
