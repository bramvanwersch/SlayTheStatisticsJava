package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import global.AllRunSummary;

public class GlobalApp extends App{
	private AllRunSummary runSummary;
	private final List<String> cardColumnNames = Arrays.asList(new String[] {"card", "win", "loss", "winrate"});
	private final List<String> relicColumnNames = Arrays.asList(new String[] {"relic", "win", "loss", "winrate"});
	
	public GlobalApp() {
		runSummary = new AllRunSummary();
	}

	public void calculateCharacterSummary(String character) {
		runSummary.makeCharacterFile(character, false);
	}

	public DefaultTableModel getSummaryTableData(String character, boolean relic, boolean card) {
		ArrayList<String[]> d = runSummary.getCharacterData(character, relic, card);
		Object[][] data = listToObjectArrayOfArray(d);
		ArrayList<String> columnNames = new ArrayList<String>();
		if (card) {
			columnNames.addAll(cardColumnNames);
		}
		if (relic) {
			columnNames.addAll(relicColumnNames);
		}
		return new DefaultTableModel(data,columnNames.toArray(new String[columnNames.size()]));
	}

}
