public class Experience {
	
	public static final String UNDEFINED = "undefined";
	public static final String FIRST_YEAR = "first year";
	public static final String INTERMEDIATE = "intermediate";
	public static final String EXPERIENCED = "experienced";
	
	// class method
	public static boolean LegalExperience(String experience) {
		return experience.equals(FIRST_YEAR) || experience.equals(INTERMEDIATE) || experience.equals(EXPERIENCED);
	}
	
	private String myExperience = UNDEFINED;
	
	// constructors
	
	public Experience() {
	}
	
	public Experience(String aString) {
		setExperience(aString);
	}
	
	public Experience(Experience anExperience) {
		myExperience = anExperience.myExperience;
	}
	
	// accessor
	
	public String getExperience() {
		return myExperience;
	}
	
	// mutator
	
	public void setExperience(String experience) {
		if (!LegalExperience(experience))
			throw new IllegalStateException("Experience.setExperience(), illegal value for experience.");
		myExperience = experience;
	}

	public void setExperience(Experience experience) {
		myExperience = experience.toString();
	}
	
	public String toString() {
		return myExperience;
	}
}