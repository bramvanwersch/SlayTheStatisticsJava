package tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import run.GeneralFloor;
import run.MonsterFloor;

public class MonsterFloorTest extends TestCase {
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
		floorDict.put("picked", "\"SKIP\"");
		floorDict.put("not_picked","[\"Headbutt\",\"BodySlam\",\"SwordBoomerang\"]");
		floorDict.put("damage", "10.0");
		floorDict.put("enemies", "birds");
		floorDict.put("turns", "5.0");
	}
	
	public void setupDifferentKey(String key, String value) {
		floorDict.put(key, value);
	}
	
	public void testCreateMonsterFloor() {
		setupFloorDict();
		setupPreviousFloor();
		MonsterFloor mFloor = new MonsterFloor(floorDict,previousFloor, "1");
		assertNotNull(mFloor);	
	}
	
	public void testGetPicked() {
		setupFloorDict();
		setupPreviousFloor();
		MonsterFloor mFloor = new MonsterFloor(floorDict,previousFloor, "1");
		String[] pCard = mFloor.getPicked();
		assertEquals(Arrays.toString(new String [] {"SKIP"}), Arrays.toString(pCard));
		setupDifferentKey("picked", "a card\",[\"another card\"]");
		MonsterFloor mFloor2 = new MonsterFloor(floorDict,previousFloor, "1");
		String pCard2[] = mFloor2.getPicked();
		assertEquals(Arrays.toString(new String[] {"a card", "another card"}), Arrays.toString(pCard2));
	}
	
	public void testGetNotPicked() {
		setupFloorDict();
		setupPreviousFloor();
		MonsterFloor mFloor = new MonsterFloor(floorDict,previousFloor, "1");
		String[] npCard = mFloor.getNotPicked();
		assertEquals("[Headbutt, BodySlam, SwordBoomerang]", Arrays.toString(npCard));
	}
	
	public void testGetHealed() {
		setupFloorDict();
		setupPreviousFloor();
		MonsterFloor mFloor = new MonsterFloor(floorDict,previousFloor, "1");
		int healed = mFloor.getHealed();
		assertEquals(40, healed);
	}
	
	public void testGetMonsterText() {
		setupFloorDict();
		setupPreviousFloor();
		MonsterFloor mFloor = new MonsterFloor(floorDict,previousFloor, "1");
//		System.out.println(mFloor.getMonsterText());
		assertEquals("Monster encounter (1):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Encountered birds they dealt 10 damage in 5 turns\n" + 
				"Ignored cards: [Headbutt, BodySlam, SwordBoomerang]\n",
				mFloor.getMonsterText());
		setupDifferentKey("picked", "Homing shot");
		setupDifferentKey("not_picked", "[\"shot\",\"honey bun\"]");
		MonsterFloor mFloor2 = new MonsterFloor(floorDict,previousFloor, "1");
//		System.out.println(mFloor2.getReturnText());
		assertEquals("Monster encounter (1):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Encountered birds they dealt 10 damage in 5 turns\n" + 
				"Picked card: [Homing shot].\nIgnored cards: [shot, honey bun]\n",
				mFloor2.getMonsterText());
	}
}
