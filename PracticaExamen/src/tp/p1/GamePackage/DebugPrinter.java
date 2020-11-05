package tp.p1.GamePackage;

public class DebugPrinter extends BoardPrinter {

	public DebugPrinter( ) {
		super();
		this.dimX = 1;
	}
	
	public void printGame(Game game) {
		encodeGame(game);
		System.out.println("\nNumber of cycles: " + game.getCycle());
		System.out.println("Sun coins: " + game.getSuncoins());
		System.out.println("Remaining zombies: " + game.getRemainingZombies());
		System.out.println("Level: " + game.getNivel());
		System.out.println("Seed: " + game.getSeed());
		System.out.println(boardtoString(18));
	}

	public void encodeGame(Game game) {
		this.dimY = game.getNumeroPlantas() + game.getNumeroZombies();
		this.board = new String[dimX][dimY];
		int j = 0;
		int k = 0;
		for (int i = 0; i < dimY; i++) {
			if (i < game.getNumeroPlantas()) {
				board[k][i] = game.debugPlant(i);
			}
			else {
				board[k][i] = game.debugZombie(j);
				j++;
			}
		}
	}

}
