package tp.p1.CommandPackage.Command;

import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;
import tp.p1.GamePackage.Game;

public abstract class Command {
	
	private String helpText;
	protected final String commandName;
	
	public Command(String commandText, String commandTextMsg, String helpTextMsg) { 
		helpText = commandTextMsg;
		commandName = commandText;
	}
	
	// Some commands may generate an error in the execute or parse methods.
	
	// In the absence of exceptions , they must the tell the controller not to print the board 
	
	public abstract  boolean execute(Game game) throws CommandExecuteException;
	
	public abstract Command parse(String[] commandWords) throws CommandParseException;
	
	public String helpText() {
		return " " + helpText;
	}
}