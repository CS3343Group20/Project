package lift;

import java.util.ArrayList;

import controlSystem.CMS;
import controlSystem.Passenger;
import lift.loadState.Idle;
import lift.loadState.Status;

public class Lift {
	private int capacity;
	private int loadWeight;
	private int direction;
	private int currentFloor;
	private Handler handler;
	private Status status;
	private ArrayList<Passenger> passengerList;
	private ArrayList<Integer> floorList;//show request floors that are accepted by current lift
	public Lift(int capacity) {
		direction=1;
		currentFloor=0;
		loadWeight=0;
		handler=new Handler(this);
		status = new Idle();
		passengerList = new ArrayList<Passenger>();
		floorList = new ArrayList<Integer>();
		this.capacity=capacity;
	}
	public int getDirection() {return direction;}
	public int getCapacity() {return capacity;}
	public int getCurrentFloor() {return currentFloor;}
	public int getLoadWeight() {return loadWeight;}
	public String getStatus() {return status.checkStatus();}
	public void setStatus(Status status) {
		this.status=status;
	}
	public void setLoadWeight(int weight) {
		loadWeight=weight;
	}
	public ArrayList<Passenger> getPassengerList() {return passengerList;}
	public ArrayList<Integer> getReqFloorList(){return floorList;}
	public Handler getHandler() {return handler;}
	
	public void move() {
		handler.directionHandle(CMS.getInstance().getCurrentTime());
		if (!this.getStatus().equals("idle")) {
			if (direction==1) {
				currentFloor++;
			}
			else {
				currentFloor--;
			}
		}
	}
	public void setDirection(int dir) {
		// TODO Auto-generated method stub
		direction=dir;
	}
}
