package global;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import gui.Settings;
import run.ReadingRunFile;

public class RunSummarys {
	private Map<String, ItemSummary> cardRates;
	private Map<String, ItemSummary> relicRates;
	private String[] recordedRunNames;
	
	public RunSummarys() {
		cardRates = new HashMap<String, ItemSummary>();
		relicRates = new HashMap<String, ItemSummary>();
		recordedRunNames = getAlreadyProcessedRuns();
	}
	
	public ArrayList<Object[]> getCharacterData(String character, boolean relic) {
		if (getNewFiles(getCharacterRuns(character)).length > 0) {
			makeCharacterSummaryFile(character);
		}
		if (relic) {
			return getCsvSummaryData(character + "_relicStats.csv");
		}
		else {
			return getCsvSummaryData(character + "_cardStats.csv");
		}
	}
	
	public ArrayList<Object[]> getCsvSummaryData(String fileName){
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".//data//" + fileName );       
			Scanner sc = new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read
			
			while(sc.hasNextLine()) {
				//add the values as integers or doubles to ensure proper sorting.
				//still some parts hardcoded that can give some trouble
				String[] s = sc.nextLine().split(",");
				Object[] o = new Object[s.length];
				o[0] = s[0].replace("\"", "");
				o[1] = Double.valueOf(s[1]);
				o[2] = Double.valueOf(s[2]);
				o[3] = Double.valueOf(s[3]);
				data.add(o);
			}
			sc.close();     //closes the scanner   
		} catch(IOException e){  
			e.printStackTrace();
		}
		return data;
	}
	
