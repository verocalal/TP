package tp.p1.Exceptions;

public class CommandParseException extends Exception {

	public CommandParseException() {
		super("Comando incorrecto. Para obtener mas informacion teclea el comando <help>");
	}
	
	public CommandParseException(String mensaje) {
		super(mensaje);
	}
}  
