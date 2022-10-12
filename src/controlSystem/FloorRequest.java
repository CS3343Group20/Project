package controlSystem;

public class FloorRequest {
	int direction;
	boolean reserved;
	boolean enable;
	public FloorRequest(int dir) {
		direction=dir;
		reserved=false;
	}
	public void reserveByLift() {
		reserved=true;
	}
}
