package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	private List<HashSet<Integer>> columnIndexes;
	
	public ColumnTypePreferences() {
		this.types = new ArrayList<Class<?>>();
		this.columnIndexes = new ArrayList<HashSet<Integer>>();
	}
	
	public void addPreference(Class<?> type, Integer... indexes) {
		checkDoubleIndexes(indexes);
		checkType(type);
		HashSet<Integer> setIndexes = new HashSet<Integer>(Arrays.asList(indexes));
		if (types.contains(type)) {
			columnIndexes.get(types.indexOf(type)).addAll(Arrays.asList(indexes));
		}
		else {
			types.add(type);
			columnIndexes.add(setIndexes);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] getIndexes(Class<?> type) {
		checkType(type);
		ArrayList<T> temp1 = new ArrayList<T>();
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i) == type) {
				temp1.addAll((Collection<T>) columnIndexes.get(i));
			}
		}
		return temp1.toArray((T[]) new Object[temp1.size()]);
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
	
	private void checkDoubleIndexes(Integer[] indexes) {
		for (int i = 0; i < columnIndexes.size(); i++) {
			Set<Integer> prefIndexes = columnIndexes.get(i);
			for (int index : indexes) {
				if (prefIndexes.contains(index)) {
					throw new IllegalArgumentException(String.format("Cannot add column %s as preference"
							+ " %s already has a preference for %s",index, index, types.get(i)));
				}
			}
		}
	}
}
