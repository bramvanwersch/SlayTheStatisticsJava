package Utilities;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	protected List<Column> columns;
	private int[] size;
	
	public Matrix(int cols, int rows) {
		columns = new ArrayList<Column>(cols);
		this.size = new int[] {cols, rows};
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
	public <Any> Any get(int x, int y) {
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
	public <Any> Any get(int x) {
		checkColumnIndex(x);
		checkColumnContainsData(x);
		return (Any) columns.get(x).get();
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
	private void checkColumnIndex(int x){
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
