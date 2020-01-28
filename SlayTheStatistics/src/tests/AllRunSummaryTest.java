package tests;

import global.RunSummarys;
import junit.framework.TestCase;

public class AllRunSummaryTest extends TestCase {
	
	public void createAllRunSummary() {
		RunSummarys mySum = new RunSummarys();
		assertNotNull(mySum);
	}
}
