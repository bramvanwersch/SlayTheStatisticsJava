package floors;

import java.awt.Color;
import java.util.Map;

public class ChestFloor extends GeneralFloor {

	public ChestFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String getRelic() {
		return getStringValue("key");
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		floorMap.put("relic", getRelic());
		return floorMap;	
	}
	
	public Color getColor() {
		return new Color(148,78,3,80);
	}

	public String getText() {
		String text = starterText();
		text += String.format("Relic gained: %s\n", getRelic());
		return text;
	}

}
