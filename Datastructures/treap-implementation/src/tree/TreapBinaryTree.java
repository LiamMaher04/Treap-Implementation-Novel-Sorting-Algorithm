package tree;

import interfaces.Entry;
import interfaces.Position;
import tree.BalanceableBinaryTree.BSTNode;


import java.util.Random;

public class TreapBinaryTree<K extends Comparable<K>, V> extends BalanceableBinaryTree<K,V> {

    @Override
    protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left, Node<Entry<K, V>> right) {
        return new TreapNode<>(e, parent, left, right);
    }

    public TreapBinaryTree() {
        super();
    }

    //NESTED TREAP NODE CLASS
    protected static class TreapNode<E> extends BSTNode<E> {
        int priority;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        TreapNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
            super(e, parent, left, right);
            this.priority = new Random().nextInt(Integer.MAX_VALUE);
        }

        public String toString() {
            return this.getElement() == null ? "" : this.getElement().toString() + "[" + this.getPriority() + "]";
        }

    }
}
