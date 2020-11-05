package tp.p1.GameObject.Zombies;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Zombie;

public class ZombieComun extends Zombie {

	private int resistencia = 5;
	private int velocidad = 1;
	private final String zombieName = "ZOMBIE";
	private int accion_cont;

	public ZombieComun() {
		super(1);
		this.vida = this.resistencia;
		this.accion_cont = 0;
	}
	
	public void avanzar() {
		this.y -= this.velocidad;
	}
	
	public String toString() {
		return "Z" + "[" + this.vida + "]";
	}
	
	public String getName() {
		return this.zombieName;
	}
	
	public Zombie parse(String name) {
		Zombie type = null;
		if (zombieName.equalsIgnoreCase(this.zombieName) || zombieName.equalsIgnoreCase(this.zombieName.substring(0, 1))) {
			type = new ZombieComun();
		}
		return type;
	}
	
	public String debugString() {
		return this.zombieName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("z:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}
}
