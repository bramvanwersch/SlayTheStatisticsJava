package global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import gui.App;

public class GlobalApp extends App{
	private AllRunSummary runSummary;
	private final List<String> cardColumnNames = Arrays.asList(new String[] {"card", "win", "loss", "winrate"});
	private final List<String> relicColumnNames = Arrays.asList(new String[] {"relic", "win", "loss", "winrate"});
	
	public GlobalApp() {
		runSummary = new AllRunSummary();
	}

	/**
	 * Function that retrieves the summary information of a character and
	 * returns a tableModel. If no information is available it is generated 
	 * when needed.
	 * @param character name of the character for the summary (this is passed
	 * on for consistency sake making sure the character cant change halfway 
	 * trough retrieving the information).
	 * @param relic if true returns the relic info if false returns the card info.
	 */
	public GlobalTableModel getSummaryTableData(String character, boolean relic) {
		ArrayList<Object[]> d = runSummary.getCharacterData(character, relic);
		Object[][] data = listToObjectArrayOfArray(d);
		ArrayList<String> columnNames = new ArrayList<String>();
		if (relic) {
			columnNames.addAll(relicColumnNames);
		}
		else {
			columnNames.addAll(cardColumnNames);
		}
		return new GlobalTableModel(data,columnNames.toArray(new String[columnNames.size()]),1,2,3);
	}

	public void getFullFile(String character) {
		runSummary.makeAllCharacterDataFile(character);
	}

	/**
	 * Requests Rscript to run a rfile to get random  forest predictions for the cards,
	 * relics and together. It saves the data in a csv file that shows the most
	 * important predictors for each of the categories.
	 */
	public void runRTest() {
		BufferedReader reader = null;
        Process shell = null;
        try {
            shell = Runtime.getRuntime().exec("D:\\R-3.6.1\\bin\\i386\\Rscript.exe .\\src\\global\\getForestModel.R");

            reader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
            //wait untill the program is done.
            String line;
            while ((line = reader.readLine()) != null) {
            	System.out.println(line);
            }
            System.out.println("done with making forests");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
