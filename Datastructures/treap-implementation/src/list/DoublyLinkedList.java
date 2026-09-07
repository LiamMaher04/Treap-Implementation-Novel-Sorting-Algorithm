package list;

import interfaces.List;

import java.util.Iterator;
import java.util.LinkedList;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() { return element; }
        public DoublyLinkedList.Node<E> getNext() { return next; }
        public DoublyLinkedList.Node<E> getPrev() { return prev; }
        public void setNext(DoublyLinkedList.Node<E> n) { next = n; }
        public void setPrev(DoublyLinkedList.Node<E> p) { prev = p; }
    }


    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred,succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        Node<E> curr = header.getNext(); //get next to skip header node
        for(int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    @Override
    public void add(int index, E e) {
        Node<E> curr = header.getNext(); //header.getnext because header is non data node
        for(int i = 0; i < index -1; i ++) {
            curr = curr.getNext();
        }
        addBetween(e, curr, curr.getNext());
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<E> curr = header.getNext();
        for(int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        Node<E> prev = curr.getPrev();
        Node<E> next = curr.getNext();

        prev.setNext(next);
        size--;
        return curr.getElement();
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node curr;
        public DoublyLinkedListIterator() {
            curr = header.getNext();
        }

        @Override
        public boolean hasNext() {
            return curr != trailer;
        }

        @Override
        public E next() {
            E res = (E) curr.getElement();
            curr = curr.getNext();
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
        size--;
        return n.getElement();

    }

    public E first() {
        Node<E> first = header.getNext();
        if(isEmpty()) {
            return null;
        }
        else {
            return first.getElement();
        }
    }

    public E last() {
        Node<E> last = trailer.getPrev();
        if(isEmpty()) {
            return null;
        }
        else {
            return last.getElement();
        }
    }

    @Override
    public E removeFirst() {
        return remove(header.getNext());

    }

    @Override
    public E removeLast() {
        return remove(trailer.getPrev());
    }

    @Override
    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }

    @Override
    public void addFirst(E e) {
        addBetween(e, header, header.getNext());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        DoublyLinkedList.Node<E> current = header.getNext();
        while(current.getNext() != null) {
            sb.append(current.getElement());
            if(current.getNext().getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public void reverseInplace() {
        Node<E> prev = null;
        Node<E> curr = header;
        Node<E> next;

        while(curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        header = prev;

    }

    public static void main(String [] args) {
        Integer [] arr = {1,2,3,4,5,6,7,8,9};
        DoublyLinkedList<Integer> dl = new DoublyLinkedList<>();
        for(Integer i : arr) dl.addLast(i);
        System.out.println("forward list: " + dl);
        dl.reverseInplace();
        System.out.println("reverse list: " + dl);
    }
}
