package tp.p1.CommandPackage.Command.ParamsCommand.Commands;

import tp.p1.CommandPackage.Command.ParamsCommand.NoParamsCommand;
import tp.p1.GamePackage.Game;

public class ExitCommand extends NoParamsCommand {
	
	final static String commandText = "EXIT";
	final static String commandTextMsg = "";
	final static String helpTextMsg = "\n[E]xit: Terminates the program.";
	
	public ExitCommand() {
		super(commandText, commandTextMsg, helpTextMsg);
	}

	public boolean execute(Game game) {
		game.setExit();
		return true;
	}
	
	public String helpText() {
		return ExitCommand.helpTextMsg;
	}

}
