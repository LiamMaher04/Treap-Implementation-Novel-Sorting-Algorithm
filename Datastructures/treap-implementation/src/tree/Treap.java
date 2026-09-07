package tree;

import interfaces.Entry;
import interfaces.Position;
import utils.MapEntry;

import java.io.IOException;
import java.util.*;

public class Treap<K extends Comparable<K> ,V> extends TreeMap<K,V> {

    public Treap() {
        super();
    }

    @Override
    protected BalanceableBinaryTree<K, V> makeTree() {
        return new TreapBinaryTree<>();
    }

    //Adding to a Treap involves first inserting the node the same as a BST, so we can just use the treemaps put implementation
    //however the heap property might not be restored so we have to upheap the node until the property is restored
    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) throws IOException {

        while (p != tree.root()) {
            // Safely cast position to TreapNode if it's valid
            TreapBinaryTree.TreapNode<Entry<K, V>> node = (TreapBinaryTree.TreapNode<Entry<K, V>>) p;
            TreapBinaryTree.TreapNode<Entry<K, V>> parent = (TreapBinaryTree.TreapNode<Entry<K, V>>) tree.parent(p);

            //if parent is null then we are at the root
            if (parent == null) break;

            // If the priority is already valid (parent has higher priority), stop
            if (node.getPriority() < parent.getPriority()) break;

            // Rotate to maintain heap property
            tree.rotate(p);
        }

    }



    protected void downheap(Position<Entry<K, V>> p) throws IOException {
        while (tree.isInternal(p)) {
            //left and right children of p and a placeholder
            Position<Entry<K, V>> leftChild = tree.left(p);
            Position<Entry<K, V>> rightChild = tree.right(p);
            Position<Entry<K, V>> highestPriorityChild = null;

            if (tree.isInternal(leftChild)) {
                highestPriorityChild = leftChild;
                //check if theres a right child and if its priority is greater then the left one
                if (tree.isInternal(rightChild)) {
                    int rightPriority = ((TreapBinaryTree.TreapNode<Entry<K, V>>) rightChild).getPriority();
                    int leftPriority = ((TreapBinaryTree.TreapNode<Entry<K, V>>) leftChild).getPriority();
                    if (rightPriority > leftPriority) {
                        highestPriorityChild = rightChild;
                    }
                }
                //if there is no left child and there is a right child then the right child has the highest
            } else if (tree.isInternal(rightChild)) {
                highestPriorityChild = rightChild;
            }


            if (highestPriorityChild != null && ((TreapBinaryTree.TreapNode<Entry<K, V>>) highestPriorityChild).getPriority() > ((TreapBinaryTree.TreapNode<Entry<K, V>>) p).getPriority()) {
                tree.rotate(highestPriorityChild);
                p = highestPriorityChild;
            } else {
                break; // Heap property satisfied
            }
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException, IOException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(tree.root(), key);

        //if the node is not in the tree return null
        if(tree.isExternal(p)) {
            return null;
        } else {
            V toBeRemoved = p.getElement().getValue();
            //if the node is already a leaf then we remove it straight away
            if(tree.numChildren(p) == 0) {
                super.remove(p.getElement().getKey());
            } else {
                downheap(p);
                super.remove(p.getElement().getKey());
            }

            return toBeRemoved;
        }

    }

    //Recursive call that runs through the tree and checks if it holds the BST property
    public boolean checkBST() {
        return checkBSTHelper(tree.root(), null, null);
    }

    private boolean checkBSTHelper(Position<Entry<K, V>> node, K min, K max) {
        if (node == null) return true;

        Entry<K, V> element = node.getElement();
        if (element == null) return true;  // Skip null elements (defensive check)

        K key = element.getKey();

        if ((min != null && key.compareTo(min) <= 0) || (max != null && key.compareTo(max) >= 0)) {
            return false;
        }

        return checkBSTHelper(tree.left(node), min, key) &&
                checkBSTHelper(tree.right(node), key, max);
    }


    public boolean isValidTreap() {
        return checkHeap() && checkBST();
    }

    //Recursive call that runs through tree and checks if it holds the heap property in regards the priorities of the nodes
    public boolean checkHeap() {
        return checkHeapHelper(tree.root());
    }

    private boolean checkHeapHelper(Position<Entry<K, V>> node) {
        if (node == null) return true;

        TreapBinaryTree.TreapNode<Entry<K, V>> current = (TreapBinaryTree.TreapNode<Entry<K, V>>) node;

        Position<Entry<K, V>> left = current.getLeft();
        Position<Entry<K, V>> right = current.getRight();

        if (left != null) {
            TreapBinaryTree.TreapNode<Entry<K, V>> leftNode = (TreapBinaryTree.TreapNode<Entry<K, V>>) left;

            // Only compare if both nodes have valid elements
            if (leftNode.getElement() != null && current.getElement() != null) {
                if (current.getPriority() < leftNode.getPriority()) {
                    return false;
                }
            }
        }

        if (right != null) {
            TreapBinaryTree.TreapNode<Entry<K, V>> rightNode = (TreapBinaryTree.TreapNode<Entry<K, V>>) right;

            if (rightNode.getElement() != null && current.getElement() != null) {
                if (current.getPriority() < rightNode.getPriority()) {
                    return false;
                }
            }
        }

        return checkHeapHelper(left) && checkHeapHelper(right);
    }


    //sorting alg for treap that adds values in a given array to the treap and returns the inorder value
    public static <T extends Comparable<T>> void treapSort(final T[] input) throws IOException {
        Treap<T,T> treap = new Treap<>();
        for(T i : input) {
            treap.put(i, i);
        }


    }



    public boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }


    public static void main(String[] args) throws IOException {
        Treap<Integer, String> treap = new Treap<>();

        Integer[] initialInsert = {73, 15, 91, 38, 59, 11, 86, 4, 27, 65, 98, 22, 51, 79, 31, 60, 88, 19, 45, 94, 7, 34, 56, 82, 1};

        System.out.println("== Inserting initial elements ==");
        for (Integer key : initialInsert) {
            treap.put(key, key.toString());
            if (!treap.isValidTreap()) {
                System.out.println("Invalid Treap after inserting: " + key);
            }
        }

        System.out.println("\nTreap structure (preorder):");
        System.out.println(treap.tree.preorder());

        System.out.println("\nTree (formatted):");
        System.out.println(treap.toBinaryTreeString());

        System.out.println("\n== Re-inserting same keys (update values) ==");
        for (Integer key : initialInsert) {
            treap.put(key, "Updated-" + key);
            if (!treap.isValidTreap()) {
                System.out.println("Invalid Treap after updating: " + key);
            }
        }

        System.out.println("\n== Checking BST and Heap properties ==");
        System.out.println("BST valid: " + treap.checkBST());
        System.out.println("Heap valid: " + treap.checkHeap());

        System.out.println("\n== Removing some keys ==");
        int[] keysToRemove = {59, 1, 91, 73};
        for (int key : keysToRemove) {
            System.out.println("Removing: " + key);
            treap.remove(key);
            if (!treap.isValidTreap()) {
                System.out.println("Invalid Treap after removing: " + key);
            }
        }

        System.out.println("\nTree after deletions:");
        System.out.println(treap.toBinaryTreeString());

        System.out.println("\n== Final Check ==");
        System.out.println("BST valid: " + treap.checkBST());
        System.out.println("Heap valid: " + treap.checkHeap());

        System.out.println("\n== Treap Sort Test ==");
        Integer[] toSort = {47, 13, 82, 6, 29, 55, 90, 17, 38, 64, 21, 73, 5, 88, 34, 12, 99, 43, 70, 25};
        Treap.treapSort(toSort);
    }

}
