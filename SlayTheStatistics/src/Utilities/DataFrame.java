package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Is a matrix with named columns. The names of each column are created in advance to allow the user
 * to introduce unnamed columns.
 * @author Bram van Wersch
 *
 */
public class DataFrame extends Matrix{
	private List<String> columnNames;
	
	public DataFrame(int cols, int rows) {
		super(cols, rows);
		this.columnNames = new ArrayList<String>(cols);
		addDefaultColumnNames(size[0]);
	}
	
	public DataFrame(String values, String sep, boolean header) {
		super(values, sep);
		this.columnNames = new ArrayList<String>();
		if (header) {
			addHeader();
		}
		else {
			addDefaultColumnNames(size[0]);
		}
	}
	
	/**
	 * for replacing a column by name
	 * @param name of the column
	 * @param data to replace it with.
	 */
	public void replaceColumn(String name, Object[] data) {
		checkColumnNameExist(name);
		replaceColumn(columnNames.indexOf(name), data);
	}
	
	/**
	 * combination for replacing name and data of a column
	 * @param oldName the name to replace
	 * @param newName the name to change it to.
	 * @param data to replace it with.
	 */
	public void replaceColumn(String oldName, String newName, Object[] data) {
		replaceColumnName(oldName, newName);
		replaceColumn(newName, data);
	}
	
	/**
	 * change a columnName
	 * @param oldName the name to replace
	 * @param newName the name to change it to.
	 */
	public void replaceColumnName(String oldName, String newName) {
		checkColumnNameExist(oldName);
		checkDoubleName(newName);
		columnNames.set(columnNames.indexOf(oldName), newName);
	}
	
	/**
	 * Takes the first row of the matrix and assignes the column names to it.
	 * Then removes the row.
	 */
	private void addHeader() {
		String[] header = getRow(0);
		Set<String> setHeader = new HashSet<String>();
		setHeader.addAll(Arrays.asList(header));
		if (setHeader.size() != header.length) {
			throw new IllegalArgumentException("Header contains duplicate values. Cannot create the dataframe.");
		}
		removeRow(0);
		this.columnNames.addAll(Arrays.asList(header));
	}
	
	/**
	 * removes a column by its name
	 * @param colName of the column
	 */
	public void removeColumn(String colName) {
		checkColumnNameExist(colName);
		removeColumn(columnNames.indexOf(colName));
	}
	
	/**
	 * Overrides the Matrix method and simply removes the columnName aswell.
	 */
	@Override
	public void removeColumn(int index) {
		checkColumnIndex(index);
		columns.remove(index);
		columnNames.remove(index);
		this.size[0] -= 1;
	}
	
	/**
	 * Generates default names for each column numbering from 1 to noNames
	 * @param noNames number of columns as defined by the user when creating the data frame.
	 */
	private void addDefaultColumnNames(int noNames) {
		for (int i = 0; i < noNames; i++) {
			columnNames.add("col" + i);
		}
	}
	
	/**
	 * Adds a named column to the matrix. Uses matrix metods to add the column
	 * @see Matrix.add()
	 * @param data to be added
	 * @param name of the column
	 */
	public void addNamedColumn(Object[] data, String name) {
		checkDoubleName(name);
		columnNames.add(columns.size(), name);
		addColumn(data);
	}
	
	/**
	 * Adds a named column to the matrix at a specific index. Uses matrix metods to add the column
	 * @see Matrix.add()
	 * @param data to be added
	 * @param name of the column
	 * @param index of the column to be added.
	 */
	public void addNamedColumn(Object[] data, String name, int index) {
		checkDoubleName(name);
		columnNames.add(index, name);
		addColumn(data, index);
	}
	
	/**
	 * implementation of the get method of the matrix for a columnName. Will raise 
	 * an IllegalArgumentException if no such column name exists or the column does
	 * not contain data.
	 * @see Matrix.get()
	 * @param colName the name of the column.
	 * @return an array of the downcasted type of the column
	 */
	public <Any> Any getColumn(String colName) {
		checkColumnNameExist(colName);
		return getColumn(columnNames.indexOf(colName));
	}
	
	/**
	 * implementation of the get method of the matrix for a columnName. Will raise 
	 * an IllegalArgumentException if no such column name exists or the column does
	 * not contain data.
	 * @see Matrix.get()
	 * @param colName the name of the column.
	 * @return a value of the downcasted value in the column.
	 */
	public <Any> Any getValue(String colName, int y) {
		checkColumnNameExist(colName);
		return getValue(columnNames.indexOf(colName), y);
	}
	
	/**
	 * Method to make sure there are no double names in the columns to prevent
	 * unwanted behaviour
	 * @param n the name to be checked.
	 */
	private void checkDoubleName(String n) {
		for (String name : columnNames) {
			if (n.equals(name)) {
				throw new IllegalArgumentException("Column name " + n + " aleardy exists");
			}
		}
	}
	
	/**
	 * Check if the column name is present in the list of columnNames
	 * @param colName the name of the column
	 */
	private void checkColumnNameExist(String colName) {
		if (columnNames.indexOf(colName) == -1) {
			throw new IllegalArgumentException("Column name " + colName + " does not exist");
		}
	}
}
