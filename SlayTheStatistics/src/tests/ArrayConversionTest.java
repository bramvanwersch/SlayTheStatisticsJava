package tests;

import java.util.Arrays;

import Utilities.ArrayConversions;
import junit.framework.TestCase;

public class ArrayConversionTest extends TestCase {
	
	public void testStringToInteger() {
		String[] ss = new String[] {"1", "2"};
		Integer[] is = ArrayConversions.stringToInteger(ss);
		assertEquals(Arrays.toString(is), Arrays.toString(ss));
	}
	
	public void testStringToDouble() {
		String[] ss = new String[] {"1.0", "2.0"};
		Double[] is = ArrayConversions.stringToDouble(ss);
		assertEquals(Arrays.toString(is), Arrays.toString(ss));
	}

}
