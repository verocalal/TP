package tp.p1.PlantFactory;

import tp.p1.GameObject.Zombie;
import tp.p1.GameObject.Zombies.Caracubo;
import tp.p1.GameObject.Zombies.Deportista;
import tp.p1.GameObject.Zombies.ZombieComun;

public class ZombieFactory {

	private static Zombie[] availableZombies = { //similar a plantFactory pero con el añadido de que el zombie añadido es aleatorio
			new ZombieComun(),
			new Caracubo(),
			new Deportista(),
		};
		
		public static Zombie getZombie(int random) {
			Zombie zombie = availableZombies[random];
			return zombie;
		}
		public static Zombie getZombieSave(String name) {
			Zombie zombie;
			if (name.equalsIgnoreCase("Z")) zombie = availableZombies[0];
			else if (name.equalsIgnoreCase("W")) zombie = availableZombies[1];
			else if (name.equalsIgnoreCase("X")) zombie = availableZombies[2];
			else zombie = null;
			return zombie;
		}
		
	
}
