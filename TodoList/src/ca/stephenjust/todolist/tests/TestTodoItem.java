package ca.stephenjust.todolist.tests;

import ca.stephenjust.todolist.data.TodoItem;
import junit.framework.TestCase;

public class TestTodoItem extends TestCase {

	TodoItem mItem;

	protected void setUp() throws Exception {
		super.setUp();
		mItem = new TodoItem("Sample item.");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testInitialize() {
		new TodoItem("Test");
	}
	
	public void testInitialize_invalidText() {
		try {
			new TodoItem(null);
			fail("Should have caught exception.");
		} catch (IllegalArgumentException ex) {
			// pass!
		}
		try {
			new TodoItem("");
			fail("Should have caught exception.");
		} catch (IllegalArgumentException ex) {
			// pass!
		}
	}
	
	public void testToString() {
		assertEquals("[ ] Sample item.", mItem.toString());
		mItem.setCompleted(true);
		assertEquals("[x] Sample item.", mItem.toString());
	}

}
