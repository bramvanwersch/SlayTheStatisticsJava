package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Settings {
	
	public static boolean DEBUG = true;
	public static String RUN;
	public static String STS_DIRECTORY;

	
	public Settings() {
		loadSettings();
	}
	
	private void loadSettings() {
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".//config.txt");       
			Scanner sc = new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read
			
			while(sc.hasNextLine()) {
				//add the values as integers or doubles to ensure proper sorting.
				//still some parts hardcoded that can give some trouble
				String[] settingValue = sc.nextLine().split("=");
				loadSetting(settingValue[0].trim(), settingValue[1].trim());
			}
			sc.close();     //closes the scanner   
		} catch(IOException e){  
			e.printStackTrace();  
		}
	}
	
	private void loadSetting(String setting, String value) {
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
		default:
			System.out.println("Unrecocnized setting: " + setting);
		}
	}
}
