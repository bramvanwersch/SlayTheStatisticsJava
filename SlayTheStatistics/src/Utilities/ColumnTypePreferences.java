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
	private final Class<?>[] ALLOWED_TYPES = new Class<?>[] {Integer.class, Double.class, Object.class, String.class}; 
	private List<HashSet<String>> columnNames;
	private List<HashSet<Integer>> columnIndexes;
	
	public ColumnTypePreferences() {
		this.types = new ArrayList<Class<?>>();
		this.columnNames = new ArrayList<HashSet<String>>();
		this.columnIndexes = new ArrayList<HashSet<Integer>>();
	}
	
	public void addPreference(Class<?> c, String... names) {
		checkDoubleNames(names);
		checkType(c);
		HashSet<String> setNames = new HashSet<String>(Arrays.asList(names));
		types.add(c);
		columnNames.add(setNames);
	}
	
	public void addPreference(Class<?> c, Integer... indexes) {
		checkDoubleIndexes(indexes);
		checkType(c);
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
	
	private void checkType(Class<?> type) {
		for (Class<?> c : ALLOWED_TYPES) {
			if (c == type) {
				return;
			}
		}
		throw new IllegalArgumentException(String.format("Invalid type as preference %s. It should be"
				+ " one of the following: %s", type, Arrays.toString(ALLOWED_TYPES)));
	}
	
	private void checkDoubleNames(String[] names) {
		for (int i = 0; i < columnNames.size(); i++) {
			Set<String> prefNames = columnNames.get(i);
			for (String name : names) {
				if (prefNames.contains(name)) {
					throw new IllegalArgumentException(String.format("Cannot add column %s as preference"
							+ " %s already has a preference for %s",name,name, types.get(i)));
				}
			}
		}
	}
	
	private void checkDoubleIndexes(Integer[] indexes) {
		for (int i = 0; i < columnIndexes.size(); i++) {
			Set<Integer> prefIndexes = columnIndexes.get(i);
			for (int index : indexes) {
				if (prefIndexes.contains(index)) {
					System.out.println(types.get(i));
					throw new IllegalArgumentException(String.format("Cannot add column %s as preference"
							+ " %s already has a preference for %s",index, index, types.get(i)));
				}
			}
		}
	}
}
