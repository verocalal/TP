package tp.p1.GamePackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.FileContentsException;
import tp.p1.GameObject.Plant;
import tp.p1.GameObject.Zombie;
import tp.p1.PlantFactory.ZombieFactory;
import tp.p1.ZombiePackage.*;

public class Game{ // clase game que contiene la logica del juego y metodos necesarios para que se ejecute correctamente

	private int cycleCount = 0;
	private Level nivel;
	private SuncoinManager suncoin;
	private ZombieManager zm;
	private int seed;
	private Random r;
	private Board board;
	private boolean exit;
	private BoardPrinter printer;
	public static final String wrongPrefixMsg = "unknown game attribute: ";
	public static final String lineTooLongMsg = "too many words on line commencing: ";
	public static final String lineTooShortMsg = "missing data on line commencing: ";

	
	public Game(Level dificultad, int seed, String mode) {
		this.suncoin = new SuncoinManager();
		this.zm = new ZombieManager(dificultad, seed);
		this.nivel = dificultad;
		this.board = new Board(this);
		this.seed = seed;
		this.exit = false;
		this.r = new Random(this.seed);
		if (mode.equalsIgnoreCase("RELEASE")) this.printer = new ReleasePrinter();
		else this.printer = new DebugPrinter();
	}
	
	public Game(Game game) {
		this.suncoin = game.getSuncoinManager();
		this.zm = game.getZombieManager();
		this.board = game.getBoard();
		this.nivel = game.getLevel();
		this.seed = game.getSeed();
		this.exit = false;
		this.r = new Random(this.seed);
		this.printer = new ReleasePrinter();
	}
	
	public Random getRandom() {
		return this.r;
	}
	
	public Level getLevel() {
		return this.nivel;
	}
	
	public SuncoinManager getSuncoinManager() {
		return this.suncoin;
	}
	
