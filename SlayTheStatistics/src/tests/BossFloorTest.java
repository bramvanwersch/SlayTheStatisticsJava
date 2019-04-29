package tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import run.BossFloor;
import run.GeneralFloor;

public class BossFloorTest extends TestCase {
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
		floorDict.put("path_per_floor", "B");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("picked", "BloodVial");
		floorDict.put("not_picked","[\"Astrolabe\",\"CallingBell\"]");
		floorDict.put("damage", "10.0");
		floorDict.put("enemies", "bossie boy");
		floorDict.put("turns", "5.0");
	}
	
	public void testCreateBossFloor() {
		setupPreviousFloor();
		setupFloorDict();
		BossFloor bFloor = new BossFloor(floorDict, previousFloor, "3");
		assertNotNull(bFloor);
	}
	
	public void testGetBossText() {
		setupPreviousFloor();
		setupFloorDict();
		BossFloor bFloor = new BossFloor(floorDict, previousFloor, "3");
//		System.out.println(bFloor.getText());
		assertEquals("Boss fight (3):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Encountered bossie boy it dealt 10 damage in 5 turns\n" + 
				"Picked Relic: BloodVial.\nIgnored relics: [Astrolabe, CallingBell]\n",
				bFloor.getText());
	}

}
