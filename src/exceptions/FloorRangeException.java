package exceptions;

public class FloorRangeException extends Exception{
	public FloorRangeException(){
		super("Floor range incorrect");
		System.out.println("Floor range incorrect");
	}
}
