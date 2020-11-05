package tp.p1.GameObject;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.GamePackage.Game;

public abstract class GameObject {
	
	protected int x;
	protected int y;
	protected Game game;
	protected int vida;
	
	public GameObject() { //tipo abstracto gameobject, los atributos se definen en los objetos planta y zombie
		
	}
	
	public abstract void update();
	public abstract String getInfo();
	public abstract String debugString();
	public abstract void store(BufferedWriter output) throws IOException;
	public abstract void setAcciont(int parseInt);

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getVida() {
		return this.vida;
	}
	
	public boolean comprobarCoordenadas(int x, int y) {
		return (this.x == x && this.y == y);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void reducirVida(int danio) {
		this.vida -= danio;
	}

	public boolean comprobarMuerto() {
		return this.vida <= 0;
	}

	public void setVida(int parseInt) {
		this.vida = parseInt;
	}
}
