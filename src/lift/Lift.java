package lift;

public class Lift {
	private int capacity;
	private int direction;
	private int floor;
	private Handler handler;
	public Lift(int capacity) {
		direction=1;
		floor=0;
		handler=new Handler(this);
	}
}
