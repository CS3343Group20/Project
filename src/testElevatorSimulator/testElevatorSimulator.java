package testElevatorSimulator;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
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


public class testElevatorSimulator {
	//Main.java
	//remark: can't find how to have multiple input
	@Test
	public void testMain() throws ParseException, InsufficientArgumentException {
		//System.setIn(new ByteArrayInputStream(("00:00:00 1 3 50"+"\n"+"-1").getBytes()));
		System.setIn(new ByteArrayInputStream(("-1").getBytes()));
		//ByteArrayOutputStream stream= new ByteArrayOutputStream();
		//PrintStream pStream = new PrintStream(stream);
		//System.setOut(pStream);
		Main.main(null);
		//assertEquals("lift created!\nlift created!\nEnter request time (hh:mm:ss)|current floor|target floor|weight:\n------------------Start simulation-----------------\n\nSimulation ends!", stream.toString());
	}
	
	
	

	//Lift.java
	//--------------------------------------------------
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
	public void testPickupP_3() throws OverWeightException {
		Lift lift = new Lift(60);
		Handler handler = new Handler(lift);
		Passenger p = new Passenger(70, 2, 5);
		
		OverWeightException ex = assertThrows(OverWeightException.class, ()->{
			handler.pickupPassenger(p);
		});
		
		assertEquals("Lift will overload, reject new passenger!", ex.getMessage());
	}
	
	//---------
	
	//test Handler.java checkArriveToTarget() 
		//not arrived
		@Test
		public void testCheckArriveT_1() {
			Lift lift = new Lift(60);
			Handler handler = new Handler(lift);
			Passenger p = new Passenger(70, 1, 5);
			lift.getPassengerList().add(p);
			lift.setStatus(new Loaded());
			handler.checkArriveToTarget(0);
			assertEquals(1, lift.getPassengerList().size());
		}
		
		
	//test Handler.java checkArriveToTarget() 
	//arrived
	@Test
	public void testCheckArriveT_2() {
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
		RequestSystem rs = cms.getReqSys();
		rs.request(r);
		assertEquals(true, handler.curFloorHaveRequest2(0));
		cms.getBuilding().getFlrMap().get(0).getUpQueue().clear();
		rs.getAllReq().clear();
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
		RequestSystem rs = cms.getReqSys();
		rs.request(r);
		assertEquals(true, handler.curFloorHaveRequest2(1));
		cms.getBuilding().getFlrMap().get(1).getDownQueue().clear();
		rs.getAllReq().clear();
	}
	//remarks: Floor.java addToUp/DownQueue are not necessary? I think we can delete them
	
	//test Handle.java curFloorHaveRequest2()
	//no request
	@Test
	public void testCurFlrHvReq_3() {
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		assertEquals(false, handler.curFloorHaveRequest2(0));
	}
	
	
	//-----
	//test Handle.java handleCurrentFloor()
	//have up request
	
	@Test
	public void testHandleCF_1() {
		class stubHandler extends Handler{

			public stubHandler(Lift lift) {
				super(lift);
			}
			
			public boolean curFloorHaveRequest2(int f) {
				return true;
			}
			
		}
		
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(120);
		stubHandler sh = new stubHandler(lift);
		lift.getUpReqFloorList().add(0);
		Passenger p = new Passenger(50, 0, 1);
		Request r = new Request(p, 0);
		RequestSystem rs = cms.getReqSys();
		rs.request(r);
		sh.handleCurrentFloor(0, 0);
		assertEquals(0, rs.getAllReq().size());
		assertEquals(1, lift.getPassengerList().size());
		
		rs.getAllReq().clear();
		cms.getBuilding().getFlrMap().get(1).getUpQueue().clear();
	}
	
	//test Handle.java handleCurrentFloor()
	//have down request
	
	@Test
	public void testHandleCF_2() {
		class stubHandler extends Handler{

			public stubHandler(Lift lift) {
				super(lift);
			}
			
			public boolean curFloorHaveRequest2(int f) {
				return true;
			}
			
		}
		
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(120);
		stubHandler sh = new stubHandler(lift);
		lift.getDownReqFloorList().add(3);
		Passenger p = new Passenger(50, 3, 1);
		Request r = new Request(p, 0);
		RequestSystem rs = cms.getReqSys();
		rs.request(r);
		assertEquals(1, cms.getBuilding().getFlrMap().get(3).getDownQueue().size());
		sh.handleCurrentFloor(3, 0);
		
		assertEquals(0, rs.getAllReq().size());
		assertEquals(1, lift.getPassengerList().size());
		
		rs.getAllReq().clear();
		cms.getBuilding().getFlrMap().get(3).getDownQueue().clear();
	}
	
	
	//test Handle.java handleCurrentFloor()
	//have up request, but lift is going down with passenger
	// not test now, next phrase
	@Test
	public void testHandleCF_3() {
		
	}
		
	//test Handle.java handleCurrentFloor()
	//have down request, but lift is going up with passenger
	// not test now, next phrase
	@Test
	public void testHandleCF_4() {
		
	}	
	
	//test Handle.java handleCurrentFloor()
	//have request but overWeightexception
	@Test
	public void testHandleCF_5() {
		class stubHandler extends Handler{

			public stubHandler(Lift lift) {
				super(lift);
			}
			
			public boolean curFloorHaveRequest2(int f) {
				return true;
			}
			
		}
		
		CMS cms = CMS.getInstance();
		Lift lift = new Lift(40);
		stubHandler sh = new stubHandler(lift);
		lift.getUpReqFloorList().add(0);
		Passenger p = new Passenger(50, 0, 1);
		Request r = new Request(p, 0);
		RequestSystem rs = cms.getReqSys();
		rs.request(r);
		sh.handleCurrentFloor(0, 0);
		
		assertDoesNotThrow(() -> {sh.handleCurrentFloor(0, 0);});

		assertEquals("full", lift.getStatus());
		assertEquals(1, rs.getAllReq().size());
		assertEquals(0, lift.getPassengerList().size());
		rs.getAllReq().clear();
		cms.getBuilding().getFlrMap().get(1).getUpQueue().clear();		

	}

	//test Handle.java handleCurrentFloor()
	//exception
	//remark: no idea how to trigger the exception
	public void testHandleCF_6() {
		
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
	
	//---
	//test CMS.java operate()
	//idle
	@Test
	public void testOperate_1() {
		String expect = "%nCurren time: 0:0:0 %n";
		expect += "-----------------------------------%n";
		expect+= "lift 0 in 0/F (0)%n";
		expect+= "lift 0 is idling......";
		
		//ByteArrayOutputStream stream= new ByteArrayOutputStream();
		//PrintStream pStream = new PrintStream(stream);
		//System.setOut(pStream);
		CMS cms=CMS.getInstance();
		cms.createLift(120);
		cms.operate(0);
		//assertEquals(expect,stream.toString());
		
	}
	
	//test CMS.java operate()
	//not idle
	@Test
	public void testOperate_2() {
		String expect = "%nCurren time: 0:0:0 %n";
		expect += "-----------------------------------%n";
		expect+= "lift 0 in 0/F (0)%n";
		//expect+= "lift 0 is idling......";
		
		//ByteArrayOutputStream stream= new ByteArrayOutputStream();
		//PrintStream pStream = new PrintStream(stream);
		//System.setOut(pStream);
		CMS cms=CMS.getInstance();
		cms.createLift(120);
		//test fail
		cms.getLiftList().get(0).setStatus(new Loaded());
		cms.operate(0);
		//assertEquals(expect,stream.toString());
		
	}
	
}
