package tp.p1.GameObject.Plants;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Plant;

public class Cherrybomb extends Plant {
	
	private int resistencia = 2;
	private int contador_vida;
	private int accion_cont;
	private final String plantName = "Cherrybomb";
	private final static String plantInfo = "Peta[c]ereza: Cost: 50 suncoins Harm: 10 ";

	public Cherrybomb() {
		super(50, 10, plantInfo);
		this.vida = this.resistencia;
		this.accion_cont = 4;
	}
	
	public void update() {  // update de explotar que llama a un metodo explotar en game para ver que zombies van a ver reducida su vida
		if (this.contador_vida == 2) {
			explotar();
			this.vida = 0;
		}
		else {
			this.contador_vida++;
			this.accion_cont--;
		}
	}

	private void explotar() {
		this.game.explotar(getDanio(), getX(), getY());
	}

	public String toString() {
		return "C " + "[" + getVida() + "]";
	}
	
	public String getName() {
		return this.plantName;
	}

	public Plant parse(String plantName) {
		Plant type = null;
		if (plantName.equalsIgnoreCase(this.plantName) || plantName.equalsIgnoreCase(this.plantName.substring(0, 1))) {
			type = new Cherrybomb();
		}
		return type;
	}

	
	public String debugString() {
		return this.plantName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}

	public void store(BufferedWriter output) throws IOException {
		output.write("c:" + this.contador_vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}

	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}
}
