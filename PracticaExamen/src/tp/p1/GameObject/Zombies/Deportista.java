package tp.p1.GameObject.Zombies;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GameObject.Zombie;

public class Deportista extends Zombie {
	
	private int resistencia = 2;
	private int velocidad = 2;
	private final String zombieName = "DEPORTISTA";
	private int accion_cont;

	public Deportista() {
		super(1);
		this.vida = this.resistencia;
		this.accion_cont = 0;
	}
	
	public void avanzar() { //avanza el doble de rapido que el zombie comun
		if (this.y > 1) {
			if (this.game.hayAlgoDelante(getX(), getY() - 2)) {
				this.y -= 1;
			}
			else this.y -= this.velocidad;
		}
		else if (this.y == 1) this.y -= 1;
	}
	
	public String toString() {
		return "X" + "[" + this.vida + "]";
	}
	
	public String getName() {
		return this.zombieName;
	}
	
	public Zombie parse(String name) {
		Zombie type = null;
		if (zombieName.equalsIgnoreCase(this.zombieName) || zombieName.equalsIgnoreCase(this.zombieName.substring(0, 1))) {
			type = new Deportista();
		}
		return type;
	}
	
	public String debugString() {
		return this.zombieName.substring(0, 1) + "[" + this.vida + ":" + this.resistencia + ",x:" + this.x + ",y:" + this.y + ",t:" + this.accion_cont + "]";
	}
	
	public void store(BufferedWriter output) throws IOException {
		output.write("x:" + this.vida + ":" + this.x + ":" + this.y + ":" + this.accion_cont);
	}
	
	public void setAcciont(int parseInt) {
		this.accion_cont = parseInt;
		
	}

}
