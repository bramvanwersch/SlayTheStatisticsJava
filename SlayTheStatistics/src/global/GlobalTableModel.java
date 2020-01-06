package global;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class GlobalTableModel implements TableModel {
	private String[] columnNames;
	private Object[][] data;
	private int[] doubleSortColumns;

	public GlobalTableModel(Object[][] data, String[] columnNames, int... doubleSortColumns) {
		this.data = data;
		this.columnNames = columnNames;
		this.doubleSortColumns = doubleSortColumns;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
	}
	
	@Override
	public Class<?> getColumnClass(int column){
		for (int col : doubleSortColumns) {
			if (col == column) {
				return Integer.class;
			}
		}
        return String.class;
    }

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}


}
