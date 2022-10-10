package controlSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class RequestSystem {
	private  List<Request> waitingList;//<Passenger,RequestTime>
	private CMS cms;
	private HashMap<Integer,List<Request>> eachFloorReq;
	public RequestSystem(CMS cms){
		waitingList = new ArrayList();
		this.cms=cms;
		eachFloorReq = new HashMap();
	}
	public void request(Request req) {
		waitingList.add(req);
		RequestComparator sortrule=new RequestComparator();
		int reqFloor=req.getPassenger().getCurrentFloor();
		waitingList.sort(sortrule);
		if (eachFloorReq.get(reqFloor)==null) {
			List<Request> waitingQueue=new ArrayList();
			waitingQueue.add(req);
			eachFloorReq.put(reqFloor,waitingQueue);
		}
		else {
			eachFloorReq.get(reqFloor).add(req);
			eachFloorReq.get(reqFloor).sort(sortrule);
		}
	}
	public void deleteFromList(Request req) {
		int reqFloor=req.getPassenger().getCurrentFloor();
		eachFloorReq.get(reqFloor).remove(req);
		waitingList.remove(req);
	}
	
	public HashMap<Integer,List<Request>> getAllReq(){return eachFloorReq;}
	public void printQueue() {
		eachFloorReq.forEach((k,v)->{
			System.out.printf("Floor %s (request count): %s%n Request time: ",k,v.size());
			v.forEach((r)->{
				System.out.printf("%s ", r.getRequestTime());
			});
			System.out.println();
		});
	}
}
