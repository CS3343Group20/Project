package building;

import java.util.ArrayList;

import controlSystem.Request;

public class Floor {
	ArrayList<Request> goUpList,goDownList;
	boolean upflag,downflag;
	int floorNum;
	public Floor(int f) {
		goUpList=new ArrayList<>();
		goDownList=new ArrayList<>();
		upflag=false;
		downflag=false;
		floorNum=f;
	}
	public boolean haveUpReq(int curTime) {
		for (Request r: goUpList) {
			if (r.getRequestTime()<=curTime)
				return true;
		}
		return false;
	}
	public boolean haveDownReq(int curTime) {
		for (Request r: goDownList) {
			if (r.getRequestTime()<=curTime)
				return true;
		}
		return false;
	}
	public void upReqAccepted() {
		upflag=true;
	}
	public void downReqAccepted() {
		downflag=true;
	}
	public boolean getUpflag() {return upflag;}
	public boolean getDownflag() {return downflag;}
	public void AddtoQueue(Request r) {
		if (r.getPassenger().getDirection()==1) {
			addToUpQueue(r);
		}
		else addToDownQueue(r);
	}
	private void addToUpQueue(Request r) {
		goUpList.add(r);
	}
	private void addToDownQueue(Request r) {
		goDownList.add(r);	
	}
	public ArrayList<Request> getUpQueue(){return goUpList;}
	public ArrayList<Request> getDownQueue(){return goDownList;}
	public boolean haveUpReq() {
		return !goUpList.isEmpty();
	}
	public boolean haveDownReq() {
		return !goDownList.isEmpty();
	}
	public void setUpflag(boolean b) {
		upflag=b;
	}
	public void setDownflag(boolean b) {
		downflag=b;
	}
}
