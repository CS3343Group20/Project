package exceptions;

public class FloorRangeException extends Exception{
	public FloorRangeException() {
		super("Floor out of range. Please try again.");
		System.out.println("Floor out of range. Please try again.");
	}
}
