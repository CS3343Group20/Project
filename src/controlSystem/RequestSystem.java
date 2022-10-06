package controlSystem;

import java.util.ArrayList;
import java.util.List;

public class RequestSystem {
	private static List<Passenger> waitingList;//<Passenger,RequestTime>
	private static RequestSystem instance=new RequestSystem();
	private RequestSystem(){
		waitingList = new ArrayList();
	}
	public static RequestSystem getInstance() {
		return instance;
	}
	public static void request(Passenger passenger,int requestTime) {
		waitingList.add(passenger);
	}
}
