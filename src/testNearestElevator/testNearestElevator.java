package testNearestElevator;


import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import lift.*;
import controlSystem.*;
import exceptions.*;


public class testNearestElevator {
	//test Lift.java checkClosestFromPassenger()
	//lift up, req down, exist highest reqflr < request flr
	@Test
	public void testCheckClosestFP_1() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 2, 5);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(1, 6, 0);
		
		assertEquals(1, result);
		
	}
	
	//test Lift.java checkClosestFromPassenger()
	//lift up, req down, exist highest reqflr > request flr
	@Test
	public void testCheckClosestFP_2() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 2, 5);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(1, 4, 0);
		//missing situation?
		assertEquals(1, result);
		
	}
	
	//test Lift.java checkClosestFromPassenger()
	//lift down, req up, exist lowest reqflr < request flr
	@Test
	public void testCheckClosestFP_3() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 5, 2);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 3, 1);
		//missing situation?
		assertEquals(1, result);
		
	}

	//test Lift.java checkClosestFromPassenger()
	//lift down, req up, exist lowest reqflr > request flr
	@Test
	public void testCheckClosestFP_4() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 5, 2);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 1, 1);
		
		assertEquals(1, result);
		
	}
	
	//------------------------------
	//test Handler.java pickupPassenger() - normal
	@Test
	public void testPickupP_1() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(40, 2, 5);
		handler.pickupPassenger(p);
		
	}
	
	//test Handler.java pickupPassenger() - weight == capacity
	@Test
	public void testPickupP_2() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(60, 2, 5);
		handler.pickupPassenger(p);
		
	}
	
	//test Handler.java pickupPassenger() - OverWeightEx
	@Test
	public void testPickupP_3() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(70, 2, 5);
		
		Assertions.assertThrows(OverWeightException.class, () -> handler.pickupPassenger(p));
	}
	
	//------------------------------
	//test CMS.java assignClosest2()
	//
	@Test
	public void testAssignC2_1() {
		CMS cms = CMS.getInstance();
		cms.createLift(120);
		cms.createLift(120);
		
	}
	
}
