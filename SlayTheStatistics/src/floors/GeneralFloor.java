package floors;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gui.Settings;


//TODO: raise apprpriate error when a key is not in the map.
public class GeneralFloor implements Floor{
	private final Map<String, String> floorDict;
	private final Floor prevFloor;
	protected final String floorNo;

	public GeneralFloor(Map<String, String> floorDict, Floor previousFloor,
			String floorNo) {
		this.floorDict = floorDict;
		this.prevFloor = previousFloor;
		this.floorNo = floorNo;
	}
	
	public Color getColor() {
		return new Color(255,255,255);
	}

	public String getPath() {
		String pathName = null;
		switch(getStringValue("path_per_floor")) {
		case "M":
			pathName = "Monster encounter";
			break;
		case "E":
			pathName = "Elite encounter";
			break;
		case "R":
			pathName = "Rest site";
			break;
		case "?":
			pathName = "Event";
			break;
		case "T":
			pathName = "Chest";
			break;
		case "$":
			pathName = "Shop";
			break;
		case "B":
			pathName = "Boss fight";
			break;
		case "null":
			pathName = "Start of new floor";
			break;
		}
		return pathName;
	}
	
	public int getGold(){
		return getIntValue("gold_per_floor");
	}
	
	public int getHealth() {
		return getIntValue("current_hp_per_floor");
	}
	
	public int getMaxHp() {
		return getIntValue("max_hp_per_floor");
	}
	
	//calculated vars
	public int getGoldChange() {
		return getGold() - prevFloor.getGold();
	}
	
	public int getHealthChange() {
		return getHealth() - prevFloor.getHealth();
	}
	
	public int getHealed() {
		if (getHealthChange() > 0) {
			return getHealthChange();
		}
		return 0;
	}
	
	public String getPotionObtained() {
		return getStringValue("potion_gained");
	}
	
	public String getPotionUse() {
		return getStringValue("potions_floor_usage");
	}
	
	protected Map<String,String> getBaseFloorMap(){
		Map<String, String> floorMap = new HashMap<String,String>();
		floorMap.put("path", getPath());
		floorMap.put("health", "" + getHealth());
		floorMap.put("maxHealth", "" + getMaxHp());
		floorMap.put("healthChange", ""+ getHealthChange());
		floorMap.put("gold", "" + getGold());
		floorMap.put("goldChange", "" + getGoldChange());
		floorMap.put("healed", ""+ getHealed());
		if (!getPotionObtained().equals("!")) {
			floorMap.put("potionGain", getPotionObtained());}
		if(!getPotionUse().equals("!")) {
		floorMap.put("potionUse", getPotionUse());}
		return floorMap;	
	}
	
	public Map<String,String> getFloorMap(){
		return getBaseFloorMap();
	}
	
	protected String starterText() {
		String starterString = String.format("%s (%s):\n",getPath(), floorNo);
		starterString += String.format("Health: %s/%s (%d)\n", getHealth(),getMaxHp(),
				getHealthChange());
		starterString += String.format("Gold: %s (%d)\n",getGold(), getGoldChange());
		if (!getPotionObtained().equals("!") || !getPotionUse().equals("!")) {
			starterString += String.format("Potion gained: %s. Potion used: %s",
					getPotionObtained(),getPotionUse());
		}
		return starterString;
	}
	
	public String getText() {
		return starterText();
	}

	protected int getIntValue(String... keys) {
		if (getHasKey("int", keys)) {
			for (String key:keys) {
				if (floorDict.containsKey(key)) {
					return Integer.parseInt(floorDict.get(key).split("\\.")[0]);
				}
			}
		}
		return 0;
	}
	
	protected String getStringValue(String... keys) {
		if (getHasKey("string", keys)) {
			for (String key:keys) {
				if (floorDict.containsKey(key)) {
					return floorDict.get(key).replace("\"", "");
				}
			}
		}
		return "!";
	}
	
	protected String[] getArrayValues(String... keys) {
		if (getHasKey("array", keys)) {
			for (String key:keys) {
				if (floorDict.containsKey(key)) {
						String notPicked = floorDict.get(key).replace("[", "").replace("\"", "").replace("]", "");
						return notPicked.split("\\,");
				}
			}
		}
		return new String[0];
	}
	
// to allow for multiple keys, if multiple keys correspond whit the same value, to avoid warning printing
// slightly different function that will return the first key that is in the dict or print a warning
	protected boolean getHasKey(String id, String... keys) {
		for (String key:keys) {
			if (!floorDict.containsKey(key)) {
				continue;
			}
			else {
				return true;
			}
		}
		if (Settings.DEBUG && false) {
			if (id.equals("string")) {
				System.out.println(String.format("WARNING! No keys %s for floorno %s. Set to default: !", Arrays.toString(keys), floorNo));
			}
			else if (id.equals("int")) {
				System.out.println(String.format("WARNING! No keys %s for floorno %s. Set to default: 0", Arrays.toString(keys), floorNo));
			}
			else if (id.equals("array")) {
				System.out.println(String.format("WARNING! No keys %s for floorno %s. Set to default: []", Arrays.toString(keys), floorNo));
			}
		}
		return false;
	}

}