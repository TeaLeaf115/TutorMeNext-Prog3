public class Error {
	
	public static final String UNDEFINED = "undefined";
	public static final String COMPILING = "compiling";
	public static final String LINKING = "linking";
	public static final String RUNTIME = "runtime";
	
	// class method
	public static boolean LegalError(String error) {
		return error.equals(COMPILING) || error.equals(LINKING) || error.equals(RUNTIME);
	}
	
	private String myError = UNDEFINED;
	
	// constructors
	
	public Error() {
	}
	
	public Error(String aString) {
		setError(aString);
	}
	
	public Error(Error anError) {
		myError = anError.myError;
	}
	
	// accessor
	
	public String getError() {
		return myError;
	}
	
	// mutator
	
	public void setError(String error) {
		if (!LegalError(error))
			throw new IllegalStateException("Error .setError(), illegal value for Error.");
		myError = error;
	}
	
	public String toString() {
		return myError;
	}
}