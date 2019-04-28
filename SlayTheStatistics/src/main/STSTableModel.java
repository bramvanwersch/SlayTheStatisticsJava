package main;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class STSTableModel implements TableModel {
	private String[] columnNames;
	private Object[][] data;

	public STSTableModel(Object[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
	}
	
	@Override
	public int getRowCount() {
		return data[0].length;
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
	public Class getColumnClass(int column){
        for (int row = 0; row < getRowCount(); row++){
            Object o = getValueAt(row, column);
            if (o != null){
                return o.getClass();
            }
        }
        return Object.class;
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
