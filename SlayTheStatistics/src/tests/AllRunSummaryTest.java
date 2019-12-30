package tests;

import java.util.Arrays;

import global.AllRunSummary;
import junit.framework.TestCase;

public class AllRunSummaryTest extends TestCase {
	
	public void createAllRunSummary() {
		AllRunSummary mySum = new AllRunSummary();
		assertNotNull(mySum);
	}
	
	public void testGetFileNames() {
		AllRunSummary mySum = new AllRunSummary();
		mySum.getFileNames("IRONCLAD");
		mySum.getRunFileClasses();
		mySum.countCardStats();
		mySum.countRelicStats();
		mySum.mapsToCsv();
		//unfinished
	}

}
