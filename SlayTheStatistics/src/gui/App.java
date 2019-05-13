package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import run.Floor;
import run.STSRun;

public class App {
	private STSRun myRun;
	
	public App() {
		myRun = new STSRun("IRONCLAD","1556227492.run");
	}
	
	public Object[][] getTableData(String[] keys, String filterKey) {
		ArrayList<String[]> data = new ArrayList<String[]>(100);
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			String[] innerArray = new String[keys.length];
			innerArray[0] = "" + i;
			Map<String, String> fM = myRun.getFloorAtIndex(i).getFloorMap();
			for (int j = 1; j < keys.length; j++ ) {
				innerArray[j] = fM.get(keys[j]);
			}
			if (fM.containsKey(filterKey)) {
				data.add(innerArray);
			}
		}
		Object [][] arrayData = listToObjectArrayOArray(data,keys.length);
		return arrayData;
	}

	private Object[][] listToObjectArrayOArray(ArrayList<String[]> data, int rowLength) {
		Object[][] returnArray = new Object[data.size()][rowLength];
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < rowLength; j++) {
				returnArray[i][j] = data.get(i)[j];
			}
		}
		return returnArray;
	}

	public Object[][] getBasicRunTableData(){
		String[] keys = {"","path", "health","maxHealth","healed","gold","goldChange"};
		return getTableData(keys,"path");
	}
	
	public Object[][] getAllRunTableData() {
		String[] keys = {"","path", "health","maxHealth","healed","gold","goldChange","potionGain",
				"potionUse","picked","notPicked","relic","special"};
		return getTableData(keys, "path");
	}

	public Object[][] getSpecialRunTableData() {
		String[] keys = {"","path","picked","notPicked","relic","special"};
		return getTableData(keys, "special");
	}

	public Object[][] getCardsRunTableData() {
		String[] keys = {"","path","picked","notPicked"};
		return getTableData(keys, "picked");
	}

	public Object[][] getRelicsRunTableData() {
		String[] keys = {"","path","relic","notRelic"};
		return getTableData(keys, "relic");
	}

	public Object[][] getEncountersRunTableData() {
		String[] keys = {"","path","enemies","damage","turns","healed"};
		return getTableData(keys, "enemies");
	}

	public Object[][] getRestRunTableData() {
		String[] keys = {"","path","activity","data"};
		return getTableData(keys,"activity");
	}

	public Object[][] getEventRunTableData() {
		String[] keys = {"","path","name","choice","enemies","damage","turns","healed","relic","picked","cardRemoved","cardUpgraded"};
		return getTableData(keys,"name");
	}

	public Object[][] getShopRunTableData() {
		String[] keys = {"","path","purchased","purged"};
		return getTableData(keys, "shop");
	}
	
	public int[][] getIntGraphValuesHealth(){
		int[][] dataValues = new int[2][myRun.getFloorTotal()];
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			int health = myRun.getFloorAtIndex(i).getHealth();
			dataValues[1][i] = health;
			dataValues[0][i] = i;
		}
		return dataValues;
	}
	
	public int[][] getIntGraphValuesGold(){
		int[][] dataValues = new int[2][myRun.getFloorTotal()];
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			int health = myRun.getFloorAtIndex(i).getGold();
			dataValues[1][i] = health;
			dataValues[0][i] = i;
		}
		return dataValues;
	}
	
	public int[][] getIntGraphValuesMaxHp(){
		int[][] dataValues = new int[2][myRun.getFloorTotal()];
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			int health = myRun.getFloorAtIndex(i).getMaxHp();
			dataValues[1][i] = health;
			dataValues[0][i] = i;
		}
		return dataValues;
	}
	
	public int[][] getIntGraphValuesHealed(){
		int[][] dataValues = new int[2][myRun.getFloorTotal()];
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			int health = myRun.getFloorAtIndex(i).getHealed();
			dataValues[1][i] = health;
			dataValues[0][i] = i;
		}
		return dataValues;
	}

	public String[] getTextValues() {
		//add the cards and relics in a reasonable fassion.
		String[] textV = new String[8];
		String[] keys = {"character_chosen","master_decks","ascension_level","relicss","victory","floor_reached",
				"seed_played","special_seed"};
		for (int i = 0; i < keys.length; i++) {
			textV[i] = myRun.getGlobalKeys(keys[i]);
		}
		return textV;
	}

}
