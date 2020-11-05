package tp.p1.GameObject.Zombies;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Zombie;

public class Caracubo extends Zombie {
	
	private int resistencia = 8;
	private int contador_quieto;
	private int velocidad = 1;
	private final String zombieName = "CARACUBO";
	private int accion_cont;

	public Caracubo() {
		super(1);
		this.vida = this.resistencia;
		this.contador_quieto = 0;
		this.accion_cont = 4;
	}
	
	public void avanzar() { //ayudado por un contador de cuando tiene que moverse, avanza
		if (this.contador_quieto == 2) {
			this.y -= this.velocidad;
			this.contador_quieto = 0;
			this.accion_cont = 4;
		}
		else {
			this.contador_quieto++;
			this.accion_cont--;
		}
	}
	
	public String toString() {
		return "W" + "[" + this.vida + "]";
	}

	public String getName() {
		return this.zombieName;
	}

	public Zombie parse(String name) {
		Zombie type = null;
		if (zombieName.equalsIgnoreCase(this.zombieName) || zombieName.equalsIgnoreCase(this.zombieName.substring(0, 1))) {
			type = new Caracubo();
		}
		return type;
	}

	public String debugString() {
		return this.zombieName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("w:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}

}
