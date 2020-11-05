package tp.p1.CommandPackage.Command.ParamsCommand;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;
import tp.p1.GamePackage.Game;

public class PrintModeCommand extends Command {
	
	private String mode;
	final static String commandText = "PRINTMODE";
	final static String commandTextMsg = "\n[P]rintMode release|debug";
	final static String helpTextMsg = "";
	
	public PrintModeCommand() { //constructor identificativo
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	public PrintModeCommand(String mode) { //constructor definitivo
		super(commandText, commandTextMsg, helpTextMsg);
		this.mode = mode;
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean print = false;
		if (this.mode.equalsIgnoreCase("RELEASE")) {
			game.changeMode(getMode());
			print  = true;
		}
		else if (this.mode.equalsIgnoreCase("DEBUG")) {
			game.changeMode(getMode());
			print = true;
		}
		else throw new CommandExecuteException("Los parametros no son los correctos, consulta el comando <help> para mas informacion");
		return print;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		
		Command type = null;
			if ((commandWords[0].equalsIgnoreCase(PrintModeCommand.commandText) || commandWords[0].equalsIgnoreCase(PrintModeCommand.commandText.substring(0, 1)))) {
				if (commandWords.length == 2) {
					type = new PrintModeCommand(commandWords[1]);
				}
				else throw new CommandParseException("El numero de parametros es incorrecto, consulta el comando <help> para mas informacion");
			}
		
		
		
		return type;
	}
	
	public String getMode() {
		return this.mode;
	}

}
