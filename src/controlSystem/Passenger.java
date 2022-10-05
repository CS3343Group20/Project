package controlSystem;

public class Passenger {
	private int weight;
	private int floor;
	private String requestDirection;
	private int targetDirection;
	
	public void makeRequest() {
		RequestSystem.request(this);
	}
}
