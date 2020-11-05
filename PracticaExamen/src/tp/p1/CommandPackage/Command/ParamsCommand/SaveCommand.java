package tp.p1.CommandPackage.Command.ParamsCommand;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;
import tp.p1.GamePackage.Game;
import tp.p1.util.MyStringUtils;

public class SaveCommand extends Command {

	final static String commandText = "SAVE";
	final static String commandTextMsg = "\n[S]ave <filename>: Save the state of the game to a file.";
	final static String helpTextMsg = "";
	private String data_name;
	
	public SaveCommand() { // constructor identificativo 
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	public SaveCommand(String nombre_archivo) { //constructor definitivo
		super(commandText, commandTextMsg, helpTextMsg);
		this.data_name = nombre_archivo;
	}
	
	public boolean execute(Game game) throws CommandExecuteException { //completar
		boolean print = false;
		try (BufferedWriter output = new BufferedWriter(new FileWriter(this.data_name + ".dat"))) {
			output.write("PlantsVsZombies v3.0");
			output.newLine();
			game.store(output);
			print = true;
			System.out.println("\nGame successfully saved in file '" + this.data_name + ".dat'. Use the load command to reload it");
		}
		catch (IOException io) {
			throw new CommandExecuteException("Ha habido un fallo en el guardado");
		}
		return print;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command type = null;
		if (commandWords[0].equalsIgnoreCase(commandText) || commandWords[0].equalsIgnoreCase(AddCommand.commandText.substring(0, 1))) {
			if (commandWords.length == 2) {
				if (MyStringUtils.isValidFilename(commandWords[1])) {
					type = new SaveCommand(commandWords[1]);
				}
				else throw new CommandParseException("El nombre del archivo no es valido, consulta el comando <help> para mas informacion");
			}
			else throw new CommandParseException("Numero incorrecto de parametros, consulta el comando <help> para mas informacion");
		}
		return type;
	}
}
	