	public ZombieManager getZombieManager() {
		return this.zm;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void update() {
		board.update();
		if (zm.isZombieAdded() && board.comprobarMaxZombies(this.nivel.getNum_zombies())) {
			int num_random = r.nextInt(4);
			Zombie zombie = addZombie();
			addZombieToGame(zombie, num_random, 7);
		}
		aumentarCiclo();
	}
	
	public void reset() {
		this.suncoin = new SuncoinManager();
		this.board = new Board(this);
		this.r = new Random(this.seed);
		this.cycleCount=0;
	}

	public void aumentarCiclo() {
		this.cycleCount ++;
	}
	
	public int mostrarCiclo() {
		return this.cycleCount;
	}
	
	public void addZombieToGame (Zombie zombie, int x, int y) {
		this.board.addZombie(zombie, x, y);
		this.board.setGameZombie(this);
	}
	
	public boolean addPlantToGame (Plant plant, int x, int y) throws CommandExecuteException {
		boolean plantaAniadida = false;
		if (this.suncoin.getSuncoins() >= plant.getCost()) {
			if (!casillaOcupada(x, y)) {
				this.board.addPlant(plant, x, y);
				this.board.setGamePlant(this);
				reducirSuncoins(plant.getCost());
				plantaAniadida = true;
			}
			else throw new CommandExecuteException("La posicion " + x + ", " + y + " esta ocupada");
		}
		else throw new CommandExecuteException("No tienes suficientes suncoins para aniadir esta planta");
		return plantaAniadida;
	}
	
	public boolean gana() {
		return board.comprobarVictoria();
	}
	
	public void setGametoPlant(Game game) {
		this.board.setGamePlant(game);
	}
	
	public boolean buscarPlanta(int x, int y) {
		boolean encontrado = false;
		encontrado = board.plantaEncontrada(x, y);
		return encontrado;
	}
	
	public String enseniarPlanta(int x, int y) {
		return board.enseniarPlanta(x, y);
	}

	public int getSuncoins() {
		return this.suncoin.getSuncoins();
	}
	
	public int getCycle() {
		return this.cycleCount;
	}
	
	public int getRemainingZombies() {
		return board.getZombieCont();
	}
	
	public void addSuncoin(int soles) {
		this.suncoin.addSuncoin(soles);
	}

	public void reducirSuncoins(int cost) {
		this.suncoin.reducirSuncoins(cost);
	}

	public boolean hayAlgoDelante(int x, int y) {
		return this.board.plantaEncontrada(x, y);
	}

	public void atacar(int danio, int x, int y) {
		this.board.atacar(danio, x, y);
	}
	
	public Zombie addZombie() {
		int random = this.r.nextInt(3);
		Zombie zombie = ZombieFactory.getZombie(random);
		zombie = zombie.parse(zombie.getName());
		return zombie;	
	}

	public boolean buscarZombie(int x, int y) {
		boolean encontrado = false;
		encontrado = board.zombieEncontrado(x, y);
		return encontrado;
	}

	public String enseniarZombie(int x, int y) {
		return board.enseniarZombie(x, y);
	}

	public void disparar(int danio, int x, int y) {
		this.board.disparar(danio, x, y);
	}

	public void explotar(int danio, int x, int y) {
		board.explotar(danio, x, y);
	}

	public boolean pierde() {
		return board.comprobarDerrota();
	}

	public int getMaxZombies() {
		return nivel.getNum_zombies();
	}

	public String getNivel() {
		return nivel.getNivel();
	}

	public int getSeed() {
		return this.seed;
	}

	public int getNumeroZombies() {
		return board.getNumeroZombies();
	}

	public int getNumeroPlantas() {
		return board.getNumeroPlantas();
	}

	public String debugPlant(int i) {
		return board.getDebugPlant(i);
	}

	public String debugZombie(int i) {
		return board.getDebugZombie(i);
	}

	public boolean casillaOcupada(int x, int y) {
		return board.plantaEncontrada(x, y) || board.zombieEncontrado(x, y);
	}
	
	public void setExit() {
		this.exit = true;
	}
	
	public boolean isFinished() {
		boolean isFinished = false;
		if (pierde()) {
			isFinished = true;
			System.out.println("HAS PERDIDO D:");
		}
		else if (gana()) {
			isFinished = true;
			System.out.println("HAS GANADO :D");
		}
		else if (exit) {
			isFinished = true;
			System.out.println("\nHAS SALIDO DEL JUEGO CON EXIT(O)");
		}
		return isFinished;
	}

	public void changeMode(String mode) {
		if (mode.equalsIgnoreCase("DEBUG")) {
			this.printer = new DebugPrinter();
		}
		else this.printer = new ReleasePrinter();
	}

	public void printGame() {
		this.printer.printGame(this);
	}

	public void store(BufferedWriter output) throws IOException {
		output.newLine();
		output.write("cycle: " + getCycle());
		output.newLine();
		output.write("sunCoins: " + this.suncoin.getSuncoins());
		output.newLine();
		output.write("level: " + this.nivel.getNivel());
		output.newLine();
		output.write("remZombies: " + getRemainingZombies());
		output.newLine();
		this.board.store(output);
		output.newLine();
	}
	
	public int getNumZombies() {
		return this.nivel.getNum_zombies();
	}

	public void load(BufferedReader input) throws CommandExecuteException{
		Game game = new Game(this);
		try {
			String[] words = loadLine(input, "cycle", false);
			this.cycleCount = Integer.parseInt(words[0]);
			words = loadLine(input, "sunCoins", false);
			this.suncoin.setSuncoins(Integer.parseInt(words[0]));
			words = loadLine(input, "level", false);
			this.nivel = new Level(words[0]);
			words = loadLine(input, "remZombies", false);
			this.board.setRemZombies(Integer.parseInt(words[0]));
			String plantStrings[] = loadLine(input, "plantList", true);
			String zombieStrings[] = loadLine(input, "zombieList", true);
			this.board.setPlantList(plantStrings, zombieStrings, this);
			
		}
		catch (IOException | FileContentsException | IllegalArgumentException fe) {
			this.suncoin = game.getSuncoinManager();
			this.zm = game.getZombieManager();
			this.board = game.getBoard();
			this.nivel = game.getLevel();
			this.seed = game.getSeed();
			this.exit = false;
			this.r = new Random(this.seed);
			this.printer = new ReleasePrinter();
			throw new CommandExecuteException("No se ha podido cargar el juego");
		}
	}
	
	public String[] loadLine(BufferedReader inStream, String prefix, boolean isList) throws IOException, FileContentsException{
		String line = inStream.readLine().trim();
		// absence of the prefix is invalid
		if ( ! line . startsWith(prefix + ":") ) throw new FileContentsException(wrongPrefixMsg + prefix);
		// cut the prefix and the following colon off the line
		// then trim it to get the attribute contents
		String contentString = line. substring(prefix . length()+1).trim(); String[] words;
		// the attribute contents are not empty 
		if (! contentString. equals( "")) {
			if (! isList ) {
		// split non−list attribute contents into words
		// using 1−or−more−white−spaces as separator
				words = contentString.split("\\s+");
		// a non−list attribute with contents of more than one word is invalid 
				if ( words.length != 1) throw new FileContentsException(lineTooLongMsg + prefix); 
			} 
			else
		// split list attribute contents into words
		// using comma+0−or−more−white−spaces as separator 
				words = contentString.split(",\\s*");
		// the attribute contents are empty
		} 
		else {
		// a non−list attribute with empty contents is invalid
			if (! isList ) throw new FileContentsException(lineTooShortMsg + prefix);
		// a list attribute with empty contents is valid; // use a zero−length array to store its words
			words = new String[0]; 
		}
		return words; 
	}
	
}
