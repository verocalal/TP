package tp.p1.GameObject;

public abstract class Plant extends GameObject {

	private final int cost;
	protected final int danio;
	private final String plantInfo;
	
	public Plant(int cost, int danio, String plantInfo) { //objeto planta, establece el coste y el danio
		super();
		this.cost = cost;
		this.danio = danio;
		this.plantInfo = plantInfo;
	}
	
	public abstract Plant parse(String plantName); //parse para identificar planta
	
	public String getInfo() {
		return this.plantInfo;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public int getDanio() {
		return this.danio;
	}
	
	
}
