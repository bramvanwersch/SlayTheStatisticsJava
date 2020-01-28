package tests;

import java.util.Arrays;
import java.util.Set;


import Utilities.ColumnTypePreferences;
import junit.framework.TestCase;

public class ColumnTypePreferencesTest extends TestCase {
	
	public void testAddPreference() {
		ColumnTypePreferences ctp = new ColumnTypePreferences();
		ctp.addPreference(Integer.class, 1,2,3,4);
		assertEquals(ctp.getIndexes(Integer.class).toString(), "[1, 2, 3, 4]");
	}
	
	public void testAddPreferenceFailed1() {
		ColumnTypePreferences ctp = new ColumnTypePreferences();
		ctp.addPreference(Integer.class, 1,2,3,4);
		try {
			ctp.addPreference(Double.class, 1);
			fail("IllegalArgumentExceptionExpected");
		}catch (IllegalArgumentException e) {
			assertEquals("Cannot add column 1 as preference 1 already has a preference for class java.lang.Integer",
					e.getMessage());
		}
	}
	
	public void testAddPreferenceFailed2() {
		ColumnTypePreferences ctp = new ColumnTypePreferences();
		try {
			ctp.addPreference(this.getClass(), 1,2,3,4);
			fail("IllegalArgumentExceptionExpected");
		}catch (IllegalArgumentException e) {
			assertEquals("Invalid type as preference class tests.ColumnTypePreferencesTest. It should"
					+ " be one of the following: [class java.lang.Integer, class java.lang.Double, "
					+ "class java.lang.Object, class java.lang.String]",
					e.getMessage());
		}
	}

}
