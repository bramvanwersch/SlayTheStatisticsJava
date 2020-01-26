package Utilities;

public class Column<T>{
	private String name;
	private Object[] data;
	
	public Column(int size) {
		this.data = new Object[size];
	}
	
	public void setData(Object[] data) {
		this.data = data;
	}
	
    @SuppressWarnings("unchecked")
	public T[] get() {
    	return (T[]) data;
    }
	
    @SuppressWarnings("unchecked")
	public T get(int i) {
    	return (T) data[i];
    }
    
	public String getName() {
		return name;
	}

}
