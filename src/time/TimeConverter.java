package time;

import exceptions.timeException.HourException;
import exceptions.timeException.MinuteException;
import exceptions.timeException.SecondException;
import exceptions.timeException.TimeFormatException;

public class TimeConverter {
	public static int ConvertTime(String time) throws TimeFormatException{
		String timeFragments[]=time.split(":");
		int h,m,s;
		if(timeFragments.length>3) {
			throw new TimeFormatException("Too much parameters!");
		}
		if(timeFragments.length<3) {
			throw new TimeFormatException("Too less parameters!");
		}
		try {
			h=TimeExtracter.extractHour(timeFragments[0]);
			m=TimeExtracter.extractMinute(timeFragments[1]);
			s=TimeExtracter.extractSecond(timeFragments[2]);
			unitChecking(h,m,s);
			int timeInSecond=h*60*60+m*60+s;
			return timeInSecond;
		}
		catch (TimeFormatException ex){
			throw ex;
		}
		
	}
	public static String fromStoTime(int second) {
		String hour = String.valueOf(second/3600);
		String min=String.valueOf(second%3600/60);
		String sec=String.valueOf(second%3600%60);
		String res=hour+":"+min+":"+sec;
		return res;
	}
	private static void unitChecking(int h,int m,int s) throws HourException,MinuteException,SecondException {
		if (h>23||h<0) {
			throw new HourException("hour range incorrect");
		}
		if (m>59||m<0) {
			throw new MinuteException("Minute range incorrect");
		}
		if (s>59||s<0) {
			throw new SecondException("Second range incorrect");
		}
	}
}
