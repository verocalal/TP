package tp.p1.GamePackage;

import tp.p1.util.MyStringUtils;

public abstract class BoardPrinter implements GamePrinterj {
	
	protected String[][] board;
	int dimX; 
	int dimY;
	final String space = " ";
	
	public BoardPrinter() {
		
	}
	 // printer ayudado de la funcion dada en la practica 1, con las dimensiones necesarias dibuja un tablero
	public abstract void encodeGame(Game game);
	
	public String boardtoString(int size) {

		int cellSize = size;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		String rowDelimiter = MyStringUtils.repeat(hDelimiter, (dimY * (cellSize + 1)) - 1);
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineDelimiter);
		
		for(int i=0; i<dimX; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<dimY; j++) {
					str.append( MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
				}
				str.append(lineDelimiter);
		}
		return str.toString();
	}

}
