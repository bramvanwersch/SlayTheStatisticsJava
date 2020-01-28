package Utilities;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	protected List<Column> columns;
	protected int[] size;
	private ColumnTypePreferences ctp;
	
	public Matrix(int cols, int rows) {
		columns = new ArrayList<Column>(cols);
		this.ctp = new ColumnTypePreferences();
		this.size = new int[] {cols, rows};
	}
	
	public Matrix(String values, String sep) {
		columns = new ArrayList<Column>();
		this.ctp = new ColumnTypePreferences();
		separatedValuesToMatrix(values, sep);
	}
	
	/**
	 * Sets a columntypepreference for every index in this matrix. This means that when columns 
	 * are loaded consisting of strings or objects they can be converted into a
	 * defined set of types. This has to be done before data is loaded and is 
	 * made for seperated data not neccesairily for loading single columns.
	 * @param type
	 * @param indexes
	 */
	public void setColumnTypePreference(Class<?> type, Integer... indexes) {
		this.ctp.addPreference(type, indexes);
	}
	
	/**
	 * replaces a column at the given index
	 * @param x is the index
	 * @param data the data to replace it with
	 */
	public void replaceColumn(int x, Object[] data) {
		checkColumnIndex(x);
		checkRowSize(data.length);
		columns.get(x).setData(data);
	}
	
	/**
	 * Takes a string of values that are separated by some sort of seperator
	 * and converts them into a matrix.
	 * @param values as one big string
	 * @param sep as seperator for the values.
	 */
	private void separatedValuesToMatrix(String values, String sep) {
		String[] rows = values.split("\n");
		int noColumns = rows[0].split(sep).length;
		String[][] strColumns = new String[noColumns][rows.length];
		for (int i = 0; i < rows.length; i++) {
			String[] rowValues = rows[i].split(sep);
			if (rowValues.length != noColumns) {
				throw new IllegalArgumentException("Cannot process properly. Rows have different lengths");
			}
			for (int j = 0; j < rowValues.length; j++) {
				strColumns[j][i] = rowValues[j];
			}
		}
		//make sure size is set before.
		this.size = new int[] {noColumns, rows.length};
		for (String[] column : strColumns) {
			addColumn(column);
		}
	}
	
	/**
	 * Get the dimensions of the matrix
	 * @return an array containing column and row size in that order.
	 */
	public int[] dimensions() {
		return this.size;
	}
	
	/**
	 * removes the value at index for each column resulting in a row being removed.
	 * @param index where the value has to be removed.
	 */
	public void removeRow(int index) {
		checkRowIndex(index);
		for (Column column : columns) {
			column.remove(index);
		}
		this.size[1] -= 1;
	}
	
	/**
	 * Function for removing a column at index
	 * @param index of the column to be removed.
	 */
	public void removeColumn(int index) {
		checkColumnIndex(index);
		columns.remove(index);
		this.size[0] -= 1;
	}
	
	/**
	 * Adds a column class to the matrix if the maximum amount of columns
	 * was not reached yet.
	 * @param data to add to the column
	 */
	public void addColumn(Object[] data) {
		checkColumnSize();
		addColumn(data, columns.size());
	}
	
	/**
	 * Adds a column class to the matrix at a given index if the maximum
	 * amount of columns was not reached yet.
	 * @param data to add to the column
	 * @param index of the column to be added to.
	 */
	public void addColumn(Object[] data, int index) {
		checkColumnSize();
		columns.add(index, new Column(size[1]));
		addColumnData(index, data);
	}
	
	/**
	 * adds an array of objects to a column.
	 * @param index the index of the column
	 * @param data object array containing any class to be added to the column.
	 */
	private void addColumnData(int index, Object[] data) {
		checkRowSize(data.length);
		columns.get(index).setData(data);
	}
	
	/**
	 * Returns the value at position x,y in the matrix. If the requested type cannot be returned 
	 * a ClassCastException will be raised. An IllegalArgumentException will be raised if the 
	 * column does not contain data.
	 * @param x column of the matrix
	 * @param y row of the matrix
	 * @return the value in the requested type. 
	 */
	@SuppressWarnings("unchecked")
	public <Any> Any getValue(int x, int y) {
		checkCoordinates(x, y);
		checkColumnContainsData(x);
		return (Any) columns.get(x).get(y);
	}
	
	/**
	 * Returns a column by index as an array of the given type. If the requested type cannot
	 * be returned a ClassCastException will be raised. An IllegalArgumentException will be
	 * raised if the column does not contain data.
	 * @param x the column to be returned
	 * @return a array of the requested type.
	 */
	@SuppressWarnings("unchecked")
	public <Any> Any getColumn(int x) {
		checkColumnIndex(x);
		checkColumnContainsData(x);
		return (Any) columns.get(x).get();
	}
	
	/**
	 * Give the row at a given index as an array of strings. Because no consistent
	 * data type can be guaranteed a String array is returned
	 * @param y is the index of the row.
	 * @return
	 */
	public String[] getRow(int y) {
		checkRowIndex(y);
		String[] row = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			row[i] =  columns.get(i).get(y).toString();
		}
		return row;
	}
	
	/**
	 * Checks if a column can be added based on the lenght defined by the matrix.
	 */
	private void checkColumnSize() {
		if (columns.size() >= size[0]) {
			throw new IndexOutOfBoundsException("Tried to add one column to many"
					+ " max column size is " + size[0]);
		}
	}
	
	/**
	 * checks if the column that is being added is of the right size. All rows need to be the same size
	 * @param l the lenght of the column being added.
	 */
	private void checkRowSize(int l) {
		if (l != size[1]) {
			throw new IndexOutOfBoundsException("Column data is to long or to short."
					+ " Expected lenght is " + size[1] + " got " + l);
		}
	}
	
	/**
	 * Checks if the column contains data to return.
	 * @param colName
	 */
	private void checkColumnContainsData(int index) {
		if (index >= columns.size()) {
			throw new NoDataException("Cannot retrieve empty value(s)");
		}
	}
	
	/**
	 * checks if the given coordinates are within the matrix size
	 * @param x the column number
	 * @param y the row number
	 */
	private void checkCoordinates(int x, int y) {
		checkColumnIndex(x);
		checkRowIndex(y);
	}
	
	/**
	 * checks if the column index is within the matrix size
	 * @param x the column number
	 */
	protected void checkColumnIndex(int x){
		if (x >= size[0] || x < 0) {
			throw new IndexOutOfBoundsException(String.format("No sutch column %s in matrix of dimensions [%s,%s]",
					x, size[0], size[1]));
		}
	}
	
	/**
	 * checks if the row index is within the matrix size
	 * @param y the row number
	 */
	private void checkRowIndex(int y){
		if (y >= size[1] || y < 0) {
			throw new IndexOutOfBoundsException(String.format("No sutch row %s in matrix of dimensions [%s,%s]",
					y, size[0], size[1]));
		}
	}
}
