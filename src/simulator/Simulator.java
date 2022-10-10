package simulator;

import controlSystem.CMS;

public class Simulator {//userless now just ignore it
	private int currentTime;
	private CMS cms=CMS.getInstance();
	public Simulator() {
		currentTime=0;
	}
	public void StartSimulation() {
		while (currentTime!=86400) {
			if(!cms.curHaveRequest()) {
				continue;
			}
			else {
				for(Integer key: cms.getReqSys().getAllReq().keySet()) {
					
				}
			}
		}
		System.out.println("Simulation ends!");
	}
}
