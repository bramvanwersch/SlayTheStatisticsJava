package run;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: floor 1 has to many keys whit no values. Not sure where they come from.
public class ReadingRunFile {
	private Map<String, String> globalVarDict;
	private Map<String, Map<String, String>> floorDict;
	private String filePath;

	public ReadingRunFile(String filePath, boolean bothDicts) {
		this.globalVarDict = new HashMap<String, String>();
		this.filePath = filePath;
		String fileText = getFileText(filePath);
		String[] remainAndDicts = getDictionaries(fileText);
		String [][] globalAndFloor = getKeysAndValues(remainAndDicts[0]);
		addToGlobalDict(keyValToLists(globalAndFloor[0]));
		if (bothDicts) {
			//can only be created if the amount of floors is known. This is a key in the global dict.
			createFloorDict();
			addToFloorDict(keyValToLists(globalAndFloor[1]));
			addInnerDictToFloorDict(remainAndDicts[1].split("\\}\\{"));
			}
		}

// reading the file
	private String getFileText(String filePath) {
		Scanner in = null;
		try {
			in = new Scanner(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		while(in.hasNext()) {
		    sb.append(in.next());
		}
		in.close();
		return sb.toString();
	}
//splits the fileText into dictionaries that where in the full dictionary and the remaining full dictionary.
	private String[] getDictionaries(String fileText) {
		Pattern dictPattern = Pattern.compile("[\\[,](\\{.+?\\})");
		Matcher matcher = dictPattern.matcher(fileText);
		String tempDictString = "";
		while(matcher.find()) {
			tempDictString += matcher.group(1);
		}
		String[] remainAndDictsArray = {matcher.replaceAll(""), tempDictString};
		return remainAndDictsArray;
	}
	
	private String[][] getKeysAndValues(String remDict) {
		//matches everything but the last key in the remaining dict.
		Pattern keyValuePattern = Pattern.compile("([\"a-z_]+?):(.+?),+(?=([\"a-z_]+?):(.+?))");
		Matcher matcher = keyValuePattern.matcher(remDict);
		List<String> tempFloorList = new  ArrayList<String>();
		List<String> tempGlobalList = new  ArrayList<String>();
		while (matcher.find()) {
			if (keyCheck(matcher.group(1)).equals("Global")) {
				tempGlobalList.add(matcher.group(1) + ":" + matcher.group(2));
			}
			else if (keyCheck(matcher.group(1)).equals("FloorList")) {
				tempFloorList.add(matcher.group(1) + ":" + matcher.group(2));
			}
		}
		//for capturing the last match.
		String lastMatch = matcher.replaceAll("");
		String [] finalMatch = lastMatch.replace("\"","").replace("}", "").replace("{","").split(":");
		if (keyCheck(finalMatch[0]).equals("Global")) {
			tempGlobalList.add(finalMatch[0] + ":" + finalMatch[1]);
		}
		else if (keyCheck(finalMatch[0]).equals("FloorList")) {
			tempFloorList.add(finalMatch[0] + ":" + finalMatch[1]);
		}
		String [][] tempFloorGlobalArray = {tempGlobalList.toArray(new String[tempGlobalList.size()]),
				tempFloorList.toArray(new String[tempFloorList.size()])};
		return tempFloorGlobalArray;
	}

	private String[][] keyValToLists(String[] keysAndValues) {
		List<String> tempKeys = new ArrayList<String>();
		List<String> tempValues = new ArrayList<String>();
		for (int i = 0; i < keysAndValues.length; i++) {
			String[] keyVals = keysAndValues[i].split(":");
			int count = 0;
			for (int j = 0; j < keyVals.length/2; j++) {
				tempKeys.add(j, keyVals[count].replace("\"", ""));
				tempValues.add(j, keyVals[count+1]);
				count += 2;
			}
		}
		assert(tempKeys.size() == tempValues.size());
		String[][] keyValArray = {tempKeys.toArray(new String[tempKeys.size()]),
				tempValues.toArray(new String[tempValues.size()])};
		return keyValArray;
	}
	
	private String keyCheck(String key) {
		key = key.replace("\"", "");
		String [] globalKeys = {"character_chosen", "items_purchased", "floor_reached","items_purged",
				"neow_bonus", "is_prod", "is_daily", "is_ascension_mode", "chose_seed", "neow_cost",
				"master_deck","victory", "special_seed", "relics", "is_endless", "seed_played",
				"is_trial", "purchased_purges", "campfire_upgraded", "gold", "campfire_rested",
				"ascension_level"};
		String[] unwantedKeys =  {"playtime","score","play_id","local_time","seed_source_timestamp",
				"circlet_count","win_rate","timestamp","build_version", "player_experience","is_beta",
				"path_taken","potions_floor_spawned"};
		for(String uKey : unwantedKeys) {
			if (uKey.equals(key)) {
				return "Unwanted";
			}
		}
		for (String uKey: globalKeys) {
			if (uKey.equals(key)) {
				return "Global";
			}
		}
		return "FloorList";
	}
	
	private void addToGlobalDict(String[][] keysValues) {
		for (int i = 0; i < keysValues[0].length; i++) {
			globalVarDict.put(keysValues[0][i], keysValues[1][i]);
		}
	}

	private void addToFloorDict(String[][] keysValues) {
		//expects a list of values that are either floor values or a list that is 
		//almost as long as the amount of floors.
		for (int i = 0; i < keysValues[0].length; i++) {
			String []arrayOfFloors =  keysValues[1][i].replace("[","").replace("]","").replace("\"","").split(",");
			String key = keysValues[0][i];
			if (key.equals("potions_floor_usage")){
				for (String val:arrayOfFloors) {
					Map<String, String> floorMap = floorDict.get(val);
					floorMap.put(key, "yes");
				}
			}
			else if (key.equals("items_purged_floors")) {
				mergeDictEntries("items_purged", arrayOfFloors);
			}
			else if (key.equals("item_purchase_floors")) {
				mergeDictEntries("items_purchased", arrayOfFloors);
			}
			//when an array equal to floor length (or almost because certain things are saved unproper).
			else {
				for (int j = 1; j < arrayOfFloors.length + 1; j++) {
					Map<String, String> floorMap = floorDict.get(j+"");
					floorMap.put(key, arrayOfFloors[j-1]);
				}
			}	
		}
	}
	
	private void mergeDictEntries(String key, String[] arrayOfFloors) {
		String [] arrayItems = globalVarDict.get(key).replace("[","").replace("]","").replace("\"","").split(",");
		for (int k =0; k < arrayOfFloors.length; k++) {
			Map<String, String> floorMap = floorDict.get(arrayOfFloors[k]);
			if (floorMap == null) {
				//TODO check wtf is wrong here
				continue;
			}
			else if (floorMap.containsKey(key)) {
				//to add multiple items to a dict entry
				floorMap.put(key,floorMap.get(key)+ "," + arrayItems[k]);
			}
			else {
				floorMap.put(key, arrayItems[k]);
			}
		}
		globalVarDict.remove(key);
	}

	private void createFloorDict() {
		this.floorDict = new HashMap<String, Map<String, String>>();
		for (int i = 0; i < Integer.valueOf(globalVarDict.get("floor_reached")); i++ ) {
			floorDict.put(i + 1 +"", new HashMap<String,String>() );
		}
	}
	
	private void addInnerDictToFloorDict(String[] arrayDicts) {
		//adds dictionaries in the form {floor: 12, someKeys: values}
		int count = 1;
		for (String dict: arrayDicts) {
			//no global values in the inner dicts
			String[] floorValues = getKeysAndValues(dict)[1];
			String[][] keysValues = keyValToLists(floorValues);
			Map<String,String> relevantFloor = null;
			for (int i = 0; i < keysValues[0].length; i++) {
				//finding floor
				if (keysValues[0][i].equals("floor")){
					//making sure it is not double format
					String floorNo = keysValues[1][i].split("\\.")[0];
					relevantFloor = floorDict.get(floorNo);
					break;
				}
			}
			if (relevantFloor == null) {// meaning no floor keys are in the dict.
				//this should only be true for boss entries. Of witch their are multiple
				//depending on how far the user got.
				saveBossData(keysValues, count);
				count ++;
			}
			for (int j = 0; j < keysValues[0].length; j++) {
				if (!keysValues[0][j].equals("floor") && relevantFloor != null) {
					if (relevantFloor.containsKey(keysValues[0][j]) || keysValues[0][j].equals("key")) {
						addDuplicateKeys(keysValues[0][j],keysValues[1][j], relevantFloor);
					}
					else{
						relevantFloor.put(keysValues[0][j], keysValues[1][j]);
					}
				}//end outer if
			}
		}
	}
	
	private void saveBossData(String[][] keysValues, int count) {
		Map<String, String> mapFloor = null;
		if (count == 1) {//boss on floor 16
			mapFloor = floorDict.get("16");
		}
		else if (count == 2) {
			mapFloor = floorDict.get("33");
		}
		else {
			System.out.println("Higher count then expected for boss floor: " + count + "for keysValues: "+ Arrays.deepToString(keysValues));
		}
		assert(mapFloor.get("path_per_floor").equals("B"));
		if (mapFloor != null) {
			mapFloor.put(keysValues[0][0], keysValues[1][0]);
			mapFloor.put(keysValues[0][1], keysValues[1][1]);
		}
	}
	
	private void addDuplicateKeys(String key, String value, Map<String, String> floorMap) {
		if (key.equals("not_picked") || key.equals("picked")) {
			floorMap.put(key, value +","+ floorMap.get(key));
		}
		else if (value.toLowerCase().contains("potion")) {
			floorMap.put("potion_gained", value);
		}
		else if (key.equals("key") && !floorMap.containsKey("key")) {
			floorMap.put(key, value);
		}
		else {
			System.out.println(String.format("No case made for duplicat key %s whit value %s", key, value));
		}
	}

	
// for retreiving information.
	public String getGlobalKey(String key) {
		return globalVarDict.get(key);
	}
	public Map<String, String> getFloorKey(String key) {
		return floorDict.get(key);
		}

	public String[] getAllGlobalKeys() {
		Set<String> keySet = globalVarDict.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		return keyArray;
	}
	
	@Override
	/**
	 * gives the final part of the filepath back as the name of the current run.
	 */
	public String toString() {
		filePath = filePath.replace("\\", ":");
		String[] parts = filePath.split(":");
		System.out.println(parts[parts.length -1]);
		return parts[parts.length -1];
	}
	
}

