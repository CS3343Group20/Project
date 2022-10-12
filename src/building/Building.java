package building;

import java.util.HashMap;

public class Building {
	private int floors;
	private HashMap<Integer,Floor> flrMap;
	public Building(int flrCount) {
		floors=flrCount;
		flrMap = new HashMap<>();
		for (int i=0;i<flrCount;i++) {
			flrMap.put(i, new Floor(i));
		}
	}
	public int getFloorCount() {return floors;}
	public HashMap<Integer,Floor> getFlrMap(){
		return flrMap;
	}
}
