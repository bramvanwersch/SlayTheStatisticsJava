package tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import main.GeneralFloor;
import main.ShopFloor;

public class ShopFloorTest extends TestCase {
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
		floorDict.put("path_per_floor", "$");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("items_purchased", "apple, orange, fruit");
		floorDict.put("items_purged", "stuff");
	}
	
	public void testCreateShopFloor() {
		setupPreviousFloor();
		setupFloorDict();
		ShopFloor sFloor = new ShopFloor(floorDict, previousFloor, "2");
		assertNotNull(sFloor);
	}
	
	public void testGetShopText() {
		setupPreviousFloor();
		setupFloorDict();
		ShopFloor sFloor = new ShopFloor(floorDict, previousFloor, "2");
//		System.out.println(sFloor.getText());
		assertEquals("Shop (2):\nHealth: 80/90 (30)\n" + 
				"Gold: 400 (-50)\nItems bought: [apple,  orange,  fruit]\n" + 
				"Items purged: [stuff]\n", sFloor.getText());
	}
}
