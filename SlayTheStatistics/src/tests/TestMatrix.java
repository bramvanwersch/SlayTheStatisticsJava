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
	
	public void testAddColumnData() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		String x = m.getValue(0,0);
		String[] xl = m.getColumn(0);
		assertEquals(x, "1");
		assertEquals(Arrays.toString(xl), Arrays.toString(new String[] {"1"}));
	}
	
	public void testGetColumnFailed1() {
		Matrix m = new Matrix(2,1);
		m.addColumn(new String[] {"1"});
		try {
			m.getColumn(1);
			fail("NoDataException expected");
		}catch (NoDataException e) {
			assertEquals("Cannot retrieve empty value(s)",e.getMessage());
		}
	}
	
	public void testGetColumnFailed2() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		try {
			m.getColumn(1);
			fail("NoDataException expected");
		}catch (IndexOutOfBoundsException e) {
			assertEquals("No sutch column 1 in matrix of dimensions [1,1]",e.getMessage());
		}
	}
	
	public void testGetColumnFailed3() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		try {
			m.getValue(0,1);
			fail("NoDataException expected");
		}catch (IndexOutOfBoundsException e) {
			assertEquals("No sutch row 1 in matrix of dimensions [1,1]",e.getMessage());
		}
	}
	
	public void testSeperatedValuesToMatrix1() {
		String sepVals = "1\n2\n3\n";
		Matrix m = new Matrix(1,3);
		m.addStringMatrix(sepVals, ",");
		assertEquals(m.getValue(0,0), "1");
		assertEquals(m.getValue(0,1), "2");
		assertEquals(m.getValue(0,2), "3");
	}
	
	public void testSeperatedValuesToMatrix2() {
		String sepVals = "1,2\n2,3\n3,4\n";
		Matrix m = new Matrix(2,3);
		m.addStringMatrix(sepVals, ",");
		String[] c1 = m.getColumn(0);
		String[] c2 = m.getColumn(1);
		assertEquals(Arrays.toString(c1), Arrays.toString(new String[] {"1","2","3"}));
		assertEquals(Arrays.toString(c2), Arrays.toString(new String[] {"2","3","4"}));
	}
	
	public void testSeperatedValuesToMatrix3() {
		String sepVals = "1,2\n2,3\n3,4\n";
		Matrix m = new Matrix(2,3);
		m.setColumnTypePreference(Integer.class, 0,1);
		m.addStringMatrix(sepVals, ",");
		Integer[] c1 = m.getColumn(0);
		Integer[] c2 = m.getColumn(1);
		assertEquals(Arrays.toString(c1), Arrays.toString(new String[] {"1","2","3"}));
		assertEquals(Arrays.toString(c2), Arrays.toString(new String[] {"2","3","4"}));
	}
	
	public void testSeperatedValuesToMatrixFailed() {
		String sepVals = "1\n2,3\n3,4\n";
		try {
			Matrix m = new Matrix(2,3);
			m.addStringMatrix(sepVals, ",");
			fail("IllegalArgumentException expected");
		}catch (IllegalArgumentException e){
			assertEquals("Cannot process properly. Rows have different lengths", e.getMessage());
		}
	}
	
	public void testColumnRemoval() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		m.removeColumn(0);
		assertEquals(Arrays.toString(m.dimensions()), Arrays.toString(new int[] {0,1}));
	}
	
	public void testRowRemoval1() {
		Matrix m = new Matrix(2,2);
		m.addColumn(new String[] {"1","2"});
		m.addColumn(new String[] {"1","2"});
		m.removeRow(0);
		String[] c1 = m.getColumn(0);
		String[] c2 = m.getColumn(0);
		assertEquals(Arrays.toString(m.dimensions()), Arrays.toString(new int[] {2,1}));
		assertEquals(Arrays.toString(c1), Arrays.toString(new String[] {"2"}));
		assertEquals(Arrays.toString(c2), Arrays.toString(new String[] {"2"}));
	}
	
	public void testRowRemoval2() {
		Matrix m = new Matrix(2,2);
		m.addColumn(new String[] {"1","2"});
		m.addColumn(new String[] {"1","2"});
		m.removeRow(1);
		String[] c1 = m.getColumn(0);
		String[] c2 = m.getColumn(0);
		assertEquals(Arrays.toString(m.dimensions()), Arrays.toString(new int[] {2,1}));
		assertEquals(Arrays.toString(c1), Arrays.toString(new String[] {"1"}));
		assertEquals(Arrays.toString(c2), Arrays.toString(new String[] {"1"}));
	}
	
	public void testGetRow() {
		Matrix m = new Matrix(2,2);
		m.addColumn(new String[] {"1","2"});
		m.addColumn(new String[] {"1","2"});
		String[] r1 = m.getRow(0);
		assertEquals(Arrays.toString(r1), Arrays.toString(new String[] {"1","1"}));
	}
	
	public void testReplace() {
		Matrix m = new Matrix(1,1);
		m.addColumn(new String[] {"1"});
		m.replaceColumn(0, new String[] {"2"});
		String v = m.getValue(0, 0);
		assertEquals(v, "2");
	}
	
}

