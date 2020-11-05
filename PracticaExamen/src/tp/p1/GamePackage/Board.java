package tp.p1.GamePackage;

import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.Exceptions.FileContentsException;
import tp.p1.GameObject.GameObjectList;
import tp.p1.GameObject.Plant;
import tp.p1.GameObject.Zombie;

public class Board {
	
	private GameObjectList plantList; // contiene dos listas, una para plantas y otra para zombies
	private GameObjectList zombieList; // ademas tiene todos los metodos correspondientes para añadir, eliminar o modificar
	
	public Board(Game game) {
		this.plantList = new GameObjectList(game);
		this.zombieList = new GameObjectList(game);
	}
	
	public void addPlant(Plant plant, int x, int y) {
		this.plantList.add(plant);
		this.plantList.setX(x);
		this.plantList.setY(y);
	}
	
	public boolean plantaEncontrada(int x, int y) {
		boolean encontrado = this.plantList.buscar(x, y);
		return encontrado;
	}

	public String enseniarPlanta(int x, int y) {
		return plantList.enseniar(x, y);
	}

	public void update() {
		plantList.update();
		zombieList.update();
	}

	public void setGamePlant(Game game) {
		this.plantList.setGame(game);
	}

	public void atacar(int danio, int x, int y) {
		plantList.atacar(danio, x, y);
	}

	public boolean comprobarMaxZombies(int num_zombies) {
		return zombieList.getContMuerto() < num_zombies;
	}

	public boolean zombieEncontrado(int x, int y) {
		boolean encontrado = this.zombieList.buscar(x, y);
		return encontrado;
	}

	public String enseniarZombie(int x, int y) {
		return zombieList.enseniar(x, y);
	}

	public void addZombie(Zombie zombie, int x, int y) {
		this.zombieList.add(zombie);
		this.zombieList.setX(x);
		this.zombieList.setY(y);
	}
	
	public void setGameZombie(Game game) {
		this.zombieList.setGame(game);
	}

	public void disparar(int danio, int x, int y) {
		zombieList.disparar(danio, x, y);
	}

	public void explotar(int danio, int x, int y) {
		zombieList.explotar(danio, x, y);
	}

	public boolean comprobarDerrota() {
		return zombieList.comprobarDerrota();
	}

	public boolean comprobarVictoria() {
		return zombieList.comprobarVictoria();
	}

	public int getZombieCont() {
		return zombieList.getCont();
	}

	public int getNumeroPlantas() {
		return plantList.getCont();
	}

	public int getNumeroZombies() {
		return zombieList.getCont();
	}

	public String getDebugPlant(int i) {
		return plantList.getDebug(i);
	}

	public String getDebugZombie(int i) {
		return zombieList.getDebug(i);
	}

	public void store(BufferedWriter output) throws IOException {
		output.write("plantList: ");
		this.plantList.store(output);
		output.newLine();
		output.write("zombieList: ");
		this.zombieList.store(output);
	}

	public void setRemZombies(int remZombie) {
		this.zombieList.setRemZombies(remZombie);
	}

	public void setPlantList(String[] plantStrings, String[] zombieStrings, Game game) throws FileContentsException {
		this.plantList = new GameObjectList(plantStrings, game, true);
		this.zombieList = new GameObjectList(zombieStrings, game, false);
	}

}
