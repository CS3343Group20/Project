package controlSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lift.Lift;
import lift.loadState.*;

public class CMS{
	private static CMS instance=new CMS();
	List<Lift> liftList;
	RequestSystem reqSys;
	int time;
	private CMS() {
		reqSys=new RequestSystem(this);
		liftList=new ArrayList();
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
		// TODO Auto-generated method stub
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
		int shortestDistance=Integer.MAX_VALUE;
		Lift assignLift=null;
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
			if (assignLift.getStatus()=="idle") {
				assignLift.setStatus(new Loaded());
			}
		}
	}
	
	public int calculateDistance(Lift lift,Request req) {
		return Math.abs(req.getPassenger().getCurrentFloor()-lift.getCurrentFloor());
	}
	
	public boolean curHaveRequest() {
		return reqSys.getAllReq().isEmpty();
	}
	public boolean curFlrHaveRequest(int f) {
		return reqSys.getAllReq().get(f).isEmpty();
	}
	public boolean sameDir(Lift lift,Passenger p) {
		return lift.getDirection()==p.getDirection();
	}
	
}
