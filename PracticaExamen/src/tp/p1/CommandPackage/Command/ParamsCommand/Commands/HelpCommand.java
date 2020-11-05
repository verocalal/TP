package tp.p1.CommandPackage.Command.ParamsCommand.Commands;

import tp.p1.CommandPackage.CommandParser;
import tp.p1.CommandPackage.Command.ParamsCommand.NoParamsCommand;
import tp.p1.GamePackage.Game;

public class HelpCommand extends NoParamsCommand {
	
	final static String commandText = "HELP";
	final static String commandTextMsg = "";
	final static String helpTextMsg = "\n[H]elp: Prints this help message.";
	
	public HelpCommand() {
		super(commandText, commandTextMsg, helpTextMsg);
	}

	public boolean execute(Game game) {
		System.out.println(CommandParser.commandHelp() + "\n");
		return false;
	}
	
	public String helpText() {
		return HelpCommand.helpTextMsg;
	}
}
