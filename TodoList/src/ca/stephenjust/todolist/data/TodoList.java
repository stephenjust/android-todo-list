package ca.stephenjust.todolist.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * List of To-Do Items
 * @author Stephen Just
 *
 * This list represents any list of to-do items.
 */
public class TodoList extends ArrayList<TodoItem> implements Serializable {

	private static final long serialVersionUID = 1678410371420859707L;

	public TodoList() {
		super();
	}

	public TodoList(int capacity) {
		super(capacity);
	}

	public TodoList(Collection<? extends TodoItem> collection) {
		super(collection);
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

	@Override
	public String toString() {
		String s = "";
		for (TodoItem item: this) {
			s += item.toString() + "\n";
		}
		return s;
	}

}
