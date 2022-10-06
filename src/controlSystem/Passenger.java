package controlSystem;

public class Passenger {
	private int weight;
	private int currentFloor;
	private int targetFloor;
	private String requestDirection;
	private int targetDirection;
	
	public void makeRequest(int requestTime) {
		RequestSystem.request(this,requestTime);
	}
	public Passenger(int weight,int cfloor, int tfloor){
		this.weight=weight;
		this.currentFloor=cfloor;
		this.targetFloor=tfloor;
	}
}
