package global;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import run.ReadingRunFile;

public class AllRunSummary {
	private final String[] characterNames = {"IRONCLAD","THE_SILENT","DEFECT"};
	private ArrayList<ArrayList<ReadingRunFile>> allRunFiles;
	private Map<String, int[]> cardRates;
	private Map<String, int[]> relicRates;
	
	
	public AllRunSummary() {
		allRunFiles = new ArrayList<ArrayList<ReadingRunFile>>();
		cardRates = new HashMap<String, int[]>();
		relicRates = new HashMap<String, int[]>();
	}
	
	public File[] getFileNames(String character) {
		File f = new File("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\" + character);
		File[] fileNames = f.listFiles();
		return fileNames;
	}
	
	public void getRunFileClasses() {
		String separator = "\\";
		for (int i = 0; i < characterNames.length; i++) {
			File[] fileNames = getFileNames(characterNames[i]);
			ArrayList<ReadingRunFile> characterRunFiles = new ArrayList<ReadingRunFile>();
			for (int j = 0; j < fileNames.length; j++) {
				String[] dirParts = fileNames[j].toString().split(Pattern.quote(separator));
				String name = dirParts[dirParts.length-1];
				ReadingRunFile r = new ReadingRunFile(characterNames[i], name, false);
				characterRunFiles.add(j, r);
			}
			allRunFiles.add(i, characterRunFiles);
		}
	}
	
	public void countCardStats() {
		for (int i = 0; i < allRunFiles.size(); i++) {
			for (int j =0; j < allRunFiles.get(i).size(); j++) {
				String deck = allRunFiles.get(i).get(j).getGlobalKey("master_deck");
				String vict = allRunFiles.get(i).get(j).getGlobalKey("victory");
				String[] deckArray= getDeckArray(deck);
				boolean victory = getBoolVict(vict);
				addCardsToDict(deckArray, victory);
			}
		}
	}
	
	public void countRelicStats() {
		for (int i = 0; i < allRunFiles.size(); i++) {
			for (int j =0; j < allRunFiles.get(i).size(); j++) {
				String relics = allRunFiles.get(i).get(j).getGlobalKey("relics");
				String vict = allRunFiles.get(i).get(j).getGlobalKey("victory");
				String[] relicArray= getDeckArray(relics);
				boolean victory = getBoolVict(vict);
				addRelicsToDict(relicArray, victory);
			}
		}
	}

	private void addCardsToDict(String[] deckArray, boolean victory) {
		for (String card: deckArray) {
			if (victory) {
				if (cardRates.containsKey(card)){
					cardRates.put(card, new int[] {cardRates.get(card)[0] + 1, cardRates.get(card)[1]});
				}
				else {
					cardRates.put(card, new int[] {1,0});
				}
			}
			else {
				if (cardRates.containsKey(card)){
					cardRates.put(card, new int[] {cardRates.get(card)[0], cardRates.get(card)[1] + 1});
				}
				else {
					cardRates.put(card, new int[] {0,1});
				}
			}
		}
	}
	
	private void addRelicsToDict(String[] relicArray, boolean victory) {
		for (String relic: relicArray) {
			if (victory) {
				if (relicRates.containsKey(relic)){
					relicRates.put(relic, new int[] {relicRates.get(relic)[0] + 1, relicRates.get(relic)[1]});
				}
				else {
					relicRates.put(relic, new int[] {1,0});
				}
			}
			else {
				if (relicRates.containsKey(relic)){
					relicRates.put(relic, new int[] {relicRates.get(relic)[0], relicRates.get(relic)[1] + 1});
				}
				else {
					relicRates.put(relic, new int[] {0,1});
				}
			}
		}
		
	}
	
	public void mapsToCsv() {
		String header = "Name, wins, losses,\n";
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".//data//cardRates.csv", false));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(".//data//relicRates.csv", false));
			writer1.write(header);
			writer2.write(header);
		for (String key : getAllCardRateKeys()) {
			writer1.write(String.format("%s,%s,%s,\n", key, getCardRates(key)[0], getCardRates(key)[1]));
			}
		for (String key : getAllRelicRateKeys()) {
			writer2.write(String.format("%s,%s,%s,\n", key, getRelicRates(key)[0], getRelicRates(key)[1]));
			}
		writer1.close();
		writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private boolean getBoolVict(String vict) {
		if (vict.equals("true")) {
			return true;
		}
		return false;
	}

	private String[] getDeckArray(String deck) {
		String[] deckArray= null;
		deckArray = deck.replace("\"","").replace("[","").replace("]","").split(",");
		return deckArray;
	}
	
	public int[] getCardRates(String key){
		return this.cardRates.get(key);
	}
	
	public String[] getAllCardRateKeys() {
		return cardRates.keySet().toArray(new String[cardRates.keySet().size()]);
	}

	public int[] getRelicRates(String key){
		return this.relicRates.get(key);
	}
	
	public String[] getAllRelicRateKeys() {
		return this.relicRates.keySet().toArray(new String[relicRates.keySet().size()]);
	}
	

}
