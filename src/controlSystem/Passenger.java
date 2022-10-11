package controlSystem;

public class Passenger {
	private int weight;
	private int currentFloor;
	private int targetFloor;
	private String reqDir;
	private int direction;
	private RequestSystem reqSys;
	
	public void makeRequest(int requestTime) {
		reqSys.request(new Request(this,requestTime));
	}
	public Passenger(int weight,int cfloor, int tfloor){
		this.weight=weight;
		this.currentFloor=cfloor;
		this.targetFloor=tfloor;
		if (currentFloor<targetFloor) {
			direction=1;
		}
		else direction=0;
		reqSys=CMS.getInstance().getReqSys();
	}
	public int getCurrentFloor() {return currentFloor;}
	public int getTargetFloor() {return targetFloor;}
	public int getDirection() {return direction;}
	public int getWeight() {return weight;}
}
