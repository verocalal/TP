package tp.p1.GamePackage;

public class SuncoinManager {
	
	private int suncoins;
	
	public SuncoinManager() {
		this.suncoins = 50;
	}
	
	public void addSuncoin(int soles) {
		this.suncoins += soles;;
	}
	
	public int getSuncoins() {
		return this.suncoins;
	}

	public void reducirSuncoins(int cost) {
		this.suncoins -= cost;
		
	}

	public void setSuncoins(int sc) {
		this.suncoins = sc;
	}
}
