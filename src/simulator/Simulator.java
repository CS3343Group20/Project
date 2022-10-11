package simulator;

import java.util.List;
import java.util.Map;

import controlSystem.CMS;
import controlSystem.Request;

public class Simulator {//userless now just ignore it
	private int currentTime;
	private CMS cms=CMS.getInstance();
	public Simulator() {
		currentTime=0;
	}
	public void StartSimulation() {
		while (currentTime!=86400) {
			cms.setCurrentTime(currentTime);
			if(!cms.curHaveRequest(currentTime)) {
				if(cms.getRunningLift()!=0) {
					cms.operate(currentTime);
				}
			}
			else {
				for(Map.Entry<Integer, List<Request>> map: cms.getReqSys().getEachFloorReq().entrySet()) {
					//check all request to design if we need to assign new, this part incomplete now
					System.out.println("Floor "+map.getKey());
					for (Request r: map.getValue()) {
						if (r.getRequestTime()<=currentTime) {
							cms.assignClosest(r);
						}
					}
				}
				cms.operate(currentTime);
				System.out.println("Current time is: "+currentTime);
			}
			
			currentTime++;
		}
		System.out.println("Simulation ends!");
	}
}
