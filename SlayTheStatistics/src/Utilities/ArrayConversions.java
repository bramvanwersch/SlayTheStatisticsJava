package Utilities;

public class ArrayConversions {
	
	/**
	 * Convert string array to integer
	 * @param ss is the array of strings
	 * @return integer array of same lenght
	 */
	public static Integer[] stringToInteger(String[] ss){
		Integer[] is = new Integer[ss.length];
		for (int i = 0; i < ss.length; i++) {
			is[i] = Integer.parseInt(ss[i]);
		}
		return is;
	}

	/**
	 * Convert string array to double
	 * @param ss is the array of strings
	 * @return double array of same lenght
	 */
	public static Double[] stringToDouble(String[] ss) {
		Double[] is = new Double[ss.length];
		for (int i = 0; i < ss.length; i++) {
			is[i] = Double.parseDouble(ss[i]);
		}
		return is;
	}

}
