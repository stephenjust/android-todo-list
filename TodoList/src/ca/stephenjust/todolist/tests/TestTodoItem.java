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
	
	public void testEquals() {
		TodoItem itemA = new TodoItem("Item A");
		TodoItem itemB = new TodoItem("Item B");
		TodoItem itemB_checked = new TodoItem("Item B");
		itemB_checked.setCompleted(true);
		assertFalse(itemA.equals(itemB));
		assertFalse(itemB.equals(itemA));
		assertFalse(itemB.equals(itemB_checked));
		assertTrue(itemB.equals(itemB));
	}
	
	public void testToString() {
		assertEquals("[ ] Sample item.", mItem.toString());
		mItem.setCompleted(true);
		assertEquals("[x] Sample item.", mItem.toString());
	}

}
