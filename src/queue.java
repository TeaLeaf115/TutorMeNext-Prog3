import java.util.Queue;
public interface queue<E> {
	public boolean add(E e);
	public E element();
	// public boolean offer(E e);
	public E peek();
	public E poll();
	public E remove();
	public boolean contains(Object o);
	public boolean isEmpty();
	public int size();
	public boolean equals(ListQueue<E> o);
}