package testNearestElevator;


import static org.junit.Assert.*;
import org.junit.Test;

import lift.*;
import controlSystem.*;

public class testNearestElevator {
	//test Lift.java checkClosestFromPassenger
	//lift up, req down, exist highest reqflr < request flr
	@Test
	public void testCheckClosestFP1() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 2, 5);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(1, 6, 0);
		
		assertEquals(1, result);
		
	}
	
	//test Lift.java checkClosestFromPassenger
	//lift up, req down, exist highest reqflr > request flr
	@Test
	public void testCheckClosestFP2() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 2, 5);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(1, 4, 0);
		//missing situation?
		assertEquals(1, result);
		
	}
	
	//test Lift.java checkClosestFromPassenger
	//lift down, req up, exist lowest reqflr < request flr
	@Test
	public void testCheckClosestFP3() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 5, 2);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 3, 1);
		//missing situation?
		assertEquals(1, result);
		
	}

	//test Lift.java checkClosestFromPassenger
	//lift down, req up, exist lowest reqflr > request flr
	@Test
	public void testCheckClosestFP4() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 5, 2);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 1, 1);
		
		assertEquals(1, result);
		
	}
}
