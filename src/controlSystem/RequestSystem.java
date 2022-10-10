package controlSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RequestSystem {
	private  List<Request> waitingList;//<Passenger,RequestTime>
	private CMS cms;
	private HashMap<Integer,List<Request>> waitingList2;
	public RequestSystem(CMS cms){
		waitingList = new ArrayList();
		this.cms=cms;
		waitingList2 = new HashMap();
	}
	public void request(Request req) {
		//waitingList.add(req);
		int reqFloor=req.getPassenger().getCurrentFloor();
		if (waitingList2.get(reqFloor)==null) {
			List<Request> waitingQueue=new ArrayList();
			waitingQueue.add(req);
			waitingList2.put(reqFloor,waitingQueue);
		}
		else {
			waitingList2.get(reqFloor).add(req);
		}
	}
	public void deleteFromList(Request req) {
		int reqFloor=req.getPassenger().getCurrentFloor();
		waitingList2.get(reqFloor).remove(req);
		//waitingList.remove(req);
	}
	public void notifyCMS(Request req) {
		cms.receiveNewRequest(req);
	}
	
	
	public void printQueue() {
		waitingList2.forEach((k,v)->{
			System.out.printf("Floor %s (request count): %s%n Request time: ",k,v.size());
			v.forEach((r)->{
				System.out.printf("%s ", r.getRequestTime());
			});
			System.out.println();
		});
	}
}
