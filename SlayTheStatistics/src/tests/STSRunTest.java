package tests;

import junit.framework.TestCase;
import run.STSRun;

public class STSRunTest extends TestCase {

	public void testCreateSTSRun() {
		STSRun myRun = new STSRun("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\IRONCLAD\\1551815208.run");
		assertNotNull(myRun);
	}
	
//	public void 
}
