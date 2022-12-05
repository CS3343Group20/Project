package simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import building.Building;
import building.Floor;
import controlSystem.CMS;
import controlSystem.Request;

public class Simulator {
	private int currentTime;
	private CMS cms=CMS.getInstance();
	public Simulator() {
		currentTime=0;
	}
	public void StartSimulation(Building building, ArrayList<Request> inputList) {
		System.out.printf("------------------Start simulation!-----------------%n%n");
		while (currentTime!=86400) {
			Iterator<Request> itr=inputList.iterator();
			putRequest(itr);
			cms.setCurrentTime(currentTime);
			if(!cms.curHaveRequest()) {
				if(cms.anyLiftRunning()) {
					cms.operate(currentTime);
				}
			}
			else {
				cms.assignLift(building);
				cms.operate(currentTime);
			}
			currentTime++;
		}
		System.out.println("------------------Simulation ends!------------------");
	}
	
	public void putRequest(Iterator<Request> itr) {//put user input to request system
		while (itr.hasNext()) {
			Request r=itr.next();
			if (r.getRequestTime()<=currentTime) {//put request inside the req System when time meet in input
				r.getPassenger().makeRequest(currentTime);
				itr.remove();
			}
			else break;
		}
	}


}
