package lift;

import java.util.Iterator;

import building.Building;
import building.Floor;
import controlSystem.CMS;
import controlSystem.Request;
import controlSystem.RequestSystem;
import exceptions.OverWeightException;
import lift.loadState.Full;
import lift.loadState.Idle;
import main.Passenger;

public class Handler {
	protected Lift lift;
	protected CMS cms=CMS.getInstance();
	protected RequestSystem reqSys=cms.getReqSys();
	protected Building b=cms.getBuilding();
	public Handler(Lift lift) {
		this.lift=lift;
	};
	
	public void pickupPassenger(Passenger p) throws Exception{
		int newWeight=lift.getLoadWeight()+p.getWeight();
		if(newWeight>lift.getCapacity()) {
			throw new OverWeightException();
		}
		else {
			lift.setLoadWeight(newWeight);
			lift.getPassengerList().add(p);
		}
		
	}
	public void dropPassenger(Passenger p,Iterator<Passenger> itr,int i) {
		lift.setLoadWeight(lift.getLoadWeight()-p.getWeight());
		itr.remove();
		outputDroppedMsg(i);
	}

	public boolean curFloorHaveRequest2(int f) {
		if(cms.flrHaveRequest(f)) {
			return true;
		}	
		return false;
	}

	public void checkArriveToTarget(int i) {
		Iterator<Passenger> iterator=lift.getPassengerList().iterator();
		while(iterator.hasNext()){
			Passenger p=iterator.next();
			if(lift.getCurrentFloor()==p.getTargetFloor()) {
				dropPassenger(p,iterator,i);
			}
		}
	}
	public void directionHandle() {
		if(!lift.getStatus().equals("idle")) {
			if(!lift.haveReqAccepted()&&lift.isEmpty()) {//no request accepted and no passenger in lift
				if (isNotGoingToTheGround(lift)){
					lift.setDirection(0);
				}
			}
			else if(!lift.haveReqAccepted()&&!lift.isEmpty()) {//still have passenger in lift
				int dirflag=0;
				for (Passenger p:lift.getPassengerList()) {
					if (p.getDirection()==1) {
						dirflag=1;
						break;
					}
				}
				lift.setDirection(dirflag);	
			}
			else if(!lift.haveReqGoUp()&&lift.haveReqGoDown()&&lift.isEmpty()) {//accepted request to go down(not packed passenger yet)
				if(lift.getCurrentFloor()>lift.getDownReqFloorList().get(0)) {//if lift not arrived to target floor yet
					lift.setDirection(0);
				}
			}
			if(lift.getCurrentFloor()==0&&lift.isEmpty()&&!lift.haveReqAccepted()) {//reset lift status
				lift.setDirection(1);
				lift.setStatus(new Idle());
				outputArrivedGroundMsg();
			}
		}
	}

	public void handleCurrentFloor(int f,int index) {//index is lift no.
		if (curFloorHaveRequest2(f)) {
			Iterator<Request> iterator=null;
			int dirflag=-1;
			Floor flr=cms.getBuilding().getFlrMap().get((Integer) f);
			if(lift.haveReqGoUp()) {//assign upQueue to iterator
				iterator=flr.getUpQueue().iterator();
				dirflag=1;
			}
			else if(lift.haveReqGoDown()){//only trigger when there is only down request in current lift
				iterator=flr.getDownQueue().iterator();
				dirflag=0;
			}
			if (dirflag!=-1) {
				int count=0;
				while(iterator.hasNext()){
					Request r =iterator.next();		
					try {
						pickupPassenger(r.getPassenger());
						reqSys.deleteFromList(r);
						iterator.remove();
						count++;
						
					} catch (OverWeightException e) {
						outputIgnoreMsg(count);
						b.getFlrMap().get(f).setUpflag(false);
						lift.setStatus(new Full());
						break;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}	
				}
				if (!flr.haveUpReq()) {
					flr.setUpflag(false);
				}
				if (!flr.haveDownReq()) {
					flr.setDownflag(false);
				}
				outputLoadedMsg(index,count,lift.getCurrentFloor());
				if(dirflag==1)
					lift.getUpReqFloorList().remove((Integer) f);	
				else
					lift.getDownReqFloorList().remove((Integer) f);
			}
			
		}
		checkArriveToTarget(index);
	}
	
	private boolean isNotGoingToTheGround(Lift li) {
		return(li.getCurrentFloor()>0 && li.getDirection()==1);
	}

	private void outputIgnoreMsg(int c) {
		System.out.printf("Ignore people %s since overload%n",c);
	}

	private void outputLoadedMsg(int i, int c, int f) {
		System.out.printf("Lift %s Loaded %s people at %s/F %n",i,c,f);
	}

	private void outputArrivedGroundMsg() {
		System.out.printf("arrived to 0/F, idling...%n");
	}

	private void outputDroppedMsg(int i) {
		System.out.printf("lift %s dropped Passenger!%n",i);
	}
}
