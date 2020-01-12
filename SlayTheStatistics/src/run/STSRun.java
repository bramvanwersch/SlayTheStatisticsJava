package run;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import floors.BossFloor;
import floors.ChestFloor;
import floors.EliteFloor;
import floors.EventFloor;
import floors.Floor;
import floors.GeneralFloor;
import floors.MonsterFloor;
import floors.RestFloor;
import floors.ShopFloor;
import floors.StartFloor;

public class STSRun {
	//probably should not hold this dict but extending classes should hold the seperate smaller dicts.
	private ReadingRunFile myRunFile;
	private Floor[] floorArray;

	public STSRun(String runFileLocation) {
		myRunFile = new ReadingRunFile(runFileLocation, true);
		floorArray = new Floor[Integer.parseInt(myRunFile.getGlobalKey("floor_reached")) + 1];
//		try {
//			writeFloorDict();
//			writeGlobalDict();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		assignFloors();
	}
	
	public void assignFloors() {
		GeneralFloor myFloor = null;
		Map<String, String> floorDict;
		Floor previousFloor = new StartFloor(myRunFile.getGlobalKey("character_chosen"),
				myRunFile.getGlobalKey("neow_cost"), myRunFile.getGlobalKey("neow_bonus"),
				myRunFile.getGlobalKey("ascension_level"));
//		System.out.println(previousFloor.getText());
		floorArray[0] = previousFloor;
		for (int i = 1; i < Integer.parseInt(myRunFile.getGlobalKey("floor_reached")) + 1; i++) {
			floorDict = myRunFile.getFloorKey(""+i);
			switch( floorDict.get("path_per_floor")) {
			case "M":
				myFloor = new MonsterFloor(floorDict, previousFloor, i+"");
				break;
			case "E":
				myFloor = new EliteFloor(floorDict, previousFloor, i+"");
				break;
			case "R":
				myFloor = new RestFloor(floorDict, previousFloor, i+"");
				break;
			case "?":
				myFloor = new EventFloor(floorDict, previousFloor, i+"");
				break;
			case "T":
				myFloor = new ChestFloor(floorDict, previousFloor, i+"");
				break;
			case "$":
				myFloor = new ShopFloor(floorDict, previousFloor, i+"");
				break;
			case "B":
				myFloor = new BossFloor(floorDict, previousFloor, i+"");
				break;
			case "null":
				myFloor = new GeneralFloor(floorDict, previousFloor, i+"");
				break;
			}//end switch
			floorArray[i] = myFloor;
			previousFloor = myFloor;
		}//end for
	}
	
	public int getFloorTotal() {
		return floorArray.length;
	}
	
	public Floor getFloorAtIndex(int index) {
		return floorArray[index];
	}
	
	public String getGlobalKeys(String key) {
		return myRunFile.getGlobalKey(key);
	}
	
	public String getRunName() {
		return myRunFile.toString();
	}
	
	//temporary
	public void writeFloorDict() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(".//data//floorDict.txt", false));
		for (int i = 1; i < Integer.parseInt(myRunFile.getGlobalKey("floor_reached")); i++) {
			writer.write(i + ": " + myRunFile.getFloorKey(i+"").toString()+ "\n");
			}
		writer.close();
	}
	
	public void writeGlobalDict() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(".//data//GlobalDict.txt", false));
		for (String key: myRunFile.getAllGlobalKeys()) {
			writer.write(key + ": " + myRunFile.getGlobalKey(key) +"\n");
			}
		writer.close();
	}
}
