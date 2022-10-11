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
	public static String fromStoTime(int second) {
		String hour = String.valueOf(second/3600);
		String min=String.valueOf(second%3600/60);
		String sec=String.valueOf(second%3600%60);
		String res=hour+":"+min+":"+sec;
		return res;
	}
}
