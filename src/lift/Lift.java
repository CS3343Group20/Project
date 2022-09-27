package lift;

public class Lift {
	private int capacity;
	private int dir;
	private int cur_floor;
	private Handler handler=Handler.getInstance();
	public Lift(int capacity) {
		dir=1;
		cur_floor=0;
	}
}
