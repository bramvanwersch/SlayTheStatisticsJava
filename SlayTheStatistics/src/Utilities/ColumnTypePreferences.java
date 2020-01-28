package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class for noting down the preference of the column types for a list of 
 * values that are strings or objects. This object lets the user specify 
 * how to interpret certain pieces of data.
 * @author Bram
 *
 */
public class ColumnTypePreferences {
	private List<Class<?>> types;
	private List<HashSet<String>> columnNames;
	private List<HashSet<Integer>> columnIndexes;
	
	public ColumnTypePreferences() {
		this.types = new ArrayList<Class<?>>();
		this.columnNames = new ArrayList<HashSet<String>>();
		this.columnIndexes = new ArrayList<HashSet<Integer>>();
	}
	
	public void addPreference(Class<?> c, String... names) {
		checkDoubleNames(names);
		HashSet<String> setNames = new HashSet<String>(Arrays.asList(names));
		types.add(c);
		columnNames.add(setNames);
	}
	
	public void addPreference(Class<?> c, Integer... indexes) {
		checkDoubleIndexes(indexes);
		HashSet<Integer> setIndexes = new HashSet<Integer>(Arrays.asList(indexes));
		types.add(c);
		columnIndexes.add(setIndexes);
	}
	
	public Integer[] getIntegerIndexes() {
		ArrayList<Integer> temp1 = new ArrayList<Integer>();
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i) == Integer.class) {
				temp1.addAll(columnIndexes.get(i));
			}
		}
		return temp1.toArray(new Integer[temp1.size()]);
	}
	
	public void namesToIndex(List<String> colNames) {
		
	}
	
	private void checkDoubleNames(String[] names) {
		for (int i = 0; i < columnNames.size(); i++) {
			Set<String> prefNames = columnNames.get(i);
			for (String name : names) {
				if (prefNames.contains(name)) {
					throw new IllegalArgumentException(String.format("Cannot add column %s as prefernce"
							+ " %s already has a preference for class %s",name,name, types.get(i)));
				}
			}
		}
	}
	
	private void checkDoubleIndexes(int[] indexes) {
		for (int i = 0; i < columnIndexes.size(); i++) {
			Set<Integer> prefIndexes = columnIndexes.get(i);
			for (int index : indexes) {
				if (prefIndexes.contains(index)) {
					throw new IllegalArgumentException(String.format("Cannot add column %s as prefernce"
							+ " %s already has a preference for class %s",index, index, types.get(i)));
				}
			}
		}
	}
	
	private void checkDoubleIndexes(Object[] indexes) {
		Set<Object> s = new HashSet<Object>();
		if (s.size() != indexes.length) {
			throw new IllegalArgumentException("");
		}
	}

}
