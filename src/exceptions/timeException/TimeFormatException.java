package exceptions.timeException;

public class TimeFormatException extends Exception{
	public TimeFormatException(String string) {
		super(string);
		//System.out.println("Format Error: "+string);
	}
}
