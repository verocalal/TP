package tp.p1.GameObject.Plants;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Plant;

public class Peashooter extends Plant {

	private int resistencia = 3;
	private final String plantName = "Peashooter";
	private final static String plantInfo = "[P]eashooter: Cost: 50 suncoins  Harm: 1";
	private int accion_cont = 0;
	
	public Peashooter() {
		super(50, 1, plantInfo);
		this.vida = this.resistencia;
	}
	
	public void update() { // metodo disparar a game para disparar a los zombies
		disparar();
	}
	
	public void disparar() {
		this.game.disparar(getDanio(), getX(), getY());
	}
	
	public String toString() {
		return "P " + "[" + getVida() + "]";
	}
	
	public String getName() {
		return this.plantName;
	}
	
	public Plant parse(String plantName) {
		Plant type = null;
		if (plantName.equalsIgnoreCase(this.plantName) || plantName.equalsIgnoreCase(this.plantName.substring(0, 1))) {
			type = new Peashooter();
		}
		return type;
	}
	
	public String debugString() {
		return this.plantName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("p:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}
	
}
