package exceptions;

@SuppressWarnings("serial")
public class FormException extends Exception {

	public FormException(String message) {
		super(message);
	}
	
	public FormException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
