package tp.p1.GameObject;

public abstract class Zombie extends GameObject {

	protected int danio;
	
	public abstract void avanzar();
	public abstract String getName();
	public abstract Zombie parse(String name);
	
	public Zombie(int danio) { //objeto zombie, establece el danio
		super();
		this.danio = danio;
	}

	public String getInfo() {
		return null;
	}
	
	public int getDanio() {
		return this.danio;
	}

	public void update() { //al ser el mismo update para todos los zombies se implementa aqui
		if (this.game.hayAlgoDelante(getX(), getY() - 1)) {
			game.atacar(getDanio(), getX(), getY() - 1);
		}
		else avanzar();
	}

}
