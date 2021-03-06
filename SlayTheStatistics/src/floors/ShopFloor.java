package floors;

import java.awt.Color;
import java.util.Arrays;
import java.util.Map;


//TODO: figure out what items are bought. Potions, cards or relics.
public class ShopFloor extends GeneralFloor {

	public ShopFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String[] getBoughtItems() {
		return getArrayValues("items_purchased");
	}
	
	public String[] getPurged() {
		return getArrayValues("items_purged");
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		//TODO make this nicer.
		//way of identifying that the dictionary is from a shop floor
		floorMap.put("shop","");
		if (getBoughtItems().length != 0) {
			floorMap.put("purchased", Arrays.toString(getBoughtItems()));}
		if (getPurged().length != 0) {
			floorMap.put("purged", Arrays.toString(getPurged()));}
		if (getPurged().length != 0 || getBoughtItems().length != 0) { 
			floorMap.put("special",String.format("Items bought/ purged: %s / %s",
					Arrays.toString(getBoughtItems()), Arrays.toString(getPurged())));}
		return floorMap;	
	}
	
	public Color getColor() {
		return new Color(255,242,0,80);
	}
	
	@Override
	public String getText() {
		String text = starterText();
		if (getBoughtItems().length != 0) {
			text += String.format("Items bought: %s\n", Arrays.toString(getBoughtItems()));
		}
		if (getPurged().length != 0) {
			text += String.format("Items purged: %s\n", Arrays.toString(getPurged()));
		}
		return text;
	}
	

}
