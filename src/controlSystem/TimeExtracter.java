package controlSystem;

import exceptions.timeException.*;

public class TimeExtracter {
	public static int extractHour(String fragment) throws HourException{
		int hour=Integer.parseInt(fragment);
		if (hour>24||hour<0)
			throw new HourException("Hour incorrect");
		return hour;
	}
	public static int extractMinute(String fragment) throws MinuteException{
		int min=Integer.parseInt(fragment);
		if (min>60||min<0)
			throw new MinuteException("Minute incorrect");
		return min;
	}
	public static int extractSecond(String fragment) throws SecondException{
		int sec=Integer.parseInt(fragment);
		if (sec>60||sec<0)
			throw new SecondException("Second incorrect");
		return sec;
	}
}
