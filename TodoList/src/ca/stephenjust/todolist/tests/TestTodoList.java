package ca.stephenjust.todolist.tests;

import ca.stephenjust.todolist.data.TodoItem;
import ca.stephenjust.todolist.data.TodoList;
import junit.framework.TestCase;

public class TestTodoList extends TestCase {

	TodoList mList;

	protected void setUp() throws Exception {
		super.setUp();
		mList = new TodoList();
		mList.add(new TodoItem("Item A"));
		mList.add(new TodoItem("Item B"));
		mList.add(new TodoItem("Item C"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSize() {
		assertEquals(3, mList.size());
		mList.remove(0);
		assertEquals(2, mList.size());
	}
	
	public void testNumChecked() {
		assertEquals(Long.valueOf(0), mList.numChecked());
		mList.get(0).setCompleted(true);
		assertEquals(Long.valueOf(1), mList.numChecked());
	}
	
	public void testNumUnchecked() {
		assertEquals(Long.valueOf(3), mList.numUnchecked());
		mList.get(0).setCompleted(true);
		assertEquals(Long.valueOf(2), mList.numUnchecked());
		mList.remove(0);
		assertEquals(Long.valueOf(2), mList.numUnchecked());
	}

}
