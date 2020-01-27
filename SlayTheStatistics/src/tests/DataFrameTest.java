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
	
	public void testSeparatedValuesToDataFrame() {
		String sepVals = "this is one\n1\n2\n3\n";
		DataFrame df = new DataFrame(sepVals, "," , true);
		String v1 = df.getValue("this is one", 0);
		assertEquals(v1, "1");
	}
	
	public void testSeparatedValuesToDataFrameFailed() {
		String sepVals = "this is one,this is one\n1,1\n2,2\n3,3\n";
		try {
			DataFrame df = new DataFrame(sepVals, "," , true);
			fail("IllegalArgumentException expected");
		}catch (IllegalArgumentException e) {
			assertEquals("Header contains duplicate values. Cannot create the dataframe.",e.getMessage());
		}
	}
	
	public void testRemoveColumn1() {
		String sepVals = "this is one,this is two\n1,1\n2,2\n3,3\n";
		DataFrame df = new DataFrame(sepVals, "," , false);
		df.removeColumn(0);
		String v1 = df.getValue(0, 0);
		assertEquals(v1, "this is two");
	}
	
	public void testRemoveColumn2() {
		String sepVals = "this is one,this is two\n1,2\n1,2\n1,2\n";
		DataFrame df = new DataFrame(sepVals, "," , true);
		df.removeColumn("this is one");
		String v1 = df.getValue(0, 0);
		assertEquals(v1, "2");
	}
	
	
}
