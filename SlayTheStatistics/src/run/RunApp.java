package run;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import floors.Floor;
import gui.App;
import gui.FileChooser;
import gui.Settings;

public class RunApp extends App{
	private STSRun myRun;
	
	public RunApp() {
		checkRun();
		//hardcoded prevent nullpointers but needs to change.
		myRun = new STSRun(Settings.GET_RUN());
	}
	
	/**
	 * Checks if the currently saved run is valid. If that is not the case it 
	 * asks the user to select a run.
	 */
	private void checkRun() {
		try {  
			//if te file does not exist it will error and trigger the program to ask for a path
			File f = new File(Settings.GET_RUN());
			if (!f.exists()) {
				String filePath = FileChooser.open(Settings.GET_CHARACTER(), "Please choose a run file.", false);
				Settings.saveRun(filePath);
			}
		} catch(NullPointerException e){
			String filePath = FileChooser.open(Settings.GET_CHARACTER(), "Please choose a run file.", false);
			Settings.saveRun(filePath);
		}
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
		Settings.saveRun(runName);
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
