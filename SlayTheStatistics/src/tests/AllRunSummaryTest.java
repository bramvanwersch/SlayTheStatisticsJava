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
		for (String key:mySum.getAllCardRateKeys()) {
			System.out.println(key + ": " + Arrays.toString(mySum.getCardRates(key)));
		}
		for (String key1:mySum.getAllRelicRateKeys()) {
			System.out.println(key1 + ": " + Arrays.toString(mySum.getRelicRates(key1)));
		}
	}

}
