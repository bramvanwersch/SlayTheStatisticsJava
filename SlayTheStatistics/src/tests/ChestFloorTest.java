package tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import run.ChestFloor;
import run.GeneralFloor;

public class ChestFloorTest extends TestCase {
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
		floorDict.put("path_per_floor", "T");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("key", "some relic");
	}
	
	
	public void testCreateChestFloor() {
		setupFloorDict();
		setupPreviousFloor();
		ChestFloor cFloor = new ChestFloor(floorDict, previousFloor, "3");
		assertNotNull(cFloor);
	}
	
	public void testGetChestText() {
		setupFloorDict();
		setupPreviousFloor();
		ChestFloor cFloor = new ChestFloor(floorDict, previousFloor, "3");
//		System.out.println(cFloor.getText());
		assertEquals("Chest (3):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Relic gained: some relic\n",
				cFloor.getText());
	}

}
