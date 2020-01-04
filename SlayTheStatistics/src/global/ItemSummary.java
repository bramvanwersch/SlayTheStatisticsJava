package global;

public class ItemSummary {
	private String name;
	private int wins;
	private int losses;
	
	public ItemSummary(String name, String wins, String losses) {
		this.name = name;
		this.wins = Integer.parseInt(wins);
		this.losses = Integer.parseInt(losses);
	}
	
	public double getWinRate() {
		return (double) wins / (wins + losses);
	}

	public void addLoss() {
		this.losses += 1;
	}
	
	public void addWin() {
		this.wins += 1;
	}
	
	public String toString() {
		return String.format("%s,%s,%s,%s\n", name, wins, losses, getWinRate());
	}

}
