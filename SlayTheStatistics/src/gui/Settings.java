package gui;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings {
	
	public static boolean DEBUG = true;
	public static String RUN;
	public static String STS_DIRECTORY;
	public static String CHARACTER;
	
	private static ArrayList<String[]> settingInfo = new ArrayList<String[]>();
	
	/**
	 * convenience method for getting the location of the selected character
	 */
	public static String GET_CHARACTER() {
		return String.format("%s\\%s", STS_DIRECTORY, CHARACTER);
	}
	
	/**
	 * convenience method for getting the location of the selected run file
	 */
	public static String GET_RUN() {
		return String.format("%s\\%s\\%s", STS_DIRECTORY, CHARACTER, RUN);
	}
	
	/**
	 * Innitialises the static variables. They are read from a config file
	 */
	public static void loadSettings() {
		readSettings();
		saveLoadedSettings();
	}
	
	/**
	 * Reads the settings into a list of arrays. These hold the value and the 
	 * setting for each setting.
	 */
	private static void readSettings() {
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".\\src\\config.txt");       
			Scanner sc = new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read
			while(sc.hasNextLine()) {
				String[] settingValue = sc.nextLine().split("=");
				settingInfo.add(new String[]{settingValue[0].trim(), settingValue[1].trim()});
			}
			sc.close();     //closes the scanner   
		} catch(IOException e){  
			e.printStackTrace();  
		}
	}
	
	/**
	 * Saves the settings into static variables that can easily be called and 
	 * accesed
	 */
	private static void saveLoadedSettings() {
		for (String[] setVal : settingInfo) {
			String setting = setVal[0];
			String value = setVal[1];
			if (value.equals("")) value = null;
			switch (setting) {
			case "Debug":
				DEBUG = Boolean.valueOf(value);
				break;
			case "Run":
				RUN = value;
				break;
			case "Directory":
				STS_DIRECTORY = value;
				break;
			case "Character":
				CHARACTER = value;
				break;
			default:
				System.out.println("Unrecocnized setting: " + setting);
			}
		}
	}
	
	/**
	 * Saves the directory given by the user when requested during runtime
	 */
	public static void saveDirectory(String dir) {
		STS_DIRECTORY = dir;
		saveSetting("Directory", dir);
	}
	
	/**
	 * Saves the character given by the user when requested during runtime
	 */
	public static void saveCharacter(String character) {
		CHARACTER = character;
		saveSetting("Character", character);
	}
	
	/**
	 * Saves the run given by the user when requested during runtime. The run
	 * variable is the somtimes the full file path so only the last part is 
	 * selected.
	 */
	public static void saveRun(String run) {
		run = run.replace("\\", ":");
		String[] parts = run.split(":");
		String onlyRun =  parts[parts.length -1];
		RUN = onlyRun;
		saveSetting("Run", onlyRun);
	}
	
	/**
	 * Saves the changed settings in a temporary variable that will be saved when 
	 * the user closes the program
	 */
	private static void saveSetting(String set, String value) {
		for (int i = 0; i < settingInfo.size(); i++) {
			String setting = settingInfo.get(i)[0];
			if (setting.contentEquals(set)) {
				settingInfo.get(i)[1] = value;
				break;
			}
		}
	}
	
	/**
	 * this method is calles when the user closes the program and it saves the settings
	 * that might have changed during runtime.
	 */
	public static void writeSettings() {
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".\\src\\config.txt", false));
			for (int j = 0; j < settingInfo.size(); j++) {
				writer1.write(String.format("%s = %s\n", settingInfo.get(j)[0], settingInfo.get(j)[1]));
			}
		writer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
