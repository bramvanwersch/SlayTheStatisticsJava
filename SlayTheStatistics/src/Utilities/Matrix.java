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
		checkColumnSize(columns.size());
		columns.add(index, new Column(size[1]));
		addColumnData(index, data);
	}
	
	private void addColumnData(int index, Object[] data) {
		checkRowSize(data.length);
		columns.get(index).setData(data);
	}
	
	public Object get(int x, int y) {
		return columns.get(x).get(y);
	}
	
	public <Any> Any get(int x, int y, Class<Any> c){
		Object v = columns.get(x).get(y);
		try {
			if (c == String.class) {
				return (Any) v.toString();
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private void checkColumnSize(int l) {
		if (l > size[1]) {
			throw new IndexOutOfBoundsException("Column data is to long or to short."
					+ " Expected lenght is " + size[1] + " got " + l);
		}
	}
	
	private void checkRowSize(int l) {
		if (l != size[0]) {
			throw new IndexOutOfBoundsException("Added one row to many." 
					+ " Max size is " + size[0]);
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
				//	m.addColumnData(1,new String[] {"f","a"});
					String g = m.get(0, 0, String.class);
					System.out.println(g);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
