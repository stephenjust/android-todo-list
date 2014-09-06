package ca.stephenjust.todolist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * List of To-Do Items
 * @author Stephen
 *
 * This list may represent either current or archived to-do's.
 */
public class TodoList extends ArrayList<TodoItem> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1678410371420859707L;

	public TodoList() {
		// TODO Auto-generated constructor stub
	}

	public TodoList(int capacity) {
		super(capacity);
		// TODO Auto-generated constructor stub
	}

	public TodoList(Collection<? extends TodoItem> collection) {
		super(collection);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get number of checked items
	 * @return Number of checked items
	 */
	public Long numChecked() {
		Long count = 0L;
		Iterator<TodoItem> itr = iterator();
		while(itr.hasNext()) {
			TodoItem item = itr.next();
			if (item.getCompleted()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Get number of unchecked items
	 * @return Number of unchecked items
	 */
	public Long numUnchecked() {
		return size() - numChecked();
	}

}
