package tp.p1.CommandPackage.Command.ParamsCommand.Commands;

import tp.p1.CommandPackage.Command.ParamsCommand.NoParamsCommand;
import tp.p1.GamePackage.Game;

public class ResetCommand extends NoParamsCommand {

	final static String commandText = "RESET";
	final static String commandTextMsg = "";
	final static String helpTextMsg = "\n[R]eset: Starts a new game.";
	
	public ResetCommand() {
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	public boolean execute(Game game) {
		game.reset();
		return true;
	}
	
	public String helpText() {
		return ResetCommand.helpTextMsg;
	}

}
