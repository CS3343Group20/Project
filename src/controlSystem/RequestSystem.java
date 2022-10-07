package controlSystem;

import java.util.ArrayList;
import java.util.List;

public class RequestSystem {
	private  List<Request> waitingList;//<Passenger,RequestTime>
	private CMS cms;
	public RequestSystem(CMS cms){
		waitingList = new ArrayList();
		this.cms=cms;
	}
	public void request(Request req) {
		waitingList.add(req);
		notifyCMS(req);
	}
	public void deleteFromList(Request req) {
		waitingList.remove(req);
	}
	public void notifyCMS(Request req) {
		// TODO Auto-generated method stub
		cms.receiveNewRequest(req);
	}
}
