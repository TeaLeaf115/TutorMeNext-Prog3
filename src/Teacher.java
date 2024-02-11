class Teacher {
	private String name;
	private Experience experience;
	private HelpRequest currRequest;
	private ArrayStack<HelpRequest> requestStack;
	private ListQueue<HelpRequest> requestQueue;

	public Teacher(String name, Experience experience) {
		this.name = name;
		this.experience = experience;
		this.requestStack = new ArrayStack<>();
		this.requestQueue = new ListQueue<>();
		this.currRequest = new HelpRequest<>();
	}

	public void addHelpRequest(HelpRequest hr) {
		requestStack.push(hr);
		requestQueue.add(hr);
		currRequest = hr;
	}

	// Getters and Setters for Teacher class
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public HelpRequest getCurrRequest() {
		return currRequest;
	}

	public ArrayStack<HelpRequest> getRequestStack() {
		return requestStack;
	}

	public ListQueue<HelpRequest> getRequestQueue() {
		return requestQueue;
	}
}