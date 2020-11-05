package tp.p1.Exceptions;

public class CommandExecuteException extends Exception { //declaracion de la excepción con los dos correspondientes constructores

	public CommandExecuteException() {
		super("El comando no se ha podido ejecutar");
	}
	
	public CommandExecuteException(String mensaje) {
		super(mensaje);
	}

}
