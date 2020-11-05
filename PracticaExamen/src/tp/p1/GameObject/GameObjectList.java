package tp.p1.GameObject;


import java.io.BufferedWriter;
import java.io.IOException;

import tp.p1.Exceptions.FileContentsException;
import tp.p1.GamePackage.Game;
import tp.p1.PlantFactory.PlantFactory;
import tp.p1.PlantFactory.ZombieFactory;

public class GameObjectList {
	
	private GameObject listitem[] = new GameObject[32]; //contiene la lista de objetos
	private int cont;
	private int cont_muerto;
	private Game game;
	private int remZombies;
	
	public GameObjectList (Game game) {
		this.cont = 0;
		this.cont_muerto = 0;
		this.game = game;
		this.remZombies = game.getNumZombies();
	}
	
	public GameObjectList (String[] list, Game game, boolean isPlant) throws FileContentsException {
		this.cont = 0;
		this.cont_muerto = 0;
		this.game = game;
		this.remZombies = game.getNumZombies();
		if (isPlant) {
			Plant plant = null;
			for(int i = 0; i < list.length; i++) {
				String[] datos = list[i].split(":");
				plant = PlantFactory.getPlant(datos[0]);
				plant = plant.parse(datos[0]);
				listitem[i] = plant;
				listitem[i].setGame(game);
				listitem[i].setVida(Integer.parseInt(datos[1]));
				listitem[i].setAcciont(Integer.parseInt(datos[4]));
				this.cont++;
				setX(Integer.parseInt(datos[2]));
				setY(Integer.parseInt(datos[3]));
			}
		}
		else {
			Zombie zombie = null;
			for(int i = 0; i < list.length; i++) {
				String[] datos = list[i].split(":");
				zombie = ZombieFactory.getZombieSave(datos[0]);
				zombie = zombie.parse(datos[0]);
				listitem[i] = zombie;
				listitem[i].setGame(game);
				listitem[i].setVida(Integer.parseInt(datos[1]));
				listitem[i].setAcciont(Integer.parseInt(datos[4]));
				this.cont++;
				setX(Integer.parseInt(datos[2]));
				setY(Integer.parseInt(datos[3]));
			}
		}
		
	}
	
	public boolean buscar(int x, int y) {
		boolean encontrado = false;
		int i = 0;
		while (i < this.cont && !encontrado) {
			encontrado = listitem[i].comprobarCoordenadas(x, y);
			i++;
		}
		return encontrado;
	}
	
	
	public void add(GameObject object) {
		listitem[this.cont] = object;
		cont++;
	}
	
	public int getCont() {
		return this.remZombies;
	}
	
	public int getContMuerto() {
		return this.cont_muerto;
	}


	public void setX(int x) {
		this.listitem[this.cont - 1].setX(x);
	}
	
	public void setY(int y) {
		this.listitem[this.cont - 1].setY(y);
	}

	public String enseniar(int x, int y) {
		String plant = "";
		int i = 0;
		boolean encontrado = false;
		while (i < this.cont && !encontrado) {
			if(listitem[i].comprobarCoordenadas(x, y)) {
				plant = listitem[i].toString();
				encontrado = true;
			}
			i++;
		}
		return plant;
	}

	public void update() {
		for (int i = 0; i < this.cont; i++) {
			listitem[i].update();
			while (i < this.cont && listitem[i].comprobarMuerto()) {
				eliminarMuerto(i);
			}
		}
	}

	private void eliminarMuerto(int i) {
		for (int j = i; j < this.cont - 1; j++) {
			this.listitem[j] = this.listitem[j + 1];
		}
		this.cont--;
		this.cont_muerto++;
		this.remZombies--;
	}

	public void setGame(Game game) {
		this.listitem[this.cont - 1].setGame(game);
		
	}

	public void atacar(int danio, int x, int y) {
		int i = 0;
		boolean encontrado = false;
		while (i < this.cont && !encontrado) {
			if(listitem[i].comprobarCoordenadas(x, y)) {
				listitem[i].reducirVida(danio);
				encontrado = true;
			}
			i++;
		}
	}

	public void disparar(int danio, int x, int y) {
		boolean encontrado = false;
		int i = 0, j = y;
		while(i < this.cont && !encontrado) {
			j = y;
			while(j < 8 && !encontrado) {
				if (this.listitem[i].comprobarCoordenadas(x, j)) {
					this.listitem[i].reducirVida(danio);
					encontrado = true;
				}
				j++;
			}
			i++;
		}
	}

	public void explotar(int danio, int x, int y) {
		for (int k = 0; k < this.cont; k++) {
			for (int i = y - 1; i < y + 2; i++) {
				for (int j = x - 1; j < x + 2; j++) {
					if (this.listitem[k].comprobarCoordenadas(j, i)) this.listitem[k].reducirVida(danio);
				}
			}
		}
	}

	public boolean comprobarDerrota() {
		boolean pierde = false;
		int i = 0;
		while (i < this.cont && !pierde) {
			if (listitem[i].getY() == 0) pierde = true;
			i++;
		}
		return pierde;
	}

	public boolean comprobarVictoria() {
		return game.getMaxZombies() <= this.cont_muerto;
	}

	public String getDebug(int i) {
		return listitem[i].debugString();
	}

	public void store(BufferedWriter output) throws IOException {
		for (int i = 0; i < this.cont; i++) {
			this.listitem[i].store(output);
			if (i < this.cont - 1) output.write(", ");
		}
	}

	public void setRemZombies(int remZombie) {
		this.remZombies = remZombie;
	}
}
