package lift;

import java.util.ArrayList;

import controlSystem.CMS;
import lift.loadState.Idle;
import lift.loadState.Status;
import main.Passenger;

public class Lift {
	private int capacity;
	private int loadWeight;
	private int direction;
	private int requestDir;
	private int currentFloor;
	private Handler handler;
	private Status status;
	private ArrayList<Passenger> passengerList;
	private ArrayList<Integer> reqGoUpList;//show request floors that are accepted by current lift
	private ArrayList<Integer> downfloorList;
	public Lift(int capacity) {
		direction=1;
		requestDir=1;
		currentFloor=0;
		loadWeight=0;
		handler=new Handler(this);
		status = new Idle();
		passengerList = new ArrayList<Passenger>();
		reqGoUpList = new ArrayList<Integer>();
		downfloorList = new ArrayList<Integer>();
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
	public ArrayList<Integer> getUpReqFloorList(){return reqGoUpList;}
	public ArrayList<Integer> getDownReqFloorList(){return downfloorList;}
	public Handler getHandler() {return handler;}
	
	public void move() {
		handler.directionHandle();
		if (!this.getStatus().equals("idle")) {
			currentFloor=((direction==1)? currentFloor+1:currentFloor-1);
		}
	}
	public boolean isEmpty() {
		return passengerList.isEmpty();
	}
	public void setDirection(int dir) {
		direction=dir;
	}
	public void setReqDir(int reqDir) {
		requestDir=reqDir;		
	}
	public int getReqDir() {return requestDir;}
	public int totalAcceptedReq() {
		return (this.reqGoUpList.size()+this.downfloorList.size());
	}
	public int checkClosestFromPassenger(int dir, int reqf,int reqDir) {
		int shortestDist=Integer.MAX_VALUE;
		if(dir==1&&reqDir==0) {//if lift is going up but req is going down
			int highest=0;//initialise lowest target floor in passengerList
			for (Passenger p:passengerList) {
				highest=((p.getTargetFloor()>highest)? p.getTargetFloor():highest);
			}
			shortestDist = ((highest<reqf)? reqf-highest:highest-reqf);
		}
		else if(dir==0 && reqDir==1){//if lift is going down but req is going up
			int lowest=Integer.MAX_VALUE;//initialise highest target floor in passengerList
			for (Passenger p:passengerList) {
				lowest=((p.getTargetFloor()<lowest)? p.getTargetFloor():lowest);
			}
			//if lift is going down and the lowest flr passenger going is higher than the up request
			shortestDist = ((lowest<shortestDist)? lowest-reqf:reqf-lowest); 
		}
		return shortestDist;
	}
	public boolean haveReqAccepted() {
		return this.totalAcceptedReq()>0;
	}
	public boolean haveReqGoUp() {
		return reqGoUpList.size()>0;
	}
	public boolean haveReqGoDown() {
		return downfloorList.size()>0;
	}
}
