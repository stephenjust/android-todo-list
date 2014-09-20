package ca.stephenjust.todolist.data;

public interface ITodoListReaderWriter {
	public void write(String listName, TodoList list);
	public TodoList read(String listName);
}
