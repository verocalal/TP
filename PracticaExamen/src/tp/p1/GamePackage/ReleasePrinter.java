package tp.p1.GamePackage;

public class ReleasePrinter extends BoardPrinter {
	
	public ReleasePrinter() {
		super();
		this.dimX = 4;
		this.dimY = 8;
	}

	public void printGame(Game game) {
		encodeGame(game);
		System.out.println("\nNumber of cycles: " + game.getCycle());
		System.out.println("Sun coins: " + game.getSuncoins());
		System.out.println("Remaining zombies: " + game.getRemainingZombies() + "\n");
		System.out.println(boardtoString(7));
	}

	public void encodeGame(Game game) {
		board = new String[dimX][dimY];
		for(int i = 0; i < dimX; i++) {
			for(int j = 0; j < dimY; j++) {
				if(game.buscarPlanta(i, j)) {
					board[i][j] = game.enseniarPlanta(i, j);
				}
				else if (game.buscarZombie(i, j)) {
					board[i][j] = game.enseniarZombie(i, j);
				}
				else {
					board[i][j] = space;
				}
			}
		}
	}
}
