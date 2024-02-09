import java.time.LocalTime;

class Time implements Comparable {
	// class methods
	
	public static boolean LegalHour(int hour) {
		return hour >= 0 && hour < 24;
	}
	
	public static boolean LegalMinute(int minute) {
		return minute >= 0 && minute < 60;
	}
	
	public static boolean LegalTime(int hour, int minute) {
		return LegalHour(hour) && LegalMinute(minute);
	}
	
	public static boolean LegalTimeString(String timeString) {
		if (timeString == null)
			return false;
		
		int colon = timeString.indexOf(":");
		if (colon < 1 || colon >= timeString.length() - 1)
			return false;
		
		String hourString = timeString.substring(0, colon);
		String minuteString = timeString.substring(colon + 1);
		try {
			int hour = Integer.parseInt(hourString);
			int minute = Integer.parseInt(minuteString);
			return LegalTime(hour, minute);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	// instance properties
	private int myHour = 0;
	private int myMinute = 0;
	
	// constructors
	
	public Time() {}
	
	public Time(int hour, int minute) {
		if (!LegalTime(hour, minute))
			throw new IllegalStateException("! LegalTime ()");
		myHour = hour;
		myMinute = minute;
	}
	
	public Time(Time aTime) {
		myHour = aTime.myHour;
		myMinute = aTime.myMinute;
	}
	
	public Time(String timeString) {
		LocalTime localTime = LocalTime.parse(timeString);
		this.myHour = localTime.getHour();
		this.myMinute = localTime.getMinute();
	}
	
	// accessors
	
	public int getHour() {
		return myHour;
	}
	
	public int getMinute() {
		return myMinute;
	}
	
	// mutators
	
	public void setHour(int hour) {
		if (!LegalHour(hour))
			throw new IllegalStateException("! LegalHour ()");
		myHour = hour;
	}
	
	public void setMinute(int minute) {
		if (!LegalMinute(minute))
			throw new IllegalStateException("! LegalMinute ()");
		myMinute = minute;
	}
	
	public void setFromString(String timeString) {
		if (!LegalTimeString(timeString))
			throw new IllegalStateException("Time, .setFromString (), illegal Time format.");
		
		int colon = timeString.indexOf(":");
		String hourString = timeString.substring(0, colon);
		String minuteString = timeString.substring(colon + 1);
		int hour = Integer.parseInt(hourString);
		int minute = Integer.parseInt(minuteString);
		setHour(hour);
		setMinute(minute);
	}
	
	// utility functions
	
	public int inMinutes() {
		return myHour * 60 + myMinute;
	}
	
	public void nextMinute() {
		myMinute++;
		if (myMinute >= 60) {
			myMinute = 0;
			myHour = (myHour + 1) % 24;
		}
	}
	
	// overridden Object methods
	
	public String toString() {
		return myHour + ":" + (myMinute < 10 ? "0" : "") + myMinute;
	}
	
	public boolean equals(Object anObject) {
		if (!(anObject instanceof Time aTime))
			throw new IllegalStateException("Time .equals () demands Time object.");
		return inMinutes() == aTime.inMinutes();
	}
	
	// implementation of Comparable method
	
	public int compareTo(Object anObject) {
		if (!(anObject instanceof Time aTime))
			throw new IllegalStateException("Time .compareTo () demands Time object.");
		return inMinutes() - aTime.inMinutes();
	}
}