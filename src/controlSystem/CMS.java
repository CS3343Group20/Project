package controlSystem;

import java.util.ArrayList;
import java.util.List;

import lift.Lift;

public class CMS{
	private static CMS instance=new CMS();
	List<Lift> liftList;
	RequestSystem reqSystem;
	int time;
	private CMS() {
		reqSystem=RequestSystem.getInstance();
		liftList=new ArrayList();
	};
	public static CMS getInstance() {
		return instance;
	}
	public void createLift(int capacity) {
		liftList.add(new Lift(capacity));
	}
	public RequestSystem getReqSystem() {
		return reqSystem;
	}
}
