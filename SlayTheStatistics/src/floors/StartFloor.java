package floors;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

//TODO: make neow cost and bonus return more complete statements and integrate card removal transform etc.
public class StartFloor implements Floor{
	private String character;
	private String neowBonus;
	private String neowCost;
	private int ascensionLvl;

	public StartFloor(String character, String neowCost, String neowBonus,
			String ascensionLvl) {
		this.character = character.replace("\"","").toLowerCase();
		this.neowBonus = neowBonus.replace("\"","").toLowerCase();
		this.neowCost = neowCost.replace("\"","").toLowerCase();
		this.ascensionLvl = Integer.parseInt(ascensionLvl);
	}
	
	public String getPath() {
		return "StartFloor";
	}

	public int getGold(){
		int gold = 99;
		if (this.neowBonus.equals("hundred_gold") || this.neowBonus.equals("two_fifty_gold")) {
			gold += Double.parseDouble(getNeowBonus());
		}
		else if (this.neowCost.equals("no_gold")) {
			gold += Double.parseDouble(getNeowCost());
		}
		return gold;
	}
	
	public int getHealth() {
		int health = getMaxHp();
		//adjust health based on ascension level.
		health *= Double.parseDouble(ascensionPenalty()[0]);
		if (this.neowCost.equals("fifty_percent_damage")) {
			health *= Double.parseDouble(getNeowCost());
		}
		return health;
	}
	
	public int getMaxHp() {
		int maxHp = getCharacterMaxHp();
		maxHp += Integer.parseInt(ascensionPenalty()[2]);
		if (this.neowBonus.equals("ten_percent_hp_bonus")) {
			maxHp *= Double.parseDouble(getNeowBonus());
		}
		else if (this.neowCost.equals("ten_percent_hp_loss")) {
			maxHp *= Double.parseDouble(getNeowCost());
		}
		return maxHp;
	}
	
	public int getHealed() {
		return 0;
	}
	
	public Color getColor() {
		return new Color(255,255,255);
	}
	
	private int getCharacterMaxHp() {
		if (this.character.equals("ironclad")) {
			return 80;
		}
		else if (this.character.equals("the_silent")) {
			return 70;
		}
		else if (this.character.equals("defect")) {
			return 75;
		}
		return 0;
	}
	
	private String[] ascensionPenalty() {
		String[] ascensionPenalties = new String[] {"1","","0"};
		//ten percent hp loss
		if (this.ascensionLvl >= 6) {
			ascensionPenalties[0] = "0.9";
		}
		//curse card added
		if (this.ascensionLvl >= 10) {
			ascensionPenalties[1] = "card";
		}
		//max hp loss
		if (this.ascensionLvl >= 14) {
			if (this.character.equals("the_silent") || this.character.equals("defect")) {
				ascensionPenalties[2] = "-4";
			}
			else if (this.character.equals("ironclad")) {
				ascensionPenalties[2] = "-5";
			}
		}
		return ascensionPenalties;
	}
	
	private String getNeowBonus() {
		neowBonus = neowBonus.toLowerCase();
		String bonus = "";
		switch(neowBonus) {
		case "boss_relic":
			bonus = "boss relic";
			break;
		case "one_random_card":
			bonus = "one card";
			break;
		case "three_rare_cards":
			bonus = "one of 3 rare cards";
			break;
		case "random_common_relic":
			bonus = "commonrelic";
			break;
		case "three_cards":
			bonus = "one of 3 cards";
			break;
		case "three_enemy_kills":
			bonus = "relic";
			break;
		case "one_rare_relic":
			bonus = "relic";
			break;
		case "transform_two_cards":
			bonus = "transform";
			break;
		case "ten_percent_hp_bonus":
			bonus = "1.10";
			break;
		case "remove_card":
			bonus = "remove";
			break;
		case "hundred_gold":
			bonus = "100";
			break;
		case "random_colorless":
			bonus = "card";
			break;
		case "remove_two":
			bonus = "removes";
			break;
		case "two_fifty_gold":
			bonus = "250";
			break;
		case "three_small_potions":
			bonus = "potions";
			break;
		case "transform_card":
			bonus = "transform";
			break;
		case "random_colorless_2":
			bonus = "cards";
			break;
		case "upgrade_card":
			bonus = "upgrade";
			break;
		}
		return bonus;
	}
	
	private String getNeowCost() {
		neowCost = neowCost.toLowerCase();
		String cost = "";
		switch(neowCost) {
		case "none":
			cost = "none";
			break;
		case "fifty_percent_damage":
			cost = "0.5";
			break;
		case "no_gold":
			cost = "-99";
			break;
		case "ten_percent_hp_loss":
			cost = "0.9";
			break;
		case "curse":
			cost = "curse";
			break;
		case "percent_damage":
			cost = "";
			//TODO: idk what this is.
			break;
		}
		return cost;
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = new HashMap<String,String>();
		floorMap.put("path", getPath());
		floorMap.put("health", "" + getHealth());
		floorMap.put("maxHealth", "" + getMaxHp());
		floorMap.put("gold", "" + getGold());
		floorMap.put("special",String.format("Neow bonus/cost: %s / %s.",this.neowBonus, this.neowCost));
		return floorMap;	
	}
	
	public String getText() {
		String text = String.format("%s (%s):\n", getPath(), "0");
		text += String.format("Health: %s/%s \n", getHealth(),getMaxHp());
		text += String.format("Gold: %s\n", getGold());
		text += String.format("Neow bonus: %s.\n", this.neowBonus);
		text += String.format("Neow cost: %s.\n",this.neowCost);
		return text;
	}


}
