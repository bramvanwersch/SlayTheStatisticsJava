package tests;

import gui.RunApp;
import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	public void testCreateApp() {
		RunApp myApp = new RunApp();
		assertNotNull(myApp);
	}
	
	public void testGetRunTableData() {
		RunApp myApp = new RunApp();		
	}

}
