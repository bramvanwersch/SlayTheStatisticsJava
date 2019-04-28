package tests;

import junit.framework.TestCase;
import main.STSRun;

public class STSRunTest extends TestCase {

	public void testCreateSTSRun() {
		STSRun myRun = new STSRun("IRONCLAD","1551815208.run");
		assertNotNull(myRun);
	}
	
//	public void 
}
