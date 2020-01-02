package floors;

import java.util.Arrays;
import java.util.Map;

public class BossFloor extends GeneralFloor{

	public BossFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public int getDamage() {
		return getIntValue("damage");
	}
	
	public String[] getNotPicked() {
		return getArrayValues("not_picked");
	}

	public String getPicked() {
		return getStringValue("picked");
	}
	
	public int getTurns() {
		return getIntValue("turns");
	}
	
	public String getEnemies() {
		return getStringValue("enemies");
	}
	
	public int getHealed() {
		return getDamage() + getHealthChange();
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		if (!this.floorNo.equals("50")){
			floorMap.put("relic", getPicked());
			floorMap.put("notRelic", Arrays.toString(getNotPicked()));}
		floorMap.put("enemies", getEnemies());
		floorMap.put("damage", "" + getDamage());
		floorMap.put("turns", "" + getTurns());
		floorMap.put("healed", "" + getHealed());
		floorMap.put("special",String.format("Fought: %s they dealt %d damage in %d turns. Ignored relics: %s",
				getEnemies(), getDamage(),getTurns(), Arrays.toString(getNotPicked())));
		return floorMap;	
	}
	
	@Override
	public String getText() {
		String text = starterText();
		text += String.format("Encountered %s it dealt %d damage in %d turns\n", getEnemies(), getDamage(),
				getTurns());
		if (getPicked().equals("SKIP")) {
			text += String.format("Ignored relics: %s\n", Arrays.toString(getNotPicked()));
		}
		else if (!this.floorNo.equals("50")){
			text += String.format("Picked Relic: %s.\nIgnored relics: %s\n", getPicked(), Arrays.toString(getNotPicked()));
		}
		return text;
	}
}
