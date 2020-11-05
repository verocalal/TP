package tp.p1;

import tp.p1.GamePackage.Level;

import tp.p1.GamePackage.Controller;

public class PlantsVsZombies 
{

	public static void main(String[] args) 	// En el main creamos un controller a partir de la dificultad
	{										// la seed y el modo del print que predeterminadamente viene en RELEASE
		try {
			String nivel = args[0]; 
			int seed = Integer.parseInt(args[1]);
			String mode = "RELEASE";
			
			if (nivel.equals("HARD")) {
				Level dificultad = new Level(nivel);
				Controller juego = new Controller(dificultad, seed, mode);
				juego.run();
			}
			
			else if (nivel.equals("EASY")) {
				Level dificultad = new Level(nivel);
				Controller juego = new Controller(dificultad, seed, mode);
				juego.run();
			}
			
			else {
				Level dificultad = new Level(nivel);
				Controller juego = new Controller(dificultad, seed, mode);
				juego.run();
			}
		}
		
		catch (NumberFormatException ex) {
			System.out.println("La seed no es un numero entero, reinicia el programa con una seed correcta");
		} // Aquí tenemos el catch de la excepción por si la seed no es un entero
		
	}
}
