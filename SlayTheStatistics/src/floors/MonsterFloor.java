package floors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MonsterFloor extends GeneralFloor {

	public MonsterFloor(Map<String, String> floorDict, Floor previousFloor,
			String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String[] getPicked() {
		return getArrayValues("picked");
	}

	public String[] getNotPicked() {
		return getArrayValues("not_picked");
	}
	
	public int getDamage() {
		return getIntValue("damage");
	}
	
	public String getEnemies() {
		return getStringValue("enemies");
	}
	
	public int getTurns() {
		return getIntValue("turns");
	}

	public int getHealed() {
		return getDamage() + getHealthChange();
	}
	
	public Map<String,String> getMonsterFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		floorMap.put("picked", Arrays.toString(getPicked()));
		floorMap.put("notPicked", Arrays.toString(getNotPicked()));
		floorMap.put("enemies", getEnemies());
		floorMap.put("damage", "" + getDamage());
		floorMap.put("turns", "" + getTurns());
		floorMap.put("healed", "" + getHealed());
		floorMap.put("special",String.format("Fought: %s they dealt %d damage in %d turns",
				getEnemies(), getDamage(),getTurns()));
		return floorMap;	
	}
	
	public Map<String,String> getFloorMap(){
		return this.getMonsterFloorMap();
	}
	
	public String getMonsterText() {
		String text = starterText();
		text += String.format("Encountered %s they dealt %d damage in %d turns\n", getEnemies(), getDamage(),
				getTurns());
		if (isAllSkip()) {
			text += String.format("Ignored cards: %s\n", Arrays.toString(getNotPicked()));
		}
		else if (Arrays.asList(getPicked()).contains("SKIP")) {
			text += String.format("Picked card: %s.\nIgnored cards: %s\n", Arrays.toString(removeAllSkip()), Arrays.toString(getNotPicked()));
		}
		else {
			text += String.format("Picked card: %s.\nIgnored cards: %s\n", Arrays.toString(getPicked()), Arrays.toString(getNotPicked()));
		}
		return text;
	}
	@Override
	public String getText() {
		return getMonsterText();
	}
	
	private boolean isAllSkip() {
		for (int i = 0; i < getPicked().length ; i++) {
			  if (!getPicked()[i].equals("SKIP")) { 
				  return false;
			  }
		}
			return true;
	}
	
	private String[] removeAllSkip() {
		List<String> tempList = new ArrayList<String>();
		for (String card : getPicked()) {
			if (!card.equals("SKIP")) {
				tempList.add(card);
			}
		}
		return tempList.toArray(new String[tempList.size()]);
	}


}
