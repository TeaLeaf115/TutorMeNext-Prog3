import java.util.Stack;
public interface stack<E> {
	public boolean empty();
	public E peek();
	public E pop();
	public E push(E item);
	public int search(Object o);
	public boolean equals(ArrayStack<E> o);
	public boolean isEmpty();
	public int size();
	public String toString();
}