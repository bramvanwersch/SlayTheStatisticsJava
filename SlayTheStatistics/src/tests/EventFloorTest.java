package tests;

import java.util.HashMap;
import java.util.Map;

import floors.EventFloor;
import floors.GeneralFloor;
import junit.framework.TestCase;

public class EventFloorTest extends TestCase {
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
		floorDict.put("path_per_floor", "?");
		floorDict.put("gold_per_floor", "400");
		floorDict.put("max_hp_per_floor", "90");
		floorDict.put("current_hp_per_floor","80");
		floorDict.put("event_name", "happy event");
		floorDict.put("player_choice", "nice_cake");
		floorDict.put("enemies", "cake eaters");
		floorDict.put("damage", "5.0");
		floorDict.put("turns", "5.0");
		floorDict.put("damage_healed", "0");
		floorDict.put("key", "cake relic");
		floorDict.put("cards_obtained", "cake card");
		floorDict.put("cards_removed", "no cake card");
		floorDict.put("cards_upgraded", "cake");
	}
	
	public void testCreateEventFloor() {
		setupFloorDict();
		setupPreviousFloor();
		EventFloor eFloor = new EventFloor(floorDict, previousFloor, "2");
		assertNotNull(eFloor);
	}
	
	public void testGetEventText() {
		setupFloorDict();
		setupPreviousFloor();
		EventFloor eFloor = new EventFloor(floorDict, previousFloor, "2");
//		System.out.println(eFloor.getText());
		assertEquals("Event (2):\n" + 
				"Health: 80/90 (30)\nGold: 400 (-50)\n" + 
				"Event happy event, you chose nice_cake\n" + 
				"Encountered cake eaters they dealt 5 damage in 5 turns\n" + 
				"Relic gained: cake relic\nCard gained: cake card\n" + 
				"Card removed: no cake card\nCard upgraded: cake\n",
				eFloor.getText());
	}

}
