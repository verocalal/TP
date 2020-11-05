package tp.p1.CommandPackage.Command.ParamsCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;
import tp.p1.GamePackage.Game;
import tp.p1.util.MyStringUtils;

public class LoadCommand extends Command {

	final static String commandText = "LOAD";
	final static String commandTextMsg = "\n[Lo]ad <filename>: Load the state of the game from a file.";
	final static String helpTextMsg = "";
	private String data_name;
	
	public LoadCommand() { //constructor identificativo
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	public LoadCommand(String nombre_archivo) { //constructor definitivo
		super(commandText, commandTextMsg, helpTextMsg);
		this.data_name = nombre_archivo;
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean print = false;
		try (BufferedReader input = new BufferedReader(new FileReader(this.data_name))) {
			String line = input.readLine();
			if (line != null && line.equalsIgnoreCase("PlantsVsZombies v3.0")) {
				line = input.readLine();
				game.load(input);
				print = true;
				System.out.println("\nGame successfully loaded from file " + this.data_name);
			}
			else {
				throw new CommandExecuteException();
			}
		}
		catch (IOException | CommandExecuteException io) {
			throw new CommandExecuteException("Ha habido un error al cargar el juego, consulta el comando <help> para mas informacion");
		}
		return print;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command type = null;
		if (commandWords[0].equalsIgnoreCase(commandText) || commandWords[0].equalsIgnoreCase(AddCommand.commandText.substring(0, 2))) {
			if (commandWords.length == 2) {
				if (MyStringUtils.isValidFilename(commandWords[1]) && MyStringUtils.fileExists(commandWords[1]) && MyStringUtils.isReadable(commandWords[1])) {
					type = new LoadCommand(commandWords[1]);
				}
				else throw new CommandParseException("Ha habido un problema al cargar " + commandWords[1] + ".dat, consulta el comando <help> para mas informacion");
			}
			else throw new CommandParseException("Numero incorrecto de parametros, consulta el comando <help> para mas informacion");
		}
		return type;
	}
}
	
