package tests;

import global.AllRunSummary;
import junit.framework.TestCase;

public class AllRunSummaryTest extends TestCase {
	
	public void createAllRunSummary() {
		AllRunSummary mySum = new AllRunSummary();
		assertNotNull(mySum);
	}
}
