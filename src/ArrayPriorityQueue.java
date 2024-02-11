import java.util.*;

public class ArrayPriorityQueue<E> implements priorityQueue<E> {
	private PriorityQueue<E> pq;
	
	public ArrayPriorityQueue() {
		pq = new PriorityQueue<>();
	}
	
	public boolean add(E e) {
		return pq.add(e);
	}
	
	public void clear() {
		pq.clear();
	}
	
	public Comparator<? super E> comparator() {
		return pq.comparator();
	}
	
	public boolean contains(Object o) {
		return pq.contains(o);
	}
	
	public Iterator<E> iterator() {
		return pq.iterator();
	}
	
	public E element() {
		return pq.element();
	}

	public boolean offer(E e) {
		return pq.offer(e);
	}
	
	public E peek() {
		return pq.peek();
	}
	
	public E poll() {
		return pq.poll();
	}
	
	public boolean remove(Object o) {
		return pq.remove(o);
	}
	
	public boolean isEmpty() {
		return pq.isEmpty();
	}
	
	public int size() {
		return pq.size();
	}

	public PriorityQueue getReal() {
		return pq;
	}
}