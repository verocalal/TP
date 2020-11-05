package tp.p1.CommandPackage.Command.ParamsCommand.Commands;

import tp.p1.CommandPackage.Command.ParamsCommand.NoParamsCommand;
import tp.p1.GamePackage.Game;
import tp.p1.PlantFactory.PlantFactory;

public class ListCommand extends NoParamsCommand {

	final static String commandText = "LIST";
	final static String commandTextMsg = "";
	final static String helpTextMsg = "\n[L]ist: Prints the list of available plants.";
	
	public ListCommand() {
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	public boolean execute(Game game) {
		System.out.println(PlantFactory.listOfAvilablePlants());
		return false;
	}
	
	public String helpText() {
		return ListCommand.helpTextMsg;
	}
}
