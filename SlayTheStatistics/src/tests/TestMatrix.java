package tests;

import java.util.Arrays;

import Utilities.Matrix;
import junit.framework.TestCase;

public class TestMatrix extends TestCase {
	
	public void testAddColumn() {
		Matrix m = new Matrix(0,0);
		try {
			m.addColumn(new String[] {});
			fail("Expected an IndexOutOfBoundsException");
		}catch(IndexOutOfBoundsException e) {
			assertEquals("Tried to add one column to many max column size is 0", e.getMessage());
		}
	}
	
	public void testAddColumn2() {
		Matrix m = new Matrix(1,0);
		try {
			m.addColumn(new String[] {"1"});
			fail("Expected an IndexOutOfBoundsException");
		}catch(IndexOutOfBoundsException e) {
			assertEquals("Column data is to long or to short. Expected lenght is 0 got 1", e.getMessage());
		}
	}
	
	public void testAddData() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		String x = m.get(0,0);
		String[] xl = m.get(0);
		assertEquals(x, "1");
		assertEquals(Arrays.toString(xl), Arrays.toString(new String[] {"1"}));
	}
	
	
}

