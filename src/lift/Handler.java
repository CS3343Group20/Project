package lift;

public class Handler {
	private static Handler instance = new Handler();
	private Handler() {};
	public static Handler getInstance() {
		// TODO Auto-generated method stub
		return instance;
	};
	public int goUp() {
		return 1;
	};
	public int goDown() {
		return 0;
	}
	
}
