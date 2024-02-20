public class Demeanor {
	
	public static final String UNDEFINED = "undefined";
	public static final String POLITE = "polite";
	public static final String RUDE = "rude";
	
	// class method
	public static boolean LegalDemeanor(String demeanor) {
		return demeanor.equals(POLITE) || demeanor.equals(RUDE);
	}
	
	private String myDemeanor = UNDEFINED;
	
	// constructors
	
	public Demeanor() {
		return;
	}
	
	public Demeanor(String aString) {
		setDemeanor(aString);
		return;
	}
	
	public Demeanor(Demeanor aDemeanor) {
		myDemeanor = aDemeanor.myDemeanor;
		return;
	}
	
	// accessor
	
	public String getDemeanor() {
		return myDemeanor;
	}
	
	// mutator
	
	public void setDemeanor(String demeanor) {
		if (!LegalDemeanor(demeanor))
			demeanor = "undefined";
			// throw new IllegalStateException("Demeanor.setDeamor(), illegal value for demeanor.");
		myDemeanor = demeanor;
	}
	
	public String toString() {
		return myDemeanor;
	}
	
	public static void main(String[] args) {
		Demeanor aDemeanor = new Demeanor();
		System.out.println("Default demanor is " + aDemeanor.getDemeanor());
		Demeanor rudeDemeanor = new Demeanor(RUDE);
		System.out.println("Rude demeanor is " + rudeDemeanor.getDemeanor());
		Demeanor copyDemeanor = new Demeanor(RUDE);
		System.out.println("Copy demeanor is " + copyDemeanor.getDemeanor());
		// Demeanor illegalDemeanor = new Demeanor("Popular");
		return;
	}
	
}