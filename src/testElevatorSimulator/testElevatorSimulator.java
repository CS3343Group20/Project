package testElevatorSimulator;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
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
	//pre-set for System.out
	PrintStream printStream;
	ByteArrayOutputStream bos;

	private void setOutput() throws Exception {
		printStream = System.out;
		bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
	}

	private String getOutput() { // throws Exception
		System.setOut(printStream);
		return bos.toString().trim();
	}
	
	private void clean() {
		CMS cms = CMS.getInstance();
		RequestSystem rs = cms.getReqSys();
		rs.getAllReq().clear();
		for(int i=0; i<6;i++) {
			cms.getBuilding().getFlrMap().get(i).getUpQueue().clear();
			cms.getBuilding().getFlrMap().get(i).getDownQueue().clear();
	
		}
		cms.getLiftList().clear();
	}
	
	//------------------Start Testing----------------------
	//-----------------------------------------------------
	
	//Main.java
	//remark: can't find how to have multiple input
	@Test
	public void testMain() throws Exception {
		setOutput();
		Main.main(new String[0]);	
		

	}
	
	
	
	//Building.java
	//-------------------------------------------
	//test Building.java getFloorCount()
	@Test
	public void testGetFloorCount() {
		Building b = new Building(6);
		assertEquals(6, b.getFloorCount());
	}

	
	
	
	//Floor.java
	//-------------------------------------------
	
	//test Floor.java upReqAccepted()
	@Test
	public void testupReqAccepted() {
		Floor f = new Floor(1);
		f.upReqAccepted();
		assertEquals(true, f.getUpflag());
	}
	
	//test Floor.java downReqAccepted()	
	@Test
	public void testdownReqAccepted() {
		Floor f = new Floor(1);
		f.downReqAccepted();
		assertEquals(true, f.getDownflag());
	}
	
	
	//test Floor.java haveUpReq()	
	//false
	@Test
	public void testhaveUpReq_1() {
		Floor f = new Floor(1);
		assertEquals(false, f.haveUpReq(0));
	}
	
	
	//test Floor.java haveUpReq()	
	//false
	@Test
	public void testhaveUpReq_2() {
		Floor f = new Floor(1);
		Passenger p = new Passenger(60,1,3);
		Request r = new Request(p,1);
		f.getUpQueue().add(r);
		assertEquals(false, f.haveUpReq(0));
	}
	
	//test Floor.java haveUpReq()	
	//true
	@Test
	public void testhaveUpReq_3() {
		Floor f = new Floor(1);
		Passenger p = new Passenger(60,1,3);
		Request r = new Request(p,0);
		f.getUpQueue().add(r);
		assertEquals(true, f.haveUpReq(0));
	}

	//test Floor.java haveDownReq()	
	//false
	@Test
	public void testhaveDownReq_1() {
		Floor f = new Floor(1);
		assertEquals(false, f.haveDownReq(0));
	}
	
	
	//test Floor.java haveUpReq()	
	//false
	@Test
	public void testhaveDownReq_2() {
		Floor f = new Floor(1);
		Passenger p = new Passenger(60,1,0);
		Request r = new Request(p,1);
		f.getDownQueue().add(r);
		assertEquals(false, f.haveDownReq(0));
	}
	
	//test Floor.java haveUpReq()	
	//true
	@Test
	public void testhaveDownReq_3() {
		Floor f = new Floor(1);
		Passenger p = new Passenger(60,1,0);
		Request r = new Request(p,0);
		f.getDownQueue().add(r);
		assertEquals(true, f.haveDownReq(0));
	}
	
	

	//Simulatro.java
	//--------------------------------------------------
	//test Simulatro.java StartSimulation()

	@Test
	public void testSS() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 2, 5);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(1, 6, 0);
		
		assertEquals(1, result);
		
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
	//lift down, req up, exist lowest reqflr > request flr
	@Test
	public void testCheckClosestFP_4() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 5, 2);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 1, 1);
		
		assertEquals(1, result);
		
	}
	
	//test Lift.java checkClosestFromPassenger()
	//lift down, req up, exist lowest reqflr > request flr
	@Test
	public void testCheckClosestFP_5() {
		Lift lift = new Lift(120);
		Passenger passenger = new Passenger(50, 1, 0);
		lift.getPassengerList().add(passenger);
		
		int result = lift.checkClosestFromPassenger(0, 2, 1);
		
		assertEquals(2, result);
		
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
	
	//test Lift.java haveReqAccepted()
	//going down (test fail cannot get in the 2nd if)
	@Test
	public void testHRA() {
			
		Lift lift = new Lift(120);
		lift.getUpReqFloorList().add(0);
		
		assertEquals(true, lift.haveReqAccepted());
		
	}
	
	//test Lift.java haveReqGoDown()
	@Test
	public void testHRGD() {
			
		Lift lift = new Lift(120);
		lift.getDownReqFloorList().add(0);
		
		assertEquals(true, lift.haveReqGoDown());
		
	}
	
	//test Lift.java haveReqGoDown()
	@Test
	public void testHRGD2() {
			
		Lift lift = new Lift(120);
		
		assertEquals(false, lift.haveReqGoDown());
		
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
		lift.move();
		handler.checkArriveToTarget(0);
		assertEquals(0, lift.getPassengerList().size());
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
		assertEquals(true, handler.curFloorHaveRequest(0));
		clean();
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
		assertEquals(true, handler.curFloorHaveRequest(1));
		clean();
	}
	//remarks: Floor.java addToUp/DownQueue are not necessary? I think we can delete them
	
	//test Handle.java curFloorHaveRequest2()
	//no request
	@Test
	public void testCurFlrHvReq_3() {
		Lift lift = new Lift(120);
		Handler handler = new Handler(lift);
		assertEquals(false, handler.curFloorHaveRequest(0));
	}
	
	
	//-----
		//test Handle.java handleCurrentFloor()
		//have up request
		
		@Test
		public void testHandleCF_1() {


			CMS cms = CMS.getInstance();
			Lift lift = new Lift(120);
			Handler h = new Handler(lift);
			lift.getUpReqFloorList().add(0);
			Passenger p = new Passenger(50, 0, 1);
			Request r = new Request(p, 0);
			RequestSystem rs = cms.getReqSys();
			h.handleCurrentFloor(0, 0);
			assertEquals(0, rs.getAllReq().size());
			
			clean();
		}
		
		//test Handle.java handleCurrentFloor()
		//have down request
		
		@Test
		public void testHandleCF_2() {
			
			CMS cms = CMS.getInstance();
			Lift lift = new Lift(120);
			Handler h = new Handler(lift);
			lift.getDownReqFloorList().add(3);
			Passenger p = new Passenger(50, 3, 1);
			Request r = new Request(p, 0);
			RequestSystem rs = cms.getReqSys();
			h.handleCurrentFloor(3, 0);
			
			assertEquals(0, rs.getAllReq().size());
			
			clean();
		}
		
		
		
		//test Handle.java handleCurrentFloor()
		//have request but overWeightexception
		@Test
		public void testHandleCF_3() {
			
			CMS cms = CMS.getInstance();
			Lift lift = new Lift(40);
			Handler h = new Handler(lift);
			lift.getUpReqFloorList().add(0);
			Passenger p = new Passenger(50, 0, 1);
			Request r = new Request(p, 0);
			RequestSystem rs = cms.getReqSys();
			h.handleCurrentFloor(0, 0);
			
			assertDoesNotThrow(() -> {h.handleCurrentFloor(0, 0);});

			assertEquals(0, rs.getAllReq().size());
			clean();

		}
		
		//test Handle.java handleCurrentFloor()
		//have request but overWeightexception
		@Test
		public void testHandleCF_4() {
			
			CMS cms = CMS.getInstance();
			Passenger p = new Passenger(50, 1, 0);
			Passenger p1 = new Passenger(50, 0, 1);
			Request r = new Request(p, 0);
			Floor f = cms.getBuilding().getFlrMap().get(1);
			
			cms.createLift(120);
			Lift lift = cms.getLiftList().get(0);
			Handler h = new Handler(lift);
			f.AddtoQueue(r);
			lift.getDownReqFloorList().add(1);
			lift.setStatus(new Loaded());
			lift.getPassengerList().add(p1);
			lift.move();
			lift.getPassengerList().clear();
			h.handleCurrentFloor(1, 0);

			assertEquals(1, lift.getDownReqFloorList().size());
					

		}


		//------
		//test Handler.java directionHandle()
		@Test
		public void testdirectionHandle_1() {
			Passenger p = new Passenger(50,0,3);
			
			Lift l = new Lift(120);
			l.setStatus(new Loaded());
			l.getPassengerList().add(p);
			l.move();
			l.getPassengerList().clear();
			l.getDownReqFloorList().add(0);
			Handler h = new Handler(l);
			h.directionHandle();
			assertEquals(0,l.getDirection());
			
		}
		
		//------
		//test Handler.java directionHandle()
		@Test
		public void testdirectionHandle_2() {
			Passenger p = new Passenger(50,0,3);
			
			Lift l = new Lift(120);
			l.setStatus(new Loaded());
			l.getPassengerList().add(p);
			l.move();
			l.getDownReqFloorList().add(0);
			Handler h = new Handler(l);
			h.directionHandle();
			assertEquals(1,l.getDirection());

		}
			
		//------
		//test Handler.java directionHandle()
		@Test
		public void testdirectionHandle_3() {
			Lift l = new Lift(120);
			l.setStatus(new Loaded());
			Handler h = new Handler(l);
			h.directionHandle();
			assertEquals(1,l.getDirection());

		}
	
	
		//------
		//test Handler.java directionHandle()
		@Test
		public void testdirectionHandle_4() {
			Passenger p = new Passenger(50,0,3);
			
			Lift l = new Lift(120);
			l.setStatus(new Loaded());
			l.getPassengerList().add(p);
			l.move();
			l.getPassengerList().clear();
			Handler h = new Handler(l);
			h.directionHandle();
			assertEquals(0,l.getDirection());

		}
	
	

	//Passenger.java
	//------------------------------
	//test Passenger.java makeRequest()
	//
	@Test
	public void testMR() {
		Passenger p = new Passenger(50,0,1);
		p.makeRequest(0);
		CMS cms = CMS.getInstance();
		assertEquals(1, cms.getReqSys().getAllReq().size());
		clean();
	}
	

	

	//RequestSystem.java
	//------------------------------
	//test RequestSystem.java deleteFromList()
	//
	@Test
	public void testDFL() {
		Passenger p = new Passenger(50,0,1);
		p.makeRequest(0);
		CMS cms = CMS.getInstance();
		RequestSystem rs = cms.getReqSys();
		rs.deleteFromList(rs.getAllReq().get(0));
		assertEquals(0, cms.getReqSys().getAllReq().size());
		clean();
	}
	
	
	//CMS.java
	//------------------------------
	//test CMS.java assignClosest2()
	//
	@Test
	public void testAssignC2_1() throws Exception {
		setOutput();
		CMS cms = CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		cms.createLift(120);
		
	}
	
	//---
	//test CMS.java operate()
	//idle
	@Test
	public void testOperate_1() throws Exception {
		setOutput();
		String expect = "Curren time: 0:0:0 ";
		expect += "-----------------------------------";
		expect+= "lift 0 in 0/F (0)";
		expect+= "lift 0 is idling......";
		
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		cms.operate(0);
		//assertEquals(expect,getOutput());
		
	}
	
	//test CMS.java operate()
	//not idle
	@Test
	public void testOperate_2() throws Exception {
		setOutput();
		String expect = "Curren time: 0:0:0 ";
		expect += "-----------------------------------";
		expect+= "lift 0 in 0/F (0)";
		expect+= "lift 0 is idling......";
		
		
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		cms.operate(0);
		//assertEquals(expect,getOutput());
		
	}
	
	//test CMS.java CurrentTime()
	@Test
	public void testCurrentTime() {
		CMS cms=CMS.getInstance();
		cms.setCurrentTime(3);
		assertEquals(3, cms.getCurrentTime());
	}
	
	//test CMS.java assignClosest()
	//0 time
	@Test
	public void testassignClosest_1() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.assignClosest(0, 1);	
	}
	
	//test CMS.java assignClosest()
	//1 time, all F
	@Test
	public void testassignClosest_2() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		cms.getLiftList().get(0).setStatus(new Full());
		cms.assignClosest(0, 1);	
	}
	
	//test CMS.java assignClosest()
	//1 time, not sameDir, reqDir=0, have reqf
	@Test
	public void testassignClosest_3() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.move();
		l.getDownReqFloorList().add(2);
		cms.assignClosest(2, 0);	
		assertEquals(0,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, not sameDir, reqDir=0
	@Test
	public void testassignClosest_4() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.move();
		
		cms.assignClosest(2, 0);	
		assertEquals(0,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, sameDir, DownPass
	@Test
	public void testassignClosest_5() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.move();
		l.setDirection(0);
		cms.assignClosest(2, 0);	
		assertEquals(1,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, sameDir, Down not Pass
	@Test
	public void testassignClosest_6() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.move();
		l.setDirection(0);
		cms.assignClosest(1, 0);	
		assertEquals(0,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, sameDir, up Pass
	@Test
	public void testassignClosest_7() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.move();
		cms.assignClosest(0, 1);	
		assertEquals(1,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, sameDir, up not Pass
	@Test
	public void testassignClosest_8() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());
		l.getUpReqFloorList().add(1);
		cms.assignClosest(1, 1);	
		assertEquals(1,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, sameDir, up not Pass
	@Test
	public void testassignClosest_9() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Passenger p = new Passenger(50,0,3);
		
		Lift l = cms.getLiftList().get(0);
		l.getPassengerList().add(p);
		l.setStatus(new Loaded());

		cms.assignClosest(1, 1);	
		assertEquals(1,l.getReqDir());
	}
	
	//test CMS.java assignClosest()
	//1 time, idle
	@Test
	public void testassignClosest_10() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		cms.createLift(120);
		
		Lift l = cms.getLiftList().get(0);

		cms.assignClosest(1, 1);	
		assertEquals(1,l.getReqDir());
	}
	
	//test CMS.java curHaveRequestt()
	//true
	@Test
	public void testcurHaveRequest_1() {
		CMS cms=CMS.getInstance();
		cms.getLiftList().clear();
		
		Passenger p = new Passenger(60,0,3);
		Request r = new Request(p,0);
		cms.getReqSys().request(r);
		
		assertEquals(true,cms.curHaveRequest());
		cms.getReqSys().getAllReq().clear();
	}
	
	//test CMS.java curHaveRequestt()
	//false
	@Test
	public void testcurHaveRequest_2() {
		CMS cms=CMS.getInstance();
		
		
		assertEquals(false,cms.curHaveRequest());
	}
	
	//test CMS.java anyLiftRunning()
	//false
	@Test
	public void testALR_1() {
		CMS cms=CMS.getInstance();
		
		assertEquals(false,cms.anyLiftRunning());
	}
	
	//test CMS.java anyLiftRunning()
	//true
	@Test
	public void testALR_2() {
		CMS cms=CMS.getInstance();
		cms.createLift(120);
		cms.getLiftList().get(0).setStatus(new Loaded());
		assertEquals(true,cms.anyLiftRunning());
		clean();
	}
		
	
	
	//test CMS.java assignLift()
	//true
	@Test
	public void testASL_1() {
		CMS cms=CMS.getInstance();
		Building b = new Building(6);
		Passenger p = new Passenger(60,4,0);
		Request r = new Request(p,0);
		b.getFlrMap().get(4).AddtoQueue(r);
		cms.setCurrentTime(0);
		cms.assignLift(b);
	}
		
		
		
	

	//TimeConverter.java
	//------------------------------
	//test TimeConverter.java ConvertTime()
	//too much parameters
	@Test
	public void testConTime_1() throws TimeFormatException {
		TimeConverter t = new TimeConverter();
		String time = "00:00:00:00";
		TimeFormatException ex = assertThrows(TimeFormatException.class, ()->{
			t.ConvertTime(time);
		});
		assertEquals("Too much parameters!",ex.getMessage());
	}
	
	//test TimeConverter.java ConvertTime()
	//too less parameters
	@Test
	public void testConTime_2() throws TimeFormatException {
		TimeConverter t = new TimeConverter();
		String time = "00:00";
		TimeFormatException ex = assertThrows(TimeFormatException.class, ()->{
			t.ConvertTime(time);
		});
		assertEquals("Too less parameters!",ex.getMessage());
	}
	
	//test TimeConverter.java ConvertTime()
	//normal
	@Test
	public void testConTime_3() throws TimeFormatException {
		
		TimeConverter t = new TimeConverter();
		String time = "00:00:00";
		int result = t.ConvertTime(time);
		assertEquals(0,result);
	}
	
	
	
	
	//TimeExtracter.java
	//------------------------------
	//test TimeExtracter.java extractHour()
	//normal
	@Test
	public void testExHour_1() throws HourException {
		TimeExtracter t = new TimeExtracter();
		String time = "24";
		int result = t.extractHour(time);
		assertEquals(24,result);
	}
	
	//test TimeExtracter.java extractHour()
	//> 24
	@Test
	public void testExHour_2() throws HourException {
		TimeExtracter t = new TimeExtracter();
		String time = "25";
		HourException ex = assertThrows(HourException.class, ()->{
			t.extractHour(time);
		});
		assertEquals("Hour incorrect",ex.getMessage());
	}
	
	//test TimeExtracter.java extractHour()
	//<0
	@Test
	public void testExHour_3() throws HourException {
		TimeExtracter t = new TimeExtracter();
		String time = "-1";
		HourException ex = assertThrows(HourException.class, ()->{
			t.extractHour(time);
		});
		assertEquals("Hour incorrect",ex.getMessage());
	}
	
	
	//------
	//test TimeExtracter.java extractMinute()
	//normal
	@Test
	public void testExMinute_1() throws MinuteException {
		TimeExtracter t = new TimeExtracter();
		String time = "60";
		int result = t.extractMinute(time);
		assertEquals(60,result);
	}
	
	//test TimeExtracter.java extractMinute()
	//> 60
	@Test
	public void testExMinute_2() throws MinuteException {
		TimeExtracter t = new TimeExtracter();
		String time = "61";
		MinuteException ex = assertThrows(MinuteException.class, ()->{
				t.extractMinute(time);
		});
		assertEquals("Minute incorrect",ex.getMessage());
	}
	
	//test TimeExtracter.java extractMinute()
	//<0
	@Test
	public void testExMinute_3() throws MinuteException {
		TimeExtracter t = new TimeExtracter();
		String time = "-1";
		MinuteException ex = assertThrows(MinuteException.class, ()->{
			t.extractMinute(time);
		});
		assertEquals("Minute incorrect",ex.getMessage());
	}
	
	//------
	//test TimeExtracter.java extractSecond()
	//normal
	@Test
	public void testExSecond_1() throws SecondException {
		TimeExtracter t = new TimeExtracter();
		String time = "60";
		int result = t.extractSecond(time);
		assertEquals(60,result);
	}
	
	//test TimeExtracter.java extractSecond()
	//> 60
	@Test
	public void testExSecond_2() throws SecondException {
		TimeExtracter t = new TimeExtracter();
		String time = "61";
		SecondException ex = assertThrows(SecondException.class, ()->{
			t.extractSecond(time);
		});
		assertEquals("Second incorrect",ex.getMessage());
	}
	
	//test TimeExtracter.java extractSecond()
	//<0
	@Test
	public void testExSecond_3() throws SecondException {
		TimeExtracter t = new TimeExtracter();
		String time = "-1";
		SecondException ex = assertThrows(SecondException.class, ()->{
			t.extractSecond(time);
		});
		assertEquals("Second incorrect",ex.getMessage());
	}
}
