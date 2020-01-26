package Utilities;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
	private List<Column> columns;
	private int[] size;
	
	public Matrix(int cols, int rows) {
		columns = new ArrayList<Column>();
		this.size = new int[] {cols, rows};
	}
	
	public void addColumn(Object[] data) {
		addColumn(data, columns.size());
	}
	
	public void addColumn(Object[] data, int index) {
		checkColumnSize();
		columns.add(index, new Column(size[1]));
		addColumnData(index, data);
	}
	
	private void addColumnData(int index, Object[] data) {
		checkRowSize(data.length);
		columns.get(index).setData(data);
	}
	
	/**
	 * Returns the value at position x,y in the matrix. If the requested type cannot be returned 
	 * a ClassCastException will be raised.
	 * @param x column of the matrix
	 * @param y row of the matrix
	 * @return the value in the requested type. 
	 */
	@SuppressWarnings("unchecked")
	public <Any> Any get(int x, int y) {
		return (Any) columns.get(x).get(y);
	}
	
	/**
	 * Checks if a column can be added based on the lenght defined by the matrix.
	 */
	private void checkColumnSize() {
		if (columns.size() >= size[1]) {
			throw new IndexOutOfBoundsException("Column Tried to one column to many"
					+ " max column size is " + size[1]);
		}
	}
	
	private void checkRowSize(int l) {
		if (l != size[0]) {
			throw new IndexOutOfBoundsException("Column data is to long or to short."
					+ " Expected lenght is " + size[1] + " got " + l);
		}
	}
	
//	private void checkName(String n) {
//		for (String name : columnNames) {
//			if (n.equals(name)) {
//				throw new IllegalArgumentException("column name aleardy exists");
//			}
//		}
//	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Matrix m= new Matrix(2,2);
					m.addColumn(new String[] {"wauw","omg"}, 0);
					m.addColumn(new Matrix[] {new Matrix(0,0),new Matrix(0,0)},1);
				//	m.addColumnData(1,new String[] {"f","a"});
					String l = m.get(1, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