//	private ArrayList<Object[]> mergeSummaryData(ArrayList<Object[]> relicData, ArrayList<Object[]> cardData){
//		assert (relicData.get(0).length + cardData.get(0).length == 8); //check to make sure that the hard coded
//		ArrayList<Object[]> finalData = new ArrayList<Object[]>();
//		if (cardData.size() >= relicData.size()) {
//			Object[] dummyArray = new Object[relicData.get(0).length];
//			Arrays.fill(dummyArray, "");
//			for (int i = 0; i < cardData.size(); i++) {
//				if (relicData.size() -1 > i ) {
//					finalData.add(mergedArrays(cardData.get(i), relicData.get(i)));
//				}
//				else {
//					finalData.add(mergedArrays(cardData.get(i), dummyArray));
//				}
//			}
//		}
//		else {
//			Object[] dummyArray = new Object[cardData.get(0).length];
//			Arrays.fill(dummyArray, "");
//			for (int i = 0; i < relicData.size(); i++) {
//				if (relicData.size() -1 > i ) {
//					finalData.add(mergedArrays(cardData.get(i), relicData.get(i)));
//				}
//				else {
//					finalData.add(mergedArrays(dummyArray, relicData.get(i)));
//				}
//			}
//		}
//		return finalData;
//	}
//	
//	/**
//	 * Merge 2 arrays a1 and a2. They are always the same length.
//	 * @return merged array that is double the size 
//	 */
//	private Object[] mergedArrays(Object[] a1, Object[] a2) {
//		assert (a1.length == a2.length);
//		Object[] mergedArray = new Object[a1.length + a2.length];
//		int index = a1.length;
//
//		for (int i = 0; i < a1.length; i++) {
//		    mergedArray[i] = a1[i];
//		}
//		for (int i = 0; i < a2.length; i++) {
//		    mergedArray[i + index] = a2[i];    
//		}
//		return mergedArray;
//	}
	
	public void makeCharacterSummaryFile(String character) {
		ReadingRunFile[] runs = getCharacterRuns(character);
		cardRates = getExistingItemRates(character + "_cardRates.csv");
		relicRates = getExistingItemRates(character + "_relicRates.csv");
		countItemStats(runs);
		writeSummaryCsv(character);
		//ensure that the runs that where just added are added back into the file and the value has all runs.
		recordAddedRunFiles(runs);
		recordedRunNames = getAlreadyProcessedRuns();
	}
	
	/**function that creates a massive matrix of items winrate and run
	 * for R to some calculations on. 
	 */
	public void makeAllCharacterDataFile(String character) {
		ReadingRunFile[] runs = getCharacterRuns(character);
		String[][] dataMatrix = new String[runs.length + 1][];
		String[] colNames = getColumnNames(character);
		dataMatrix[0] = colNames;
		for (int i = 0; i < runs.length; i++) {
			dataMatrix[i+1] = countItemsForRun(runs[i], colNames);
		}
		writeCsv(dataMatrix, character);
	}
	
	private String[] countItemsForRun(ReadingRunFile run, String[] colNames) {
		String[] values = new String[colNames.length];
		values[0] = run.toString();
		ArrayList<String> cardsRelics = new ArrayList<String>();
		String[] deckArray = getDeckArray(run.getGlobalKey("master_deck"));
		String[] relicArray = getDeckArray(run.getGlobalKey("relics"));
		cardsRelics.addAll(Arrays.asList(deckArray));
		cardsRelics.addAll(Arrays.asList(relicArray));
		for (int i = 1; i < colNames.length - 1; i++) {
			int total = 0;
			for (int j = 0; j < cardsRelics.size(); j++) {
//				System.out.println(cardsRelics.get(j)+ "  "+ colNames[i]);
				if (cardsRelics.get(j).equals(colNames[i])) {
					total += 1;
				}
			}
			values[i] = total + "";
		}
		values[colNames.length -1] = run.getGlobalKey("victory");
		return values;
	}
	
	private String[] getColumnNames(String character){
		//the summary files should always be available before this fuinction gets called.
		ArrayList<String> names = new ArrayList<String>();
		names.add("run");
		try {  
			FileInputStream fis1=new FileInputStream(String.format(".//data//%s_cardStats.csv", character));
			FileInputStream fis2=new FileInputStream(String.format(".//data//%s_relicStats.csv", character));
			Scanner sc_cards = new Scanner(fis1);
			Scanner sc_relics = new Scanner(fis2);
			
			while(sc_cards.hasNextLine()) {
				String[] s = sc_cards.nextLine().split(",");
				names.add(s[0]);
			}
			while(sc_relics.hasNextLine()) {
				String[] s = sc_relics.nextLine().split(",");
				names.add(s[0]);
			}
			sc_cards.close();
			sc_relics.close();
		} catch(IOException e){  
			e.printStackTrace();
		}
		names.add("Win");
		return names.toArray(new String[names.size()]);
	}
	
	private Map<String, ItemSummary> getExistingItemRates(String fileLocation) {
		Map<String, ItemSummary> existingRates = new HashMap<String, ItemSummary>();
		try {  
			//the file to be opened for reading  
			FileInputStream fis=new FileInputStream(".//data//" + fileLocation);       
			Scanner sc=new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read  
			while(sc.hasNextLine()) {
				String[] info = sc.nextLine().split(",");
				existingRates.put(info[0], new ItemSummary(info[0], info[1], info[2]));  
			}
			sc.close();     //closes the scanner  
		} catch(IOException e){
			//if the file does not exist ignore it, a new file will be made
		}
		return existingRates;
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
		File f = new File(String.format("%s//%s",Settings.STS_DIRECTORY,character));
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
	
	/**
	 * Takes a set of runs and calculates the winrate of cards and relics based
	 * on the fact if a card or relic is in a winning run or not.
	 */
	private void countItemStats(ReadingRunFile[] runs) {
		for (int i = 0; i < runs.length; i++) {
			Set<String> deckArray= new HashSet<String>();
			Set<String> relicArray= new HashSet<String>();
			deckArray.addAll(Arrays.asList(getDeckArray(runs[i].getGlobalKey("master_deck"))));
			relicArray.addAll(Arrays.asList(getDeckArray(runs[i].getGlobalKey("relics"))));
			boolean victory = Boolean.valueOf(runs[i].getGlobalKey("victory"));
			addCardsToDict(deckArray, victory);
			addRelicsToDict(relicArray, victory);
		}
	}

	private void addCardsToDict(Set<String> deckArray, boolean victory) {
		for (String card: deckArray) {
			if (victory) {
				if (cardRates.containsKey(card)){
					cardRates.get(card).addWin();
				}
				else {
					cardRates.put(card, new ItemSummary(card, "1", "0"));
				}
			}
			else {
				if (cardRates.containsKey(card)){
					cardRates.get(card).addLoss();
				}
				else {
					cardRates.put(card, new ItemSummary(card, "0", "1"));
				}
			}
		}
	}
	
	private void addRelicsToDict(Set<String> relicArray, boolean victory) {
		for (String relic: relicArray) {
			if (victory) {
				if (relicRates.containsKey(relic)){
					relicRates.get(relic).addWin();
				}
				else {
					relicRates.put(relic, new ItemSummary(relic, "1", "0"));
				}
			}
			else {
				if (relicRates.containsKey(relic)){
					relicRates.get(relic).addLoss();
				}
				else {
					relicRates.put(relic, new ItemSummary(relic, "0", "1"));
				}
			}
		}
	}
	
	private void writeCsv(String[][] data, String character) {
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".//data//" + character + "_fullSummary.csv", false));
			for (int i = 0; i < data.length - 1; i++) {
				writer1.write(arrayToCsvString(data[i]));
			}
		writer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String arrayToCsvString(String[] sa) {
		String finalString = "";
		for (String s : sa) {
			finalString += s + ",";
		}
		finalString = finalString.substring(0, finalString.length() -1);
		return finalString + "\n";
	}
	
	private void writeSummaryCsv(String character) {
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(".//data//" + character + "_cardStats.csv", false));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(".//data//" + character + "_relicStats.csv", false));
		for (String key : cardRates.keySet().toArray(new String[cardRates.keySet().size()])) {
			writer1.write(cardRates.get(key).toString());
			}
		for (String key : relicRates.keySet().toArray(new String[relicRates.keySet().size()])) {
			writer2.write(relicRates.get(key).toString());
			}
		writer1.close();
		writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private String[] getDeckArray(String deck) {
		String[] deckArray = deck.replace("\"","").replace("[","").replace("]","").split(",");
		return deckArray;
	}
}
