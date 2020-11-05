package tp.p1.PlantFactory;

import tp.p1.GameObject.Plant;
import tp.p1.GameObject.Plants.Cherrybomb;
import tp.p1.GameObject.Plants.Peashooter;
import tp.p1.GameObject.Plants.Sunflower;
import tp.p1.GameObject.Plants.Wallnut;

public class PlantFactory {
	
	private static Plant[] availablePlants = { //contiene todas las plantas del juego y ayuda a crearlas e insertarlas en el board
		new Peashooter(),
		new Sunflower(),
		new Cherrybomb(),
		new Wallnut(),
	};
	
	public static Plant getPlant(String name) {
		Plant plant;
		if (name.equalsIgnoreCase("P")) plant = availablePlants[0];
		else if (name.equalsIgnoreCase("S")) plant = availablePlants[1];
		else if (name.equalsIgnoreCase("C")) plant = availablePlants[2];
		else if (name.equalsIgnoreCase("N")) plant = availablePlants[3];
		else plant = null;
		return plant;
	}
	
	public static String listOfAvilablePlants() {
		String info = "\n";
		for (int i = 0; i < availablePlants.length; i++) {
			info += availablePlants[i].getInfo() + "\n";
		}
		return info;
	}

}
