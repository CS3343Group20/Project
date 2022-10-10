package controlSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lift.Lift;
import lift.loadState.*;

public class CMS implements Observer{
	private static CMS instance=new CMS();
	List<Lift> liftList;
	RequestSystem reqSystem;
	int time;
	private CMS() {
		reqSystem=new RequestSystem(this);
		liftList=new ArrayList();
	};
	public static CMS getInstance() {
		return instance;
	}
	public void createLift(int capacity) {
		liftList.add(new Lift(capacity));
		System.out.println("Lift created!");
	}
	public RequestSystem getReqSystem() {
		return reqSystem;
	}
	public RequestSystem getRequestSystem() {return reqSystem;}
	@Override
	public void receiveNewRequest(Request req) {//imidiate check
		int shortestDistance=Integer.MAX_VALUE;
		int i=0;
		Lift assignLift=null;
		for (Lift lift:liftList) {//the above code should be extract into seperate methods for code maintainence
			if (lift.getStatus()=="idle") {
				assignLift=lift;
			}
			else if(lift.getDirection()!=req.getPassenger().getDirection()) {//the lift is going in opposite direction with the passenger 
				//ignore
			}
			else {
				if(lift.getDirection()==1) {//the lift is going up
					if(lift.getCurrentFloor()>req.getPassenger().getCurrentFloor()) {//already get pass the passenger
						//ignore
					}
					else {//able to pick up
						int distance= calculateDistance(lift,req);
						if(distance<shortestDistance) {
							assignLift=lift;
							shortestDistance=distance;
						}
					}
				}
				else {//the lift is going down
					if(lift.getCurrentFloor()<req.getPassenger().getCurrentFloor()) {//already get pass the passenger
						//ignore
					}
					else {//able to pick up
						int distance= calculateDistance(lift,req);
						if(distance<shortestDistance) {
							assignLift=lift;
							shortestDistance=distance;
						}
					}
				}
			}
			i++;
		}//the for loop end
		if (assignLift!=null) {
			if (assignLift.getStatus()=="idle") {
				assignLift.setStatus(new Loaded());
			}
			assignLift.getPickupList().add(req.getPassenger());
		}
	}
	public int calculateDistance(Lift lift,Request req) {
		return Math.abs(req.getPassenger().getCurrentFloor()-lift.getCurrentFloor());
	}
	
	
}
