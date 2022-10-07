package lift;

import controlSystem.Passenger;
import exceptions.OverWeightException;

public class Handler {
	private Lift lift;
	public Handler(Lift lift) {
		this.lift=lift;
	};
	public int goUp() {
		return 1;
	};
	public int goDown() {
		return 0;
	}
	public void pickupPassenger(Passenger p) throws Exception{
		int newWeight=lift.getLoadWeight()+p.getWeight();
		if(newWeight>lift.getCapacity()) {
			throw new OverWeightException();
		}
		else {
			lift.setLoadWeight(newWeight);
			lift.getPassengerList().add(p);
			lift.getPickupList().remove(p);
			lift.getPassengerList().add(p);
		}
		
	}
	public void dropPassenger(Passenger p) {
		int newWeight=lift.getLoadWeight()-p.getWeight();
		lift.setLoadWeight(newWeight);
		lift.getPassengerList().remove(p);
	}
	public boolean curFloorHaveRequest() {
		for(Passenger p:lift.getPickupList()) {
			if(p.getCurrentFloor()==lift.getCurrentFloor()) {
				return true;
			}
		}
		return false;
	}
	public void handleCurrentFloor() {
		if (curFloorHaveRequest()) {
			for(Passenger p:lift.getPickupList()) {
				if(p.getCurrentFloor()==lift.getCurrentFloor()) {
					try {
						pickupPassenger(p);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}
		
	}
}
