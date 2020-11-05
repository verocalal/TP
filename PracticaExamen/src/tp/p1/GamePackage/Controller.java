package tp.p1.GamePackage;

import java.util.Scanner;

import tp.p1.CommandPackage.CommandParser;
import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;

import java.lang.String;

public class Controller {
	private Game gm;
	private Scanner in;
	public Controller(Level dificultad, int seed, String mode) {
		gm = new Game(dificultad, seed, mode);
		in = new Scanner(System.in);
		
	}
	
	public void run() { // run preparado para no ser modificado gracias al patron Command y plantFactory
		printGame(gm);
		while (!gm.isFinished()) {
			System.out.print("Command > ");
			String[] words = in.nextLine().toLowerCase().trim().split("\\s+"); 
			try {
				Command command = CommandParser.parseCommand(words);
				if (command != null) { 
					if (command.execute(gm)) {
						printGame(gm);
					}
				}
				else {
					System.out.println("\nComando desconocido\n");
				}
			}
			catch (CommandParseException | CommandExecuteException ex) {
				System.out.format("\n" + ex.getMessage() + "%n %n");
			}
			
		}
	}
	
	private void printGame(Game gm) {
		gm.printGame();
	}
}

