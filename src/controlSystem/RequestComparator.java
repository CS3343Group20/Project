package controlSystem;

import java.util.Comparator;

public class RequestComparator implements Comparator<Request>{
	@Override
	public int compare(Request r1, Request r2) {
		return r1.getRequestTime()-r2.getRequestTime();
	}
}
