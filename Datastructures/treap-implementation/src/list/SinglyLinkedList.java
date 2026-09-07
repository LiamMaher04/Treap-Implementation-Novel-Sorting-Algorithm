package list;

import interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods
        public E getElement() { return element; }

        public Node<E> getNext() { return next; }

        public void setNext(Node<E> n) { next = n; }


    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)

    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() { }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if(position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid index at position: " + position);
        }
        Node<E> current = head;
        for(int i = 0; i < position; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    @Override
    public void add(int position, E e) {
        if(position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid index at position: " + position);
        }
        if(position == 0) {
            addFirst(e);
        }
        if(position == size - 1) {
            addLast(e);
        }
        else {
            Node<E> prev = head;
            for(int i = 0; i < position - 1; i++) {
                prev = prev.getNext();
            }
            Node<E> next = prev.getNext();
            Node<E> newest = new Node<E>(e, next);
            prev.setNext(newest);
        }
    }


    @Override
    public void addFirst(E e) {
        head = new Node<E>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newest = new Node<E>(e, null);
        Node<E> last = head;
        if(last == null) {
            head = newest;
        }
        else {
            while(last.getNext() != null) {
                last = last.getNext();
            }
            last.setNext(newest);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        if(position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid index at position: " + position);
        }
        if(position == 0) {
            removeFirst();
        }

        Node<E> current = head;
        for(int i =0; i < position -1; i++) {
            if(current.getNext() == null) {
                throw new IndexOutOfBoundsException("Invalid index at position: " + position);
            }
            current = current.getNext();
        }

        if(current.getNext() == null) {
            throw new IndexOutOfBoundsException("Invalid index at position: " + position);
        }

        Node<E> removed = current.getNext();
        current.setNext(current.getNext().getNext());
        size--;
        return removed.getElement();
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) {
            return null;
        } else{
            Node<E> current = head;
            head = current.getNext();
            size--;
            return current.getElement();
        }
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (head.getNext() == null) {
            E element = head.getElement();
            head = null;
            size--;
            return element;
        }

        Node<E> current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }

        E element = current.getNext().getElement();
        current.setNext(null);
        size--;

        return element;
    }


    @Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node curr;
        public SinglyLinkedListIterator() {
            curr = head;
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = (E) curr.getElement();
            curr = curr.getNext();
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        while(current != null) {
            sb.append(current.getElement());
            if(current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }


    public void reverse() {
        if(head != null) {
            head = reverseHelper(null,head);

        }
    }

    private Node<E> reverseHelper(Node<E> curr, Node<E> succ) {
        Node<E> n = succ.getNext();
        succ.setNext(curr);
        if(n == null) {
            return succ;
        }
        return reverseHelper(succ, n);
    }

    public SinglyLinkedList<E> recursiveCopy() {
        SinglyLinkedList<E> newList = new SinglyLinkedList<>();
        newList.head = copy(this.head);
        return newList;

    }


    private Node<E> copy(Node<E> node) {
        if(node == null) {
            return null;
        }
        Node<E> nodeCopy = new Node<E>(node.getElement(), copy(node.getNext()));
        return nodeCopy;
    }





    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        System.out.println(ll);

        ll.remove(2);
        System.out.println(ll);

        ll.addLast(6);
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.add(3, 8);
        System.out.println(ll);

        ll.recursiveCopy();
        System.out.println(ll);
        ll.reverse();


        System.out.println(ll);



    }
}

