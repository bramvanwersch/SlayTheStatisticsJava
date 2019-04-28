package tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import main.GeneralFloor;
import main.RestFloor;

public class RestFloorTest extends TestCase {
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
		floorDict.put("path_per_floor", "R");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("key", "SMITH");
		floorDict.put("data", "strike");
	}
	
	public void testCreateRestFloor() {
		setupFloorDict();
		setupPreviousFloor();
		RestFloor rFloor = new RestFloor(floorDict, previousFloor, "1");
		assertNotNull(rFloor);
	}
	
	public void testGetRestText() {
		setupFloorDict();
		setupPreviousFloor();
		RestFloor rFloor = new RestFloor(floorDict, previousFloor, "1");
//		System.out.println(rFloor.getText());
		assertEquals("Rest site (1):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Campfire activity: SMITH (strike)\n",rFloor.getText());
	}

}
