package ca.stephenjust.todolist.data;

/**
 * Interface for TodoList readers/writers
 * @author Stephen Just
 *
 */
public interface ITodoListReaderWriter {
	public void write(String listName, TodoList list);
	public TodoList read(String listName);
}
