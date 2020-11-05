package tp.p1.GameObject.Plants;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Plant;

public class Sunflower extends Plant{
	
	private int resistencia = 1;
	private int soles = 10;
	private int ciclos_vida;
	private int accion_cont;
	private final String plantName = "Sunflower";
	private final static String plantInfo = "[S]unflower: Cost: 20 suncoins  Harm: 0";
	
	public Sunflower() {
		super(10, 0, plantInfo);
		this.vida = this.resistencia;
		this.ciclos_vida = 0;
		this.accion_cont = 2;
	}
	
	public void update() { // se actualizan los suncoins si el ciclo de vida de la planta es impar
		if (this.ciclos_vida%2 != 0) {
			game.addSuncoin(getSoles());
			this.accion_cont = 2;
		}
		else this.accion_cont--;
		this.ciclos_vida++;
	}
	
	public String toString() {
		return "S " + "[" + getVida() + "]";
	}
	
	public String getName() {
		return this.plantName;
	}
	
	public Plant parse(String plantName) {
		Plant type = null;
		if (plantName.equalsIgnoreCase(this.plantName) || plantName.equalsIgnoreCase(this.plantName.substring(0, 1))) {
			type = new Sunflower();
		}
		return type;
	}
	
	public int getSoles() {
		return this.soles;
	}
	
	public String debugString() {
		return this.plantName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("s:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}
}
