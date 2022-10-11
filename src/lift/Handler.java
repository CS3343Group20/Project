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
	private Lift lift;
	private RequestSystem reqSys=CMS.getInstance().getReqSys();
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
	public void dropPassenger(Passenger p,Iterator<Passenger> itr) {
		int newWeight=lift.getLoadWeight()-p.getWeight();
		lift.setLoadWeight(newWeight);
		itr.remove();
		System.out.println("dropped Passenger!");
	}
	public boolean curFloorHaveRequest(int f) {
		if(reqSys.getEachFloorReq().get(f)==null) {
			return false;
		}	
		return true;
	}
	public void handleCurrentFloor(int f,int curTime) {
		if (curFloorHaveRequest(f)) {
			Iterator<Request> iterator=reqSys.getEachFloorReq().get(f).iterator();
			while(iterator.hasNext()){
				Request r =iterator.next();
				if(r.getRequestTime()<=curTime) {
					try {
						pickupPassenger(r.getPassenger());
						reqSys.getAllReq().remove(r);
						iterator.remove();
						System.out.println("Loaded people");
					} catch (OverWeightException e) {
						System.out.println("Ignore this people since overload");
						lift.setStatus(new Full());
						break;
					}
					catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}
		checkArriveToTarget();
	}
	public void checkArriveToTarget() {
		Iterator<Passenger> iterator=lift.getPassengerList().iterator();
		while(iterator.hasNext()){
			Passenger p=iterator.next();
			if(lift.getCurrentFloor()==p.getTargetFloor()) {
				dropPassenger(p,iterator);
			}
		}
	}
	public void directionHandle(int curTime) {
		// TODO Auto-generated method stub
		if(CMS.getInstance().curHaveRequest(curTime)==false&&lift.getPassengerList().size()==0) {
			if (lift.getCurrentFloor()>0 && lift.getDirection()==1) {
				lift.setDirection(0);
			}
		}
		if(lift.getCurrentFloor()==0&&lift.getPassengerList().size()==0) {
			lift.setDirection(1);
			lift.setStatus(new Idle());
		}
	}
}
