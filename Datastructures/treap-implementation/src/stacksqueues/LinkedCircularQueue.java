package stacksqueues;

import interfaces.Queue;
import list.CircularlyLinkedList;

/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {
	private CircularlyLinkedList<E> ll;

	public LinkedCircularQueue() {
		ll = new CircularlyLinkedList<>();
	}

	public static void main(String[] args) {
		LinkedCircularQueue<Integer>  ll= new LinkedCircularQueue<>();
		for(int i =0; i < 10; i++) {
			ll.enqueue(i);
		}
		System.out.println(ll);

		for(int i =0; i < 10; i++) {
			ll.dequeue();
			System.out.println(ll);
		}

	}

	@Override
	public int size() {
		return ll.size();
	}

	@Override
	public boolean isEmpty() {
		return ll.isEmpty();
	}

	@Override
	public void enqueue(E e) {
		ll.addLast(e);
	}

	@Override
	public E first() {
		return ll.get(0);
	}

	@Override
	public E dequeue() {
		return ll.removeFirst();
	}

	@Override
	public String toString() {
		if(ll.isEmpty()) {
			return "[]";
		}
		return ll.toString();
	}

}
