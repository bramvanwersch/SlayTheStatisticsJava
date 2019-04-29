package run;

import java.util.Arrays;
import java.util.Map;

public class EventFloor extends GeneralFloor {

	public EventFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String getName() {
		return getStringValue("event_name");
	}
	
	public String getChoice() {
		return getStringValue("player_choice");
	}
	
	public String getEnemies() {
		return getStringValue("enemies");
	}
	
	public int getDamage() {
		return getIntValue("damage");
	}
	
	public int getTurns() {
		return getIntValue("turns");
	}
	
	public int getHealed() {
		return getIntValue("damage_healed");
	}
	
	public String[] getRelic() {
		return getArrayValues(new String[] {"relics_obtained", "key"});
	}
	
	public String[] getCard() {
		return getArrayValues("cards_obtained");
	}
	
	public String getRemoved() {
		return getStringValue("cards_removed");
	}
	
	public String getUpgraded() {
		return getStringValue("cards_upgraded");
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		floorMap.put("name", getName());
		floorMap.put("choice", getChoice());
		if (!getEnemies().equals("!")) {
			floorMap.put("enemies", getEnemies());
			floorMap.put("damage", "" + getDamage());
			floorMap.put("turns", "" + getTurns());
			floorMap.put("special",String.format("Fought: %s they dealt %d damage in %d turns.",
					getEnemies(), getDamage(),getTurns()));}
		if (getHealed() != 0) {
			floorMap.put("healed", "" + getHealed());}
		if (getRelic().length != 0) {
			floorMap.put("relic", Arrays.toString(getRelic()));}
		if (getCard().length != 0) {
			floorMap.put("picked", Arrays.toString(getCard()));}
		if (!getRemoved().equals("!")) {
			floorMap.put("cardRemoved", getRemoved());
			floorMap.put("special", floorMap.get("special") + " Card removed: "+ getRemoved());}
		if (!getUpgraded().equals("!")) {
			floorMap.put("cardUpgraded", getUpgraded());
			floorMap.put("special", floorMap.get("special") + " Card upgraded: "+ getUpgraded());}
		return floorMap;
	}
	@Override
	public String getText() {
		String text = starterText();
		text += String.format("Event %s, you chose %s\n", getName(), getChoice());
		if (!getEnemies().equals("!")) {
			text += String.format("Encountered %s they dealt %d damage in %d turns\n", getEnemies(), 
					getDamage(), getTurns());
		}
		if (!(getRelic().length == 0)) {
			text += String.format("Relic gained: %s\n", getRelic());
		}
		if (!(getCard().length == 0)) {
			text += String.format("Card gained: %s\n", getCard());
		}
		if (!getRemoved().contentEquals("!")) {
			text += String.format("Card removed: %s\n", getRemoved());
		}
		if (!getUpgraded().contentEquals("!")) {
			text += String.format("Card upgraded: %s\n", getUpgraded());
		}
		return text;
	}
	

}
