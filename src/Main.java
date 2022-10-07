import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import controlSystem.CMS;
import controlSystem.Passenger;
import controlSystem.TimeConverter;
public class Main {
	public static void main(String[] args) throws ParseException {	
		CMS cms= CMS.getInstance();
		cms.createLift(120);
		String requestTime="";
        Scanner input = new Scanner(System.in);
        
        while (requestTime!="-1") {//begin simulation
        	System.out.println("Enter request time (hh:mm:ss): ");
        	requestTime = input.nextLine();
        	System.out.println("Enter current floor: ");
            int currentFloor = input.nextInt();
            System.out.println("Enter target floor: ");
            int targetFloor = input.nextInt();
            System.out.println("Enter Person Weight: ");
            int weight = input.nextInt();
            Passenger p=new Passenger(weight,currentFloor,targetFloor);
            p.makeRequest(TimeConverter.ConvertTime(requestTime));
        }
        
        
       
    }
}
