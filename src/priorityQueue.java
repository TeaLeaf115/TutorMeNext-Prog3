import java.util.*;
public interface priorityQueue<E> {
	public boolean add(E e);
	public void clear();
	public Comparator<? super E> comparator();
	public boolean contains(Object o);
	public Iterator<E> iterator();
	public E element();
	public boolean offer(E e);
	public E peek();
	public E poll();
	public boolean remove(Object o);
	public boolean isEmpty();
	public int size();
}