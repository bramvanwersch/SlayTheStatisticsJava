package tests;

import junit.framework.TestCase;
import run.ReadingRunFile;

public class ReadingRunFileTest extends TestCase {
	
	public void testSetup() {
		ReadingRunFile myReader = new ReadingRunFile("IRONCLAD","1551815208.run", true); 
		assertNotNull(myReader);
	}
//	
//	public void testGetKey() {
//		ReadingRunFile myReader = new ReadingRunFile(testFilePath);
//		String path = myReader.getKey("path_per_floor");
//		String bossRelics = myReader.getKey("boss_relics");
//		String character = myReader.getKey("character_chosen");
//		assertEquals(path,"[\"M\",\"$\",\"M\",\"M\",\"M\",\"R\",\"E\",\"$\",\"T\",\"M\",\"?\",\"R\",\"?\",\"M\",\"R\",\"B\",null,\"M\",\"M\",\"$\",\"M\"]" );
//		assertEquals(bossRelics,"{\"not_picked\":[\"BlackBlood\",\"Astrolabe\"],\"picked\":\"CursedKey\"}");
//		assertEquals(character, "\"IRONCLAD\"");
//	}
}
