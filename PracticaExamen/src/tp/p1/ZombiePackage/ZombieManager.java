package tp.p1.ZombiePackage;

import java.util.Random;

import tp.p1.GamePackage.Level;

public class ZombieManager {
	
	private Level nivel;
	private int seed;
	private Random r;
	
	public ZombieManager(Level dificultad, int seed) {
		this.nivel = dificultad;
		this.seed = seed;
		this.r = new Random(this.seed);
	}
	
	public boolean isZombieAdded()
	{
		int probabilidad = r.nextInt(10);
		return  probabilidad < nivel.frecuencia();
	}
	
}
