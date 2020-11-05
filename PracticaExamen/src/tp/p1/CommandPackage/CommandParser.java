package tp.p1.CommandPackage;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.CommandPackage.Command.ParamsCommand.AddCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.LoadCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.PrintModeCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.SaveCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.Commands.ExitCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.Commands.HelpCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.Commands.ListCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.Commands.NoneCommand;
import tp.p1.CommandPackage.Command.ParamsCommand.Commands.ResetCommand;
import tp.p1.Exceptions.CommandParseException;

public class CommandParser {
	
	private static Command[] availableCommands = { // Esta es la lista de los comandos existentes
		new AddCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(), 
		new ListCommand(), 
		new NoneCommand(),
		new PrintModeCommand(),
		new SaveCommand(),
		new LoadCommand()
	};
	
	public static Command parseCommand(String[ ] commandWords) throws CommandParseException {
		Command type = null;
		int i = 0; 
		while (i < availableCommands.length && type == null) { 	// Este while recorreo los parse de todos los comandos y comprueba que 
			type = availableCommands[i].parse(commandWords);	// el texto introducido por el jugador coincide con alguno, si es así, se detiene el while
			i++;
		}
		return type; // Devuelve el tipo de comando al que se refiere, y en caso de no existir, devuelve NULL
	}
	
	public static String commandHelp() {
		String text = "";
		for (int i = 0; i < availableCommands.length; i++) {
			text += availableCommands[i].helpText(); // Imprime el help de cada comando
		}
		return text;
	}
	
}



