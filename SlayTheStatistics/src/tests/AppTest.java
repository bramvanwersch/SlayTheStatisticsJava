package tests;

import java.util.Arrays;

import gui.App;
import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	public void testCreateApp() {
		App myApp = new App();
		assertNotNull(myApp);
	}
	
	public void testGetRunTableData() {
		App myApp = new App();		
	}

}
