package tp.p1.CommandPackage.Command.ParamsCommand;

import tp.p1.CommandPackage.Command.Command;
import tp.p1.Exceptions.CommandExecuteException;
import tp.p1.Exceptions.CommandParseException;
import tp.p1.GameObject.Plant;
import tp.p1.GamePackage.Game;
import tp.p1.PlantFactory.PlantFactory;

public class AddCommand extends Command {
	

	private int x;
	private int y;
	private String plantName;
	final static String commandText = "ADD";
	final static String commandTextMsg = "\n[A]dd <plant> <x> <y>: Adds a plant in position x, y.";
	final static String helpTextMsg = "";
	
	public AddCommand() {
		super(commandText, commandTextMsg, helpTextMsg); // constructor para identificar el comando
	}
	
	public AddCommand(int x, int y, String plantName) { // constructor definitivo
		super(commandText, commandTextMsg, helpTextMsg);
		this.x = x;
		this.y = y;
		this.plantName = plantName;
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean print = false;
		Plant plant = PlantFactory.getPlant(getplantName());
		if (plant != null) {
			plant = plant.parse(getplantName());
			if (this.x < 4 && this.y < 7 && game.addPlantToGame(plant, getX(), getY())) {
				game.update();
				print = true;
			}
		}
		else throw new CommandExecuteException("La planta introducida es incorrecta, consulta el comando <list> para mas informacion");
		return print;
	}
	
	public String getplantName() {
		return this.plantName;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command type = null;
		try {
			if (commandWords[0].equalsIgnoreCase(commandText) || commandWords[0].equalsIgnoreCase(AddCommand.commandText.substring(0, 1))) {
				if (commandWords.length == 4) {
					type = new AddCommand(Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[3]), commandWords[1]);
				}
				else throw new CommandParseException("El numero de parametros es incorrecto, consulta el comando <help> para mas informacion");
			}
		}
		catch (NumberFormatException ex) {
			throw new CommandParseException("Numeros incorrectos para los argumentos de AddCommand: [A]dd <plant> <x> <y>");
		}
		return type;
	}

}
