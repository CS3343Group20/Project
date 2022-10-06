package exceptions;

public class OverWeightException extends Exception{
	public OverWeightException() {
		super("Lift will overload, reject new passenger!");
	}
}
