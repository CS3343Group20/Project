package controlSystem;

public class TimeConverter {
	public static int ConvertTime(String time) {
		String timeFragments[]=time.split(":");
		int h,m,s;
		h=TimeExtracter.extractHour(timeFragments[0]);
		m=TimeExtracter.extractMinute(timeFragments[1]);
		s=TimeExtracter.extractSecond(timeFragments[2]);
		int timeInSecond=h*60*60+m*60+s;
		return timeInSecond;
	}
}
