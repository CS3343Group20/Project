import java.text.ParseException;
import java.util.Scanner;

import controlSystem.CMS;
import controlSystem.Passenger;
import controlSystem.TimeConverter;
import exceptions.timeException.TimeFormatException;
import simulator.Simulator;
public class Main {
	
	public static void main(String[] args) throws ParseException {	
		CMS cms= CMS.getInstance();
		cms.createLift(120);
		String requestTime="";
        Simulator sim=new Simulator();
    	int parseInTime;
        while (true) {//begin simulation
        	Scanner input = new Scanner(System.in);
        	System.out.println("Enter request time (hh:mm:ss): ");
        	requestTime = input.nextLine();
        	if (requestTime.equals("-1")) {
        		break;
        	}
        	try {
        		parseInTime=TimeConverter.ConvertTime(requestTime);
			} catch (TimeFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Input error ,please try again");
				continue;
			}
        	
        	System.out.println("Enter current floor: ");
            int currentFloor = input.nextInt();
            System.out.println("Enter target floor: ");
            int targetFloor = input.nextInt();
            System.out.println("Enter Person Weight: ");
            int weight = input.nextInt();
            Passenger p=new Passenger(weight,currentFloor,targetFloor);
			p.makeRequest(parseInTime);
			
        }
        cms.getReqSys().printQueue();
        sim.StartSimulation();
        
       
    }
}
