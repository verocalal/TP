package tp.p1.CommandPackage.Command.ParamsCommand;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandParseException;

public abstract class NoParamsCommand extends Command{
	

	public NoParamsCommand(String commandText, String commandTextMsg, String helpTextMsg) {
		super(commandText, commandTextMsg, helpTextMsg);
	}

	public Command parse(String[] commandWords) throws CommandParseException {
		Command type = null;
		if (this.commandName != "") {
			if (commandWords[0].equalsIgnoreCase(this.commandName) || commandWords[0].equalsIgnoreCase(this.commandName.substring(0, 1))) {
				if (commandWords.length > 1) {
					throw new CommandParseException("Numero de parametros incorrecto, para mas ayuda teclea <help>");
				}
				type = this;
			}
		}
		else if (commandWords[0].equalsIgnoreCase(this.commandName)) type = this;
		return type;

	}
}
