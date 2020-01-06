package global;

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
	 * 
	 * @param character
	 * @param relic
	 * @return
	 */
	public GlobalTableModel getSummaryTableData(String character, boolean relic) {
		//weird solution that needs to make sure that when the info is not returned because the file is created
		//the file is requested again.
		ArrayList<Object[]> d = runSummary.getCharacterData(character, relic);
		if (d == null) {
			d = runSummary.getCharacterData(character, relic);
		}
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

}
