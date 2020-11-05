package tp.p1.Exceptions;

public class FileContentsException extends Exception {
	
	public FileContentsException() {
		super("Comando incorrecto. Para obtener mas informacion teclea el comando <help>");
	}
	
	public FileContentsException(String mensaje) {
		super(mensaje);
	}

}
