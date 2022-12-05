package main;

import controlSystem.CMS;
import controlSystem.Request;

public class Passenger {
	private int weight;
	private int currentFloor;
	private int targetFloor;
	private int direction;

	
	public void makeRequest(int requestTime) {
		CMS.getInstance().getReqSys().request(new Request(this,requestTime));
	}
	public Passenger(int weight,int cfloor, int tfloor){
		this.weight=weight;
		this.currentFloor=cfloor;
		this.targetFloor=tfloor;
		direction=((currentFloor<targetFloor)? 1:0);

	}
	public int getCurrentFloor() {return currentFloor;}
	public int getTargetFloor() {return targetFloor;}
	public int getDirection() {return direction;}
	public int getWeight() {return weight;}
}
