package tests;

import junit.framework.TestCase;
import run.RunApp;

public class AppTest extends TestCase {
	
	public void testCreateApp() {
		RunApp myApp = new RunApp();
		assertNotNull(myApp);
	}
	
	public void testGetRunTableData() {
		RunApp myApp = new RunApp();		
	}

}
