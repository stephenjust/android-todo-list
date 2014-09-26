cmput301-assign1
================

TODO List Android Application by Stephen Just

This project makes use of the [Action Bar Icon Pack](https://developer.android.com/design/downloads/index.html), CC-BY 2.5

Some code has been adapted from the [Android API docs](http://developer.android.com/guide/index.html),
whose source code is available under the Apache 2.0 License.

Usage
-----

The application launches with two empty lists of to-do items.
Add a new item to the list of current to-do's using the "+" button.
Click the checkbox on an item to mark it as complete or incomplete.
Tap and hold an item to select it. Multiple items may be selected with
additional taps. You may delete, email, or move to-do items.
Click the "Summary" entry in the action bar menu to view a summary of
your items.

Limitations
-----------

Items may not be added to the "archive" list. The customer requested
the ability to add items, and it seems logical to force new items into
the current list, as one would not normally create an entry that they
want to archive immediately. Clicking the "+" button will always add
items to the "current" list regardless of which list is visible.