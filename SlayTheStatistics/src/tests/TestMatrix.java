package tests;

import java.util.Arrays;

import Utilities.Matrix;
import Utilities.NoDataException;
import junit.framework.TestCase;

public class TestMatrix extends TestCase {
	
	public void testAddColumnFailed1() {
		Matrix m = new Matrix(0,0);
		try {
			m.addColumn(new String[] {});
			fail("Expected an IndexOutOfBoundsException");
		}catch(IndexOutOfBoundsException e) {
			assertEquals("Tried to add one column to many max column size is 0", e.getMessage());
		}
	}
	
	public void testAddColumnFailed2() {
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
	
	public void testGetFailed1() {
		Matrix m = new Matrix(2,1);
		m.addColumn(new String[] {"1"});
		try {
			m.get(1);
			fail("NoDataException expected");
		}catch (NoDataException e) {
			assertEquals("Cannot retrieve empty value(s)",e.getMessage());
		}
	}
	
	public void testGetFailed2() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		try {
			m.get(1);
			fail("NoDataException expected");
		}catch (IndexOutOfBoundsException e) {
			assertEquals("No sutch column 1 in matrix of dimensions [1,1]",e.getMessage());
		}
	}
	
	public void testGetFailed3() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		try {
			m.get(0,1);
			fail("NoDataException expected");
		}catch (IndexOutOfBoundsException e) {
			assertEquals("No sutch row 1 in matrix of dimensions [1,1]",e.getMessage());
		}
	}
	
	
	
}

