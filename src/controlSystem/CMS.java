package controlSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import building.Building;
import building.Floor;
import lift.Lift;
import lift.loadState.Loaded;
import time.TimeConverter;

public class CMS{
	private Building building;
	private static CMS instance=new CMS();
	List<Lift> liftList;
	RequestSystem reqSys;
	int time;
	
	private CMS() {
		reqSys=new RequestSystem(this);
		liftList=new ArrayList<Lift>();
		this.building= new Building(20);
	}
	public void createLift(int capacity) {
		liftList.add(new Lift(capacity));
		System.out.println("Lift created!");
	}
	public static CMS getInstance() {return instance;}
	public RequestSystem getReqSys() {return reqSys;}
	public void setCurrentTime(int t) {time=t;}
	public int getCurrentTime() {return time;}

	public void assignClosest(int reqf,int reqDir) {	
		int shortestDistance=Integer.MAX_VALUE;
		Lift assignLift=null;
		int reqFloor=reqf;
		for (Lift lift:liftList) {
			int dir=lift.getDirection();
			// if status ok, same dir, not passed
			int distance1= calculateDistance(lift,reqf);
			int distance2=lift.checkClosestFromPassenger(dir,reqf,reqDir);
			if ((checkAvailablity(lift,reqDir,reqf))&&(distance1<shortestDistance)) {//check if current lift is able to pick up the passenger in req flr
				assignLift=lift;
				shortestDistance=distance1;
			}
			else if(distance2<shortestDistance){
				//check whether the lift will be closest after finishing the current travel
				assignLift=lift;
				shortestDistance=distance2;
				}
			}
		if (assignLift!=null) {
			updateLiftInfo(assignLift);
			updatefloorInfo(assignLift , reqDir, building, reqFloor,reqf);
		}
	}

	private void updatefloorInfo(Lift assignLift, int reqDir, Building building2, int reqFloor, int reqf) {
		//lift assign to that floor request and set flag to prevent future allocation
		if(reqDir==1) {
			if(!assignLift.getUpReqFloorList().contains(reqFloor)) {//does not contains up request from that floor before
				assignLift.getUpReqFloorList().add(reqFloor);
			}
			building.getFlrMap().get(reqf).setUpflag(true);
			assignLift.setReqDir(1);
		}	
		else {
			if(!assignLift.getDownReqFloorList().contains(reqFloor)) {//does not contains down request from that floor before
				assignLift.getDownReqFloorList().add(reqFloor);
			}
			building.getFlrMap().get(reqf).setDownflag(true);
			assignLift.setReqDir(0);
		}
	}
	private void updateLiftInfo(Lift assignLift) {
		if (assignLift.getStatus().equals("idle")) {
			assignLift.setStatus(new Loaded());
		}
	}
	private boolean checkAvailablity(Lift lift, int reqDir,int reqf) {
		String status=lift.getStatus();
		if (status.equals("idle"))
			return true;
		else if((status.equals("loaded"))&&(sameDir(lift,reqDir))) {
			return(!checkPassed(lift,reqDir,reqf));
		}
		return false;
	}
	private boolean checkPassed(Lift lift,int dir, int reqf) {
		if (dir==1) {//go up
			return(lift.getCurrentFloor()>reqf);
		}
		else {//go down
			return(lift.getCurrentFloor()<reqf);
		}
	}


	public void operate(int curTime) {
		int i=0;
		System.out.printf("%nCurrent time: %s%n",TimeConverter.fromStoTime(curTime));
		for (Lift lift:liftList) {//operate each lift
			System.out.printf("-----------------------------------%n");
			System.out.printf("lift %s in %s/F (%s)%n",i,lift.getCurrentFloor(),TimeConverter.fromStoTime(curTime));
			lift.getHandler().handleCurrentFloor(lift.getCurrentFloor(),i);//handle all req & passenger on cur flr
			lift.move();//directon handle
			if (lift.getStatus().equals("idle")) {
				System.out.printf("lift %s is idling......%n",i);
			}
			else
				System.out.printf("lift %s moving to %s/F (%s)%n",i,lift.getCurrentFloor(),TimeConverter.fromStoTime(curTime));
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
	public Building getBuilding() {return building;}
	public List<Lift> getLiftList(){return liftList;}
	private boolean sameDir(Lift lift, int dir) {return lift.getDirection()==dir;}
	private int calculateDistance(Lift lift, int reqf) {return  Math.abs(reqf-lift.getCurrentFloor());}
	public boolean curHaveRequest() {return (!reqSys.getAllReq().isEmpty()); }
	public boolean flrHaveRequest(int f) {return this.building.getFlrMap().get(f).haveReq();}
	public void assignLift(Building building) {
		for (Map.Entry<Integer, Floor> flrMap: building.getFlrMap().entrySet()) {//check all req in all floor
			Floor f=flrMap.getValue();
			if (f.haveUpReq(time)&&!f.getUpflag()) {//have request and not yet accepted by any lift
				assignClosest(flrMap.getKey(),1);
			}
			if (f.haveDownReq(time)&&!f.getDownflag()) {
				assignClosest(flrMap.getKey(),0);
			}
		}
		
	}
}
