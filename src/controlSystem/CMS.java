package controlSystem;

import java.util.ArrayList;
import java.util.List;

import building.Building;
import lift.Lift;
import lift.loadState.Loaded;

public class CMS{
	private Building b;
	private static CMS instance=new CMS();
	List<Lift> liftList;
	RequestSystem reqSys;
	int time;
	int runningLift;
	
	private CMS() {
		reqSys=new RequestSystem(this);
		liftList=new ArrayList<Lift>();
		runningLift=0;
		this.b= new Building(20);
	};
	public static CMS getInstance() {
		return instance;
	}
	public void createLift(int capacity) {
		liftList.add(new Lift(capacity));
		System.out.println("Lift created!");
	}
	public RequestSystem getReqSys() {
		return reqSys;
	}
	public int getRunningLift() {return runningLift;}
	public void setRunningLift(int i) {
		runningLift=i;
	}
	public void setCurrentTime(int t) {time=t;}
	public int getCurrentTime() {return time;}
	public boolean checkAvailablity(Lift lift,Request r) {
		String status=lift.getStatus();
		if (status.equals("idle"))
			return true;
		else if(status.equals("loaded")) {
			Passenger p=r.getPassenger();
			if(sameDir(lift,p)) {
				if(checkPassed(lift,p)) {
					return false;
				}
				else return true;
			}
		}
		return false;
	}
	public boolean checkPassed(Lift lift, Passenger p) {//only triggers when same direction
		int dir=lift.getDirection();
		if (dir==1) {//go up
			if(lift.getCurrentFloor()>p.getCurrentFloor())
				return true;
			else return false;
		}
		else {//go down
			if(lift.getCurrentFloor()<p.getCurrentFloor())
				return true;
			else return false;
		}
	}
	public void assignClosest(Request req) {
		//TODO check if any lift has request on that floor already, if yes then assign that lift
		
		int shortestDistance=Integer.MAX_VALUE;
		Lift assignLift=null;
		int reqFloor=req.getPassenger().getCurrentFloor();
		for (Lift lift:liftList) {
			if (checkAvailablity(lift,req)) {
				int distance= calculateDistance(lift,req);
				if(distance<shortestDistance) {
					assignLift=lift;
					shortestDistance=distance;
				}
			}
		}
		if (assignLift!=null) {
			if (assignLift.getStatus().equals("idle")) {
				assignLift.setStatus(new Loaded());
				runningLift++;
			}
			if(!assignLift.getReqFloorList().contains(reqFloor)) {//does not contains request from that floor before
				assignLift.getReqFloorList().add(reqFloor);
			}
		}
	}
	public void assignClosest2(int reqf,int dir) {
		//TODO check if any lift has request on that floor already, if yes then assign that lift
		
		int shortestDistance=Integer.MAX_VALUE;
		Lift assignLift=null;
		int reqFloor=reqf;
		for (Lift lift:liftList) {
			if (checkAvailablity2(lift,dir,reqf)) {
				int distance= calculateDistance2(lift,reqf);
				if(distance<shortestDistance) {
					assignLift=lift;
					shortestDistance=distance;
				}
			}
		}
		if (assignLift!=null) {
			if (assignLift.getStatus().equals("idle")) {
				assignLift.setStatus(new Loaded());
				runningLift++;
			}
			if(!assignLift.getReqFloorList().contains(reqFloor)) {//does not contains request from that floor before
				assignLift.getReqFloorList().add(reqFloor);
			}
			//lift assign to that floor request and set flag to prevent future allocation
			if(dir==1) {
				b.getFlrMap().get(reqf).setUpflag(true);
				assignLift.setReqDir(1);
			}	
			else {
				b.getFlrMap().get(reqf).setDownflag(true);
				assignLift.setReqDir(0);
			}
				
		}
	}
	private boolean checkAvailablity2(Lift lift, int dir,int reqf) {
		String status=lift.getStatus();
		if (status.equals("idle"))
			return true;
		else if(status.equals("loaded")) {
			if(sameDir2(lift,dir)) {
				if(checkPassed2(lift,dir,reqf)) {
					return false;
				}
				else return true;
			}
		}
		return false;
	}
	private boolean checkPassed2(Lift lift,int dir, int reqf) {
		if (dir==1) {//go up
			if(lift.getCurrentFloor()>reqf)
				return true;
			else return false;
		}
		else {//go down
			if(lift.getCurrentFloor()<reqf)
				return true;
			else return false;
		}
	}
	private boolean sameDir2(Lift lift, int dir) {
		return lift.getDirection()==dir;
	}
	private int calculateDistance2(Lift lift, int reqf) {
		return  Math.abs(reqf-lift.getCurrentFloor());
	}
	public int calculateDistance(Lift lift,Request req) {
		return Math.abs(req.getPassenger().getCurrentFloor()-lift.getCurrentFloor());
	}
	
	public boolean curHaveRequest(int curTime) {
		if(reqSys.getAllReq().isEmpty())
			return false;
		else if(reqSys.getAllReq().get(0).getRequestTime()<=curTime)
			return true;
		else return false;
	}
	public boolean curHaveRequest() {
		if(reqSys.getAllReq().isEmpty())
			return false;
		else 
			return true;
	}
//	public boolean curFlrHaveRequest(int f) {
//		return reqSys.getEachFloorReq().get(f).isEmpty();
//	}
	public boolean sameDir(Lift lift,Passenger p) {
		return lift.getDirection()==p.getDirection();
	}
	public void operate(int curTime) {
		int i=0;
		System.out.println("Current time: "+curTime);
		for (Lift lift:liftList) {
			System.out.printf("-----------------------------------%n");
			System.out.printf("lift %s in %s/F (%s)%n",i,lift.getCurrentFloor(),curTime);
			lift.getHandler().handleCurrentFloor(lift.getCurrentFloor(),i);
			lift.move();//directon pre handle
			if (lift.getStatus().equals("idle")) {
				System.out.printf("lift %s is idling......%n",i);
			}
			else
				System.out.printf("lift %s moving to %s/F (%s)%n",i,lift.getCurrentFloor(),curTime);
			i++;
		}
	}
	public boolean anyLiftRunning() {
		for(Lift lift:liftList) {
			if (!lift.getStatus().equals("idle"))
				return true;
		}
		return false;
	}
	public Building getBuilding() {
		return b;
	}
}
