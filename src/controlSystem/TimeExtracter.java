package controlSystem;

public class TimeExtracter {
	public static int extractHour(String fragment) {
		int hour=Integer.parseInt(fragment);
		return hour;
	}
	public static int extractMinute(String fragment) {
		int min=Integer.parseInt(fragment);
		return min;
	}
	public static int extractSecond(String fragment) {
		int sec=Integer.parseInt(fragment);
		return sec;
	}
}
