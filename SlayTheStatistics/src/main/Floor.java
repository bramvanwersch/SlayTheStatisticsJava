package main;

import java.util.Map;

public interface Floor {
	public int getGold();
	public int getHealth();
	public int getMaxHp();
	public String getPath();
	public String getText();
	public Map<String,String> getFloorMap();
	

}
