package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private String[] addHeader() {
		//check unique names
		
		return null;
	}
	
	@Override
	public void removeColumn(int index) {
		
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
		checkName(name);
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
		checkName(name);
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
	private void checkName(String n) {
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
