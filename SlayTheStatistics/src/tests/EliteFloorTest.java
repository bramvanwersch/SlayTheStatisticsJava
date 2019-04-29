package tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import run.EliteFloor;
import run.GeneralFloor;

public class EliteFloorTest extends TestCase {
	private GeneralFloor previousFloor;
	private Map<String, String> floorDict;
	
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
		floorDict.put("path_per_floor", "E");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("picked", "\"SKIP\"");
		floorDict.put("not_picked","[\"Headbutt\",\"BodySlam\",\"SwordBoomerang\"]");
		floorDict.put("damage", "10.0");
		floorDict.put("enemies", "birds");
		floorDict.put("turns", "5.0");
		floorDict.put("key", "burning blood");
	}

	public void testCreateElitefloor() {
		setupFloorDict();
		setupPreviousFloor();
		EliteFloor eFloor = new EliteFloor(floorDict, previousFloor, "1");
		assertNotNull(eFloor);
	}
	
	public void testGetEliteText() {
		setupFloorDict();
		setupPreviousFloor();
		EliteFloor eFloor = new EliteFloor(floorDict, previousFloor, "1");
//		System.out.println(eFloor.getText());
		assertEquals("Elite encounter (1):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Encountered birds they dealt 10 damage in 5 turns\n" + 
				"Ignored cards: [Headbutt, BodySlam, SwordBoomerang]\n" + 
				"Relic aquired: burning blood\n", eFloor.getText());
		
	}
	
}
