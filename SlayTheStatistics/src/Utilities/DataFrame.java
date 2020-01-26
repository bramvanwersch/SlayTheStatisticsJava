package Utilities;

import java.util.ArrayList;

public class DataFrame extends Matrix{
	private ArrayList<String> columnNames;
	
	public DataFrame(int cols, int rows) {
		super(cols, rows);
		this.columnNames = new ArrayList<String>(cols);
	}
	
	public void addColumn(Object[] data, String name) {
		checkName(name);
		columnNames.add(name);
		addColumn(data);
	}
	
	public void addColumn(Object[] data, String name, int index) {
		checkName(name);
		columnNames.add(index, name);
		addColumn(data, index);
	}
	
	public <Any> Any get(String colName) {
		return get(columnNames.indexOf(colName));
	}
	
	public <Any> Any get(String colName, int y) {
		return get(columnNames.indexOf(colName), y);
	}
	
	private void checkName(String n) {
		for (String name : columnNames) {
			if (n.equals(name)) {
				throw new IllegalArgumentException("column name aleardy exists");
			}
		}
	}
}
