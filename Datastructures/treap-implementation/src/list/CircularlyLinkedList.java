package list;

import interfaces.List;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            element = e;
            next = n;
        }

        public T getElement() { return element; }

        public Node<T> getNext() { return next; }

        public void setNext(Node<T> n) { next = n; }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> start = tail.getNext();
        for(int i =0; i<index; i++) {
            start = start.next;
        }

        return start.getElement();
    }

    @Override
    public void add(int index, E e) {
      if(index < 0 || index >= size) {
          throw new IndexOutOfBoundsException();
      }

      if(index == 0) {
          addFirst(e);
      }

      if(index == size) {
          addLast(e);
      }

      Node<E> start = tail.getNext();
      for(int i =0; i<index -1; i++) {
          start = start.getNext();
      }
      Node<E> newNode = new Node<>(e, start.getNext());
      start.setNext(newNode);
      size++;
    }

    @Override
    public E remove(int i) {
        if(i < 0 || i >= size) {
            return null;
        }
        Node<E> start = tail.getNext();
        for(int count =0; count < i - 1; count++) {
            start = start.next;
        }
        Node<E> removed = start.getNext();
        start.setNext(removed.getNext());
        size--;
        return removed.getElement();
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        private Node<E> curr = (Node<E>) tail.getNext();
        private int index = 0;

        @Override
        public boolean hasNext() {
            return curr != null && index < size;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                return null;
            }
            E element = curr.getElement();
            curr = curr.getNext();
            index++;
            return element;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) {
            return null;
        }
        Node<E> head = tail.getNext();
        if(head == tail) {
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }
        size--;
        return head.getElement();
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node<E> head = tail.getNext();
        if (head != tail) {
            head = head.getNext();
        }

        Node<E> remove = tail;
        head.setNext(tail.getNext());
        size--;
        return remove.getElement();
    }

    @Override
    public void addFirst(E e) {
        if (size == 0) {
            tail = new Node<>(e, null);
            tail.setNext(tail);
        } else {
            Node<E> newest = new Node<>(e,tail.getNext());
            tail.setNext(newest);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        tail = tail.getNext();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<E> iterator = this.iterator();

        while(iterator.hasNext()) {
            sb.append(iterator.next());
            if(iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for(int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
