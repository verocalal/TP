package tp.p1.GameObject.Plants;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Plant;

public class Wallnut extends Plant {

	private int resistencia = 10;
	private final String plantName = "Nuez";
	private final static String plantInfo = "[N]uez: Cost: 50 suncoins  Harm: 0";
	private int accion_cont;
	
	public Wallnut() {
		super(50, 0, plantInfo);
		this.vida = this.resistencia;
		this.accion_cont = 0;
	}
	
	public void update() {} // Esta planta es un obstáculo entonces el update está vacío.
	
	public String toString() {
		return "N " + "[" + getVida() + "]";
	}
	
	public String getName() {
		return this.plantName;
	}
	
	public Plant parse(String plantName) {
		Plant type = null;
		if (plantName.equalsIgnoreCase(this.plantName) || plantName.equalsIgnoreCase(this.plantName.substring(0, 1))) {
			type = new Wallnut();
		}
		return type;
	}
	
	public String debugString() {
		return this.plantName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("n:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}

}
