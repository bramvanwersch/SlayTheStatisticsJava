package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame extends Matrix{
	private List<String> columnNames;
	
	public DataFrame(int cols, int rows) {
		super(cols, rows);
		this.columnNames = new ArrayList<String>(cols);
		addDefaultColumnNames(cols);
	}
	
	private void addDefaultColumnNames(int noNames) {
		for (int i = 0; i < noNames; i++) {
			columnNames.add("col" + (i + 1));
		}
	}
	
	public void addNamedColumn(Object[] data, String name) {
		checkName(name);
		columnNames.add(columns.size(), name);
		addColumn(data);
	}
	
	public void addNamedColumn(Object[] data, String name, int index) {
		checkName(name);
		columnNames.add(index, name);
		addColumn(data, index);
	}
	
	public <Any> Any get(String colName) {
		checkColumnNameExist(colName);
		return get(columnNames.indexOf(colName));
	}
	
	public <Any> Any get(String colName, int y) {
		checkColumnNameExist(colName);
		return get(columnNames.indexOf(colName), y);
	}
	
	private void checkName(String n) {
		for (String name : columnNames) {
			if (n.equals(name)) {
				throw new IllegalArgumentException("Column name " + n + " aleardy exists");
			}
		}
	}
	
	private void checkColumnNameExist(String colName) {
		if (columnNames.indexOf(colName) == -1) {
			throw new IllegalArgumentException("Column name " + colName + " does not exist");
		}
		else if (columnNames.indexOf(colName) >= columns.size()) {
			throw new IllegalArgumentException("Cannot retrieve empty value(s)");
		}
	}
}
