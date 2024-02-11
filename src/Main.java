public class Main {
	public static void main(String[] args) {
		ArrayStack<String> as = new ArrayStack<>();
		as.push("Hello");
		as.push("World");
		System.out.println(as.toString());
		as.pop();
		System.out.println(as.toString());
	}
}