class Teacher {
	private String name;
	private Experience experience;
	private HelpRequest currRequest;
	private final ArrayStack<HelpRequest> requestStack;
	private final ListQueue<HelpRequest> requestQueue;

	public Teacher() {
		this.requestStack = new ArrayStack<>();
		this.requestQueue = new ListQueue<>();
		this.currRequest = new HelpRequest();
	}
	
	public Teacher(String name, Experience experience) {
		this.name = name;
		this.experience = experience;
		this.requestStack = new ArrayStack<>();
		this.requestQueue = new ListQueue<>();
		this.currRequest = new HelpRequest();
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

	public String getExperience() {
		return experience.getExperience();
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public HelpRequest getCurrentRequest() {
		return currRequest;
	}

	public void setCurrentRequest(HelpRequest currRequest) {
		currRequest = this.currRequest;
	}

	public ArrayStack<HelpRequest> getRequestStack() {
		return requestStack;
	}

	public ListQueue<HelpRequest> getRequestQueue() {
		return requestQueue;
	}
}