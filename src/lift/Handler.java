package lift;

import java.util.Iterator;

import controlSystem.CMS;
import controlSystem.Passenger;
import controlSystem.Request;
import controlSystem.RequestSystem;
import exceptions.OverWeightException;
import lift.loadState.Full;
import lift.loadState.Idle;

public class Handler {
	protected Lift lift;
	protected CMS cms=CMS.getInstance();
	protected RequestSystem reqSys=cms.getReqSys();
	public Handler(Lift lift) {
		this.lift=lift;
	};
	public int goUp() {
		return 1;
	};
	public int goDown() {
		return 0;
	}
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
		int newWeight=lift.getLoadWeight()-p.getWeight();
		lift.setLoadWeight(newWeight);
		itr.remove();
		System.out.printf("lift %s dropped Passenger!%n",i);
	}
//	public boolean curFloorHaveRequest(int f) {
//		if(reqSys.getEachFloorReq().get(f)==null) {
//			return false;
//		}	
//		return true;
//	}
	public boolean curFloorHaveAcceptedReq(int f) {
		return (lift.getUpReqFloorList().contains(f)||lift.getDownReqFloorList().contains(f));
	}
	public boolean curFloorHaveRequest2(int f) {
		if(cms.getBuilding().getFlrMap().get(f).haveUpReq()||cms.getBuilding().getFlrMap().get(f).haveDownReq()) {
			return true;
		}	
		return false;
	}
//	public void handleCurrentFloor(int f,int curTime) {
//		if (curFloorHaveRequest(f)) {
//			Iterator<Request> iterator=reqSys.getEachFloorReq().get(f).iterator();
//			while(iterator.hasNext()){
//				Request r =iterator.next();
//				if(r.getRequestTime()<=curTime&&r.getPassenger().getDirection()==lift.getDirection()) {
//					try {
//						pickupPassenger(r.getPassenger());
//						reqSys.getAllReq().remove(r);
//						iterator.remove();
//						System.out.println("Loaded people at"+lift.getCurrentFloor()+"F");
//					} catch (OverWeightException e) {
//						System.out.println("Ignore this people since overload");
//						lift.setStatus(new Full());
//						break;
//					}
//					catch (Exception e) {
//						e.printStackTrace();
//						break;
//					}
//				}
//			}
//		}
//		lift.getReqFloorList().remove((Integer) f);
//		checkArriveToTarget();
//	}
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
			if(lift.totalAcceptedReq()==0&&lift.isEmpty()) {//no request accepted and no passenger in lift
				if (lift.getCurrentFloor()>0 && lift.getDirection()==1) {//get back to ground
					lift.setDirection(0);
				}
			}
			else if(lift.totalAcceptedReq()==0&&lift.getPassengerList().size()>0) {
				int dirflag=0;
				for (Passenger p:lift.getPassengerList()) {
					if (p.getDirection()==1) {
						dirflag=1;
						break;
					}
				}
				lift.setDirection(dirflag);	
			}
			else if(lift.getUpReqFloorList().size()==0&&lift.getDownReqFloorList().size()>0&&lift.isEmpty()) {
				if(lift.getCurrentFloor()>lift.getDownReqFloorList().get(0)) {
					lift.setDirection(0);
				}
			}
			if(lift.getCurrentFloor()==0&&lift.isEmpty()&&lift.totalAcceptedReq()==0) {
				lift.setDirection(1);
				lift.setStatus(new Idle());
				cms.setRunningLift(cms.getRunningLift()-1);
			}
		}
	}
	public void handleCurrentFloor(int f,int index) {//testing
		if (curFloorHaveRequest2(f)) {
			Iterator<Request> iterator=null;
			int dirflag=-1;
			if(lift.getUpReqFloorList().size()>0) {
				iterator=cms.getBuilding().getFlrMap().get((Integer) f).getUpQueue().iterator();
				dirflag=1;
			}
			else if(lift.getDownReqFloorList().size()>0){
				iterator=cms.getBuilding().getFlrMap().get((Integer) f).getDownQueue().iterator();
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
						System.out.printf("Ignore people %s since overload%n",count);
						lift.setStatus(new Full());
						break;
					}
					catch (Exception e) {
						e.printStackTrace();
						break;
					}	
				}
				System.out.printf("Lift %s Loaded %s people at %s/F %n",index,count,lift.getCurrentFloor());
				if(dirflag==1)
					lift.getUpReqFloorList().remove((Integer) f);	
				else
					lift.getDownReqFloorList().remove((Integer)f);
			}
			
		}
		checkArriveToTarget(index);
		
	}
}
