package lift;

public class Handler {
	private Lift controlLift;
	public Handler(Lift lift) {
		this.controlLift=lift;
	};
	public int goUp() {
		return 1;
	};
	public int goDown() {
		return 0;
	}
	
}
