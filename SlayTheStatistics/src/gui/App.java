package gui;

import java.io.File;
import java.util.ArrayList;

public class App {
	
	public String[] getCharacterNames() {
		//hardcoded and needs to change.
		File[] folder = new File(Settings.STS_DIRECTORY).listFiles();
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < folder.length; i++) {
			String folderName = folder[i].getName();
			if (folderName.equals("metrics") || folderName.equals("DAILY")) {
				continue;
			}
			names.add(folderName);
		}
		return names.toArray(new String[names.size()]);
	}
	
	protected Object[][] listToObjectArrayOfArray(ArrayList<Object[]> data) {
		int rowLength = data.get(0).length;
		Object[][] returnArray = new Object[data.size()][rowLength];
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < rowLength; j++) {
				returnArray[i][j] = data.get(i)[j];
			}
		}
		return returnArray;
	}
}
