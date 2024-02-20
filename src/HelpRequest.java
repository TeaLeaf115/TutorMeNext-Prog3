/*
Class description:  This class provides a type HelpRequest. A HelpRequest consists 
of a time (Time), name (String), demeanor (Demeanor), error (Error), minutes with 
help (integer) and minutes without help (integer).  A HelpRequest can be constructed 
by specifying nothing.

Class invariant:  Each property of HelpRequest is always defined.
*/

import java.util.Queue;

class HelpRequest {
	private Time time;
	private String name;
	private Demeanor demeanor;
	private Error error;
	private int minutesWithHelp;
	private int minutesWithoutHelp;
	
	public HelpRequest(Queue<String> inp) {
		for (int i = 0; i < 6; i++) {
			switch (i) {
				case 0:
					time = new Time (inp.remove());
					break;
				case 1:
					name = inp.remove();
					break;
				case 2:
					demeanor = new Demeanor(inp.remove());
					break;
				case 3:
					error = new Error(inp.remove());
					break;
				case 4:
					minutesWithHelp = Integer.parseInt(inp.remove());
					break;
				case 5:
					minutesWithoutHelp = Integer.parseInt(inp.remove());
					break;
			}
		}
	}

	public HelpRequest() {
		time = new Time(00, 00);
		name = "Test Student";
		demeanor = new Demeanor("runtime");
		error = new Error();
		minutesWithHelp = 20;
		minutesWithoutHelp = 24;
	}
	
	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Demeanor getDemeanor() {
		return demeanor;
	}
	
	public void setDemeanor(Demeanor demeanor) {
		this.demeanor = demeanor;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	public int getMinutesWithHelp() {
		return minutesWithHelp;
	}
	
	public void setMinutesWithHelp(int minutesWithHelp) {
		this.minutesWithHelp = minutesWithHelp;
	}
	
	public int getMinutesWithoutHelp() {
		return minutesWithoutHelp;
	}
	
	public void setMinutesWithoutHelp(int minutesWithoutHelp) {
		this.minutesWithoutHelp = minutesWithoutHelp;
	}
}