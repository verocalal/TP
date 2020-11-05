package tp.p1.CommandPackage.Command.ParamsCommand.Commands;

import tp.p1.CommandPackage.Command.ParamsCommand.NoParamsCommand;
import tp.p1.GamePackage.Game;

public class NoneCommand extends NoParamsCommand {
	
	final static String commandText = "";
	final static String commandTextMsg = "";
	final static String helpTextMsg = "\n[none]: Skips cycle.";
	
	public NoneCommand() {
		super(commandText, commandTextMsg, helpTextMsg);
	}

	public boolean execute(Game game) {
		game.update();
		return true;
	}
	
	public String helpText() {
		return NoneCommand.helpTextMsg;
	}
}
