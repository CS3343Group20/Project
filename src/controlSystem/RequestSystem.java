package controlSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import building.Building;


public class RequestSystem {
	private  List<Request> waitingList;//<Passenger,RequestTime>
	private CMS cms;
	public RequestSystem(CMS cms){
		this.cms=cms;
		waitingList = new ArrayList<Request>();
	}
	public void request(Request req) {
		Building building=cms.getBuilding();
		int reqFloor=req.getPassenger().getCurrentFloor();
		building.getFlrMap().get(reqFloor).AddtoQueue(req);
		waitingList.add(req);
		RequestComparator sortrule=new RequestComparator();		
		waitingList.sort(sortrule);
	}
	public void deleteFromList(Request req) {
		waitingList.remove(req);
	}
	public List<Request> getAllReq(){return waitingList;}

}
