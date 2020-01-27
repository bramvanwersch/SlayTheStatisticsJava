package tests;

import java.util.Arrays;

import Utilities.DataFrame;
import Utilities.NoDataException;
import junit.framework.TestCase;

public class DataFrameTest extends TestCase {
	
	public void testAddColumn() {
		DataFrame df = new DataFrame(1,1);
		df.addNamedColumn(new String[] {"1"},"a");
		String[] xl = df.getColumn("a");
		assertEquals(Arrays.toString(xl), Arrays.toString(new String[] {"1"}));
	}
	
	public void testAddColumn2() {
		DataFrame df = new DataFrame(2,1);
		df.addNamedColumn(new String[] {"1"},"a");
		try {
			df.addNamedColumn(new String[] {"1"},"a");
			fail("IllegalArgumentException expected");
		}catch (IllegalArgumentException e){
			assertEquals("Column name a aleardy exists", e.getMessage());
		}
	}
	
	public void testGetFailed1() {
		DataFrame df = new DataFrame(1,1);
		df.addNamedColumn(new String[] {"1"},"a");
		try {
			df.getColumn("abc");
			fail("IllegalArgumentException expected");
		}catch (IllegalArgumentException e){
			assertEquals("Column name abc does not exist", e.getMessage());
		}
	}
	
	public void testGetFailed2() {
		DataFrame df = new DataFrame(1,1);
		try {
			df.getColumn("col0");
			fail("IllegalArgumentException expected");
		}catch (NoDataException e){
			assertEquals("Cannot retrieve empty value(s)", e.getMessage());
		}
	}
}
