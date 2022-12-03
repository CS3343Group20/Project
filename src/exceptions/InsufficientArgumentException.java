package exceptions;

public class InsufficientArgumentException extends Exception{
	public InsufficientArgumentException() {
		super("Insufficient command arguments. Please try again.");
		System.out.println("Insufficient command arguments. Please try again.");
	}
}
