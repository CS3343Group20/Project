import java.text.ParseException;
import java.util.Scanner;

import controlSystem.CMS;
import controlSystem.Passenger;
import controlSystem.TimeConverter;
import simulator.Simulator;
public class Main {
	
	public static void main(String[] args) throws ParseException {	
		CMS cms= CMS.getInstance();
		cms.createLift(120);
		String requestTime="";
        Simulator sim=new Simulator();
        
        while (true) {//begin simulation
        	 Scanner input = new Scanner(System.in);
        	System.out.println("Enter request time (hh:mm:ss): ");
        	requestTime = input.nextLine();
        	if (requestTime.equals("-1")) {
        		break;
        	}
        	System.out.println("Enter current floor: ");
            int currentFloor = input.nextInt();
            System.out.println("Enter target floor: ");
            int targetFloor = input.nextInt();
            System.out.println("Enter Person Weight: ");
            int weight = input.nextInt();
            Passenger p=new Passenger(weight,currentFloor,targetFloor);
            p.makeRequest(TimeConverter.ConvertTime(requestTime));
        }
        cms.getReqSys().printQueue();
        sim.StartSimulation();
        
       
    }
}
