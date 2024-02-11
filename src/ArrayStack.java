import java.util.Stack;

public class ArrayStack<E> implements stack<E> {
	private Stack<E> s;
	public ArrayStack() {
		s = new Stack<E>();
	}
	
	public boolean empty() {
		return s.empty();
	}
	
	public E peek() {
		return s.peek();
	}
	
	public E pop() {
		return s.pop();
	}
	
	public E push(E item) {
		return s.push(item);
	}
	
	public int search(Object o) {
		return s.search(o);
	}
	
	public boolean equals(ArrayStack<E> o) {
		return s.equals(o.getReal());
	}
	
	public boolean isEmpty() {
		return s.isEmpty();
	}
	
	public int size() {
		return s.size();
	}

	public String toString() {
		return s.toString();
	}

	public Stack getReal() {
		return s;
	}
}