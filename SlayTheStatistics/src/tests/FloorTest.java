package tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import floors.GeneralFloor;
import junit.framework.TestCase;

public class FloorTest extends TestCase {
	private Map<String, String> floorDict;
	private GeneralFloor previousFloor;
	
	public void setupPreviousFloor() {
		previousFloor = new GeneralFloor(setupPrevDict(), null, "1");
	}
	
	public Map<String, String> setupPrevDict() {
		Map<String, String> pDict = new HashMap<String, String>();
		pDict.put("path_per_floor", "E");
		pDict.put("gold_per_floor", "450");
		pDict.put("max_hp_per_floor", "80");
		pDict.put("current_hp_per_floor","50");
		return pDict;
	}
	
	public void setupFloorDict() {
		floorDict = new HashMap<String, String>();
		floorDict.put("path_per_floor", "M");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		//test entries not to be expected in real version
		floorDict.put("test_int", "40.0");
		floorDict.put("test_string", "\"hey\"");
		floorDict.put("test_array", "[\"hey\",\"ho\"]");
	}
	
	public void testCreateFloor() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		assertNotNull(myFloor);
	}
	
	public void testFullPathName() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		assertEquals("Monster encounter", myFloor.getPath());
	}
	
	public void testStarterText() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		assertEquals("Monster encounter (2):\n" + 
				"Health: 80/90 (30)\n" + 
				"Gold: 400 (-50)\n" ,myFloor.starterText());
	}
	
	public void testGetGoldChange() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		int observedChange = myFloor.getGoldChange();
		assertEquals(-50,observedChange);
	}
	
	public void testGetHealthChange() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		assertEquals(30, myFloor.getHealthChange());
	}
	
	public void testGetIntValue() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		int obVal = myFloor.getIntValue("test_int");
		assertEquals(40, obVal);
	}
	
	public void testGetStringValue() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		String obVal = myFloor.getStringValue("test_string");
		assertEquals("hey", obVal);
	}
	
	public void testGetArrayValues() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		String[] obVal = myFloor.getArrayValues("test_array");
		String [] expVal = {"hey", "ho"};
		assertEquals(Arrays.toString(expVal), Arrays.toString(obVal));
	}
	
	public void getIntValues2() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		int obVal = myFloor.getIntValue(new String[] {"tost","key","random","test_int"});
		assertEquals(40, obVal);
	}
	
	public void getStringValues2() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		String obVal = myFloor.getStringValue(new String[] {"tost_string","test_string"});
		assertEquals("hey", obVal);
	}
	
	public void testGetArrayValues2() {
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		String[] obVal = myFloor.getArrayValues(new String [] {"test_array", "tost_array"});
		String [] expVal = {"hey", "ho"};
		assertEquals(Arrays.toString(expVal), Arrays.toString(obVal));
	}
	
	public void testGetHasKey() {
		//testing if right returns for invalid keys. The prints are an inevitable artifact.
		setupFloorDict();
		setupPreviousFloor();
		GeneralFloor myFloor = new GeneralFloor(floorDict, previousFloor, "2");
		String[] arVal = myFloor.getArrayValues(new String [] {"array", "tost_array"});
		assertEquals(Arrays.toString(new String[0]), Arrays.toString(arVal));
		String strVal = myFloor.getStringValue(new String[] {"tost_string","string"});
		assertEquals("!",strVal);
		int intVal = myFloor.getIntValue(new String[] {"tost","key","random","testint"});
		assertEquals(0, intVal);

		
	}
}
