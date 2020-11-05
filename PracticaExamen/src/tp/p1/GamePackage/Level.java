package tp.p1.GamePackage;

public class Level {
	
	private enum nivel{EASY, HARD, INSANE}
	private nivel level;
	private int num_zombies;
	private int frecuencia;
	private String nombreNivel;
	
	public Level(String dificultad) {
		this.level = nivel.valueOf(dificultad);
		if (level.equals(nivel.EASY)) {
			this.num_zombies = 3;
			this.frecuencia = 1;
			this.nombreNivel = "EASY";
		}
		else if (level.equals(nivel.HARD)) {
			this.num_zombies = 5;
			this.frecuencia = 2;
			this.nombreNivel = "HARD";
		}
		else {
			this.num_zombies = 10;
			this.frecuencia = 3;
			this.nombreNivel = "INSANE";
		}
	}
	
	public int frecuencia() {
		int frec = this.frecuencia;
		return frec;
	}
	
	public int getNum_zombies() {
		return this.num_zombies;
	}

	public String getNivel() {
		return this.nombreNivel;
	}
	
}
