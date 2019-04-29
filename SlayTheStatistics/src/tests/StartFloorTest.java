package tests;

import junit.framework.TestCase;
import run.StartFloor;

public class StartFloorTest extends TestCase {
	
	
	public void testCreateStartFloor() {
		StartFloor sFloor = new StartFloor("IRONCLAD", "NONE", "TEN_PERCENT_HP_BONUS","0");
		assertNotNull(sFloor);
	}
	
	public void testGetMaxHp() {
		StartFloor sFloor = new StartFloor("IRONCLAD", "NONE", "TEN_PERCENT_HP_BONUS","14");
		int maxHp = sFloor.getMaxHp();
		assertEquals(maxHp, 82);
	}
	
	public void testGetHealth() {
		StartFloor sFloor = new StartFloor("IRONCLAD", "TEN_PERCENT_HP_LOSS", "TWO_FIFTY_GOLD","14");
		int health = sFloor.getHealth();
		assertEquals(health, 60);
	}
	
	public void testGetGold() {
		StartFloor sFloor = new StartFloor("IRONCLAD", "TEN_PERCENT_HP_LOSS", "TWO_FIFTY_GOLD","14");
		int gold = sFloor.getGold();
		assertEquals(gold, 349);
	}

}
