package global;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import run.ReadingRunFile;

public class AllRunSummary {
	private Map<String, int[]> cardRates;
	private Map<String, int[]> relicRates;
	private String[] recordedRunNames;
	
	public AllRunSummary() {
		cardRates = new HashMap<String, int[]>();
		relicRates = new HashMap<String, int[]>();
		recordedRunNames = getAlreadyProcessedRuns();
	}
	
	public ArrayList<String[]> getCharacterData(String character, boolean relic, boolean card) {
		ArrayList<String[]> relicData = null;
		ArrayList<String[]> cardData = null;
		if (relic) {
			 relicData = getCsvSummaryData(character + "_relicStats.csv");
		}
		if (card) {
			cardData = getCsvSummaryData(character + "_cardStats.csv");
		}
		if (!relic && !card) {
			System.out.println("Something went wrong while retrieving card or relic summary. None selected.");
		}
		if (relic && card) {
			return mergeSummaryData(relicData, cardData);
		}
		else if (relic) {
			return relicData;
		}
		else {
			return cardData;
		}
	}
	
	private ArrayList<String[]> getCsvSummaryData(String fileName){
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".//Data//" + fileName );       
			Scanner sc = new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read
			
			while(sc.hasNextLine()) {
				data.add(sc.nextLine().split(","));
			}
			sc.close();     //closes the scanner   
		} catch(IOException e){  
			e.printStackTrace();  
		}
		return data;
	}
	
	/**
	 * Merges 2 arrayLists of String arrays so they can be shown together in a table.
	 * @return
	 */
	private ArrayList<String[]> mergeSummaryData(ArrayList<String[]> relicData, ArrayList<String[]> cardData){
		ArrayList<String[]> finalData = new ArrayList<String[]>();
		if (cardData.size() >= relicData.size()) {
			for (int i = 0; i < cardData.size(); i++) {
				if (relicData.size() -1 > i ) {
					finalData.add(mergedArrays(cardData.get(i), relicData.get(i)));
				}
				else {
					finalData.add(mergedArrays(cardData.get(i), new String[] {"","",""}));
				}
			}
		}
		else {
			for (int i = 0; i < relicData.size(); i++) {
				if (relicData.size() -1 > i ) {
					finalData.add(mergedArrays(cardData.get(i), relicData.get(i)));
				}
				else {
					finalData.add(mergedArrays(new String[] {"","",""}, relicData.get(i)));
				}
			}
		}
		return finalData;
	}
	
	/**
	 * Merge 2 arrays a1 and a2. They are always the same length.
	 * @return merged array that is double the size 
	 */
	private String[] mergedArrays(String[] a1, String[] a2) {
		assert (a1.length == a2.length);
		String[] mergedArray = new String[a1.length + a2.length];
		int index = a1.length;

		for (int i = 0; i < a1.length; i++) {
		    mergedArray[i] = a1[i];
		}
		for (int i = 0; i < a2.length; i++) {
		    mergedArray[i + index] = a2[i];    
		}
		return mergedArray;
	}
	
	public void makeCharacterFile(String character, boolean overwrite) {
		cardRates = new HashMap<String, int[]>();
		relicRates = new HashMap<String, int[]>();
		ReadingRunFile[] runs = getCharacterRuns(character);
		if (!overwrite) {
			runs = getNewFiles(runs);
		}
		if (runs.length > 0) {
			countItemStats(runs);
			writeCsv(character);
			//ensure that the runs that where just added are added back into the file and the value has all runs.
			recordAddedRunFiles(runs);
			recordedRunNames = getAlreadyProcessedRuns();
		}
	}
	
	private String[] getAlreadyProcessedRuns() {
		ArrayList<String> runNames = new ArrayList<String>();
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".//Data//checkedRuns.txt");       
			Scanner sc=new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read  
			while(sc.hasNextLine()) {
				runNames.add(sc.nextLine());  
			}
			sc.close();     //closes the scanner  
		} catch(IOException e){  
			e.printStackTrace();  
		}    
		return runNames.toArray(new String[runNames.size()]);
	}

	private File[] getFileNames(String character) {
		File f = new File("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\" + character);
		File[] fileNames = f.listFiles();
		return fileNames;
	}
	
	/**
	 * Saves the runs that where just added to the summary in a file to prevent
	 * unecesairy calculations
	 * @param runs list ReadingRunFile classes that were just added to the 
	 * calculation.
	 */
	private void recordAddedRunFiles(ReadingRunFile[] runs) {
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".//data//checkedRuns.txt", true));
			for (int i = 0; i < runs.length; i++) {
				writer1.write(runs[i].toString() + "\n");
			}
		writer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	

	private ReadingRunFile[] getNewFiles(ReadingRunFile[] runs) {
		ArrayList<ReadingRunFile> uniqueRuns = new ArrayList<ReadingRunFile>();
		boolean match = false;
		for (int i = 0; i < runs.length; i++) {
			for (int j = 0; j < recordedRunNames.length; j++) {
				if (runs[i].toString().equals(recordedRunNames[j])) {
					match = true;
					break;
				}
			}
			if (!match) {
				uniqueRuns.add(runs[i]);
			}
			match = false;
		}
		return uniqueRuns.toArray(new ReadingRunFile[uniqueRuns.size()]);
	}

	private ReadingRunFile[] getCharacterRuns(String... characterNames) {
		ReadingRunFile[] runs = null;
		for (String name : characterNames) {
			File[] fileNames = getFileNames(name);
			runs = new ReadingRunFile[fileNames.length];
			for (int j = 0; j < fileNames.length; j++) {
				ReadingRunFile r = new ReadingRunFile(fileNames[j].toString(), false);
				runs[j] = r;
			}
		}
		return runs;
	}
	
	private void countItemStats(ReadingRunFile[] runs) {
		for (int i = 0; i < runs.length; i++) {
			String deck = runs[i].getGlobalKey("master_deck");
			String relics = runs[i].getGlobalKey("relics");
			String vict = runs[i].getGlobalKey("victory");
			String[] deckArray= getDeckArray(deck);
			String[] relicArray= getDeckArray(relics);
			boolean victory = getBoolVict(vict);
			addCardsToDict(deckArray, victory);
			addRelicsToDict(relicArray, victory);
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
	
	private void writeCsv(String character) {
		String header = "Name, wins, losses,\n";
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".//data//" + character + "_cardStats.csv", false));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(".//data//" + character + "_relicStats.csv", false));
			writer1.write(header);
			writer2.write(header);
		for (String key : cardRates.keySet().toArray(new String[cardRates.keySet().size()])) {
			writer1.write(String.format("%s,%s,%s,\n", key, cardRates.get(key)[0], cardRates.get(key)[1]));
			}
		for (String key : relicRates.keySet().toArray(new String[relicRates.keySet().size()])) {
			writer2.write(String.format("%s,%s,%s,\n", key, relicRates.get(key)[0], relicRates.get(key)[1]));
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
}
