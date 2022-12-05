package controlSystem;
import main.Passenger;

public class Request{
	private Passenger p;
	private int requestTime;
	public Request(Passenger p,int time) {
		this.p=p;
		this.requestTime=time;
	}
	public Passenger getPassenger() {return p;}
	public int getRequestTime() {return requestTime;}
	
	
	
}
