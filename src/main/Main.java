package main;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Scanner;

import controlSystem.CMS;
import controlSystem.Passenger;
import controlSystem.Request;
import controlSystem.RequestComparator;
import exceptions.timeException.TimeFormatException;
import simulator.Simulator;
import time.TimeConverter;

//Exceptions
import exceptions.InsufficientArgumentException;
public class Main {
	
	public static void main(String[] args) throws ParseException, InsufficientArgumentException {	
		CMS cms= CMS.getInstance(); // create a cms instance
		cms.createLift(1000); // create a lift with 1000 KG capacity
		cms.createLift(1000); // create a lift with 1000 KG capacity
		String requestTime="";
        Simulator sim=new Simulator();
        ArrayList<Request> inputList=new ArrayList<>();
    	int parseInTime;
        while (true) {//begin simulation
        	String[] inputcmd;
        	Scanner input = new Scanner(System.in);
        	System.out.println("Enter request time (hh:mm:ss)|current floor|target floor|weight: ");
        	inputcmd = input.nextLine().split(" ");
        	requestTime = inputcmd[0];
        	if (requestTime.equals("-1")) { //input -1 to terminate input
        		break;
        	}
        	if (inputcmd.length < 4) {        		
        		System.out.println("Insufficient command arguments. Please try again.");
        		continue;
        	}
        	try {
        		parseInTime=TimeConverter.ConvertTime(requestTime);
			} catch (TimeFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Input error ,please try again.");
				continue;
			}
        	
        	
            int currentFloor = Integer.parseInt(inputcmd[1]);

            int targetFloor = Integer.parseInt(inputcmd[2]);

            int weight = Integer.parseInt(inputcmd[3]);
            Passenger p=new Passenger(weight,currentFloor,targetFloor);
            inputList.add(new Request(p,parseInTime));
			//p.makeRequest(parseInTime);
        }
        inputList.sort(new RequestComparator());
        //cms.getReqSys().printQueue();
        sim.StartSimulation(cms.getBuilding(),inputList);
    }
}
