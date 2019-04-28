package main;

import java.util.Arrays;
import java.util.Map;

public class EliteFloor extends MonsterFloor {

	public EliteFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String getRelic() {
		return getStringValue("key");
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getMonsterFloorMap();
		floorMap.put("relic", getRelic());
		return floorMap;	
	}
	
	@Override
	public String getText() {
		String text = getMonsterText();
		text += String.format("Relic aquired: %s\n", getRelic());
		return text;
	}

}
