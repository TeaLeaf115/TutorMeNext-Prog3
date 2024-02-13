/*
Class description:  This class provides for a
type Result which consists of a number helped
(int) and a total wait time (int).
*/

public class Result {
	private int numHelped;
	private int totalWaitTime;

	public Result(int numHelped, int totalWaitTime) {
		this.numHelped = numHelped;
		this.totalWaitTime = totalWaitTime;
	}

	public int getNumHelped() {
		return numHelped;
	}

	public void setNumHelped(int numHelped) {
		this.numHelped = numHelped;
	}

	public int getTotalWaitTime() {
		return totalWaitTime;
	}

	public void setTotalWaitTime(int totalWaitTime) {
		this.totalWaitTime = totalWaitTime;
	}
}