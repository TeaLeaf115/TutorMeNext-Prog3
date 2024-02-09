import java.util.Queue;
import java.util.Stack;

class Teacher {
	private String name;
	private Experience expreience;
	private HelpRequest currRequest;
	private Stack<HelpRequest> requestStack;
	private Queue<HelpRequest> requestQueue;
	
	public Teacher(String name) {
		this.name = name;
	}
}