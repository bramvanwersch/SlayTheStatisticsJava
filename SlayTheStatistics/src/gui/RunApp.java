package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import floors.Floor;
import run.STSRun;

public class RunApp extends App{
	private STSRun myRun;
	
	public RunApp() {
		this.myRun = null;
		//hardcoded prevent nullpointers but needs to change.
		myRun = new STSRun("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\IRONCLAD\\1556227492.run");
	}
	
	public Object[][] getTableData(String[] keys, String filterKey) {
		ArrayList<Object[]> data = new ArrayList<Object[]>(100);
		for (int i = 0; i < myRun.getFloorTotal(); i++) {
			String[] innerArray = new String[keys.length];
			innerArray[0] = "" + i;
			Map<String, String> fM = myRun.getFloorAtIndex(i).getFloorMap();
			for (int j = 1; j < keys.length; j++ ) {
				innerArray[j] = fM.get(keys[j]);
			}
			//key that determines if a certain floor can be selected and used.
			if (fM.containsKey(filterKey)) {
				data.add(innerArray);
			}
		}
		Object [][] arrayData = listToObjectArrayOfArray(data);
		return arrayData;
	}
	
	public void setRun(String runName) {
		myRun = new STSRun(runName);
	}
	
	/**
	 * Get the name of the current run loaded in. If no run is loaded in return
	 * an empty string.
	 * @return String
	 */
	public String getRun() {
		if (myRun == null) {
			return "";
		}
		return myRun.getRunName();
	}
	
	public DefaultTableModel getTableModel(String[] keys, String filter, Object[] columnNames) {
		Object[][] data = getTableData(keys, filter);
		return new DefaultTableModel(data,columnNames);
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
