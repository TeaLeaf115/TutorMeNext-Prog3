import java.util.Queue;
import java.util.LinkedList;
public class ListQueue<E> implements queue<E> {
	private Queue<E> q;

	public ListQueue() {
		q = new LinkedList<>();
	}

	public boolean add(E e) {
		return q.add(e);
	}
	
	public E element() {
		return q.element();
	}
	
	// public boolean offer(E e) {
	// 	return q.offer(e);
	// }
	
	public E peek() {
		return q.peek();
	}
	
	public E poll() {
		return q.poll();
	}
	
	public E remove() {
		return q.remove();
	}
	
	public boolean contains(Object o) {
		return q.contains(o);
	}
	
	public boolean isEmpty() {
		return q.isEmpty();
	}
	
	public int size() {
		return q.size();
	}
	
	public boolean equals(ListQueue<E> o) {
		return q.equals(o.getReal());
	}

	public Queue<E> getReal() {
		return q;
	}
}