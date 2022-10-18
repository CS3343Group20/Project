package testElevatorSimulator;


import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Iterator;

import lift.*;
import controlSystem.*;
import exceptions.*;
import lift.loadState.*;
import building.*;
import exceptions.timeException.*;
import main.*;
import simulator.*;
import time.*;


public class testNearestElevator {
	//Lift.java
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
	
	//test Lift.java move()
	//going up (test fail cannot get in the 2nd if)
	@Test
	public void testMove_1() {
		Lift lift = new Lift(120);
		lift.setStatus(new Loaded());
		Passenger p = new Passenger(50, 0, 1);
		lift.getPassengerList().add(p);
		
		lift.move();
		assertEquals(1, lift.getCurrentFloor());
		
	}
	
	//test Lift.java move()
	//going down (test fail cannot get in the 2nd if)
	@Test
	public void testMove_2() {
			
		Lift lift = new Lift(120);
		lift.setStatus(new Loaded());
		lift.setDirection(0);
		Passenger p = new Passenger(50, 1, 0);	
		lift.getPassengerList().add(p);
		
		lift.move();
		assertEquals(-1, lift.getCurrentFloor());
		
	}
	
	
	
	
	
	
	//Handle.java
	//------------------------------
	//test Handler.java pickupPassenger() - normal
	@Test
	public void testPickupP_1() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(40, 2, 5);
		handler.pickupPassenger(p);
		assertEquals(1, lift.getPassengerList().size());
	}
	
	//test Handler.java pickupPassenger() - weight == capacity
	@Test
	public void testPickupP_2() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(60, 2, 5);
		handler.pickupPassenger(p);
		assertEquals(1, lift.getPassengerList().size());
	}
	
	//test Handler.java pickupPassenger() - OverWeightEx
	@Test
	public void testPickupP_3() throws Exception {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(70, 2, 5);
		
		assertThrows(OverWeightException.class, () -> handler.pickupPassenger(p));
	}
	
	//---------
	//test Handler.java checkArriveToTarget() 
	//arrived
	@Test
	public void testCheckArriveT_1() {
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(50, 0, 1);
		
		lift.getPassengerList().add(p);
		lift.setStatus(new Loaded());
		lift.setLoadWeight(50);
		//move can't run
		lift.move();
		handler.checkArriveToTarget(0);
		assertEquals(0, lift.getPassengerList().size());
	}
	
	//test Handler.java checkArriveToTarget() 
	//not arrived
	@Test
	public void testCheckArriveT_2() {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(70, 1, 5);
		lift.setStatus(new Loaded());
		handler.checkArriveToTarget(0);
		assertEquals(1, lift.getPassengerList().size());
	}
	
	//------
	//test Handle.java curFloorHaveAccepedReq()
	//What is the use of curFloorHaveAccepedReq()
	@Test
	public void testCurHvAcpReq() {

	}
	
	//-----
	//test Handle.java curFloorHaveRequest2()
	//have up request
	@Test
	public void testCurFlrHvReq_1() {
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(50, 0, 2);
		Request r = new Request(p, 0);
		RequestSystem rs = new RequestSystem(cms);
		rs.request(r);
		assertEquals(true, handler.curFloorHaveRequest2(0));
	}
	
	//test Handle.java curFloorHaveRequest2()
	//have down request
	@Test
	public void testCurFlrHvReq_2() {
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(50, 1, 0);
		Request r = new Request(p, 0);
		RequestSystem rs = new RequestSystem(cms);
		rs.request(r);
		lift.move();

		assertEquals(true, handler.curFloorHaveRequest2(0));
	}
	
	//test Handle.java curFloorHaveRequest2()
	//no request
	@Test
	public void testCurFlrHvReq_3() {
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		assertEquals(false, handler.curFloorHaveRequest2(7));
	}
	
	
	//-----
	//test Handle.java handleCurrentFloor()
	@Test
	public void testHandleCF_1() {
		
		
	}
	
	
	
	
	
	//CMS.java
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
