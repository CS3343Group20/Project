package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import controlSystem.Request;
import exceptions.timeException.TimeFormatException;
import time.TimeConverter;
import utils.RequestComparator;

public class FileReader {
	
	ArrayList<Request> inputList;
	public FileReader() {
		inputList=new ArrayList<>();
	}
	
	public void readFile(String filename) {
		String requestTime="";
		int parseInTime;
		File file=new File(filename);
		try {
			Scanner sc= new Scanner(file);
			while (sc.hasNextLine()) {
				String[] inputcmd;
				inputcmd = sc.nextLine().split(" ");
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
					e.printStackTrace();
					System.out.println("Input error ,please try again.");
					continue;
				}
	        	
	        	
	            int currentFloor = Integer.parseInt(inputcmd[1]);

	            int targetFloor = Integer.parseInt(inputcmd[2]);

	            int weight = Integer.parseInt(inputcmd[3]);
	            Passenger p=new Passenger(weight,currentFloor,targetFloor);
	            inputList.add(new Request(p,parseInTime));
			}
			inputList.sort(new RequestComparator());
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	public ArrayList<Request> getInputList(){return inputList;}
}
