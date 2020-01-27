package Utilities;

public class Column<T>{
	private Object[] data;
	
	public Column(int size) {
		this.data = new Object[size];
	}
	
	/**
	 * sets the data of the column. The matrix class should ensure that
	 * the data is always of the correct lenght
	 * @param data object array
	 */
	public void setData(Object[] data) {
		this.data = data;
	}
	
	/**
	 * Returns a data array equal to the downcast value saved in the column
	 * @return an array of data of the type of the downcast data.
	 */
    @SuppressWarnings("unchecked")
	public T[] get() {
    	return (T[]) data;
    }
	
    /**
     * Returns a data value equal to the downcast value saved in the column
     * @param i
     * @return
     */
    @SuppressWarnings("unchecked")
	public T get(int i) {
    	return (T) data[i];
    }
    
    /**
     * Remove a value from index in the column. This is done when removing rows. 
     * @param index of the value to be removed.
     */
    public void remove(int index){
    	Object [] tempD = new Object[data.length -1];
    	for (int i = 0; i < tempD.length; i++) {
    		if (i == index) {
    			continue;
    		}
    		tempD[i] = data[i];
    	}
    	this.data = tempD;
    }
}
