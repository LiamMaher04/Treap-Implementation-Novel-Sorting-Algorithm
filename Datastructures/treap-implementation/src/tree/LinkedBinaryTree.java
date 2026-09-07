package tree;

import interfaces.BinaryTree;
import interfaces.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E extends Comparable<E>> implements BinaryTree<E> {


    protected Node<E> root = null; // root of the tree

    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // LinkedBinaryTree instance variables
    protected int size = 0; // number of nodes in the tree

// int btDiameter ()
// if no nodes or if only root
// return 0 and 1 respectively
// diameter = 0
// for nodes that are external
// add to array of position of nodes
// for each external node traverse tree to reach external node
// if only child get parent
// if left node
// if parent node less than node were looking for
// check right child isnt one were looking for
// if right is less than what were looking for move to parent
// traverse down to branch
// if right node
// if parent node is greater than node were looking for
// check left child isnt one were looking for
// if left is greater than what were looking for move to parent
// traverse down branch
// return route with longest diameter

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return makeRandom(n, arr); //array of nums
    }

    public static <E extends Comparable<E>> LinkedBinaryTree<E> makeRandom(int n, E[] arr) {
        if (arr.length < n) {
            throw new IllegalArgumentException("Array length must be at least n");
        }

        LinkedBinaryTree<E> tree = new LinkedBinaryTree<>();
        Random rand = new Random();
        List<E> list = Arrays.asList(arr);
        Collections.shuffle(list, rand);
        E[] shuffledArr = list.toArray(arr);

        tree.root = constructTree(shuffledArr, 0, n - 1);
        tree.size = n;
        return tree;
    }

    private static <E extends Comparable<E>> Node<E> constructTree(E[] arr, int start, int end) {
        if (start > end) {
            return null;
        }

        Random rand = new Random(); //random index for root
        int randomIndex = start + rand.nextInt(end - start + 1);

        E rootValue = arr[randomIndex];
        Node<E> root = new Node<>(rootValue, null, null, null);

        List<E> leftList = new ArrayList<>();   //left subtree
        List<E> rightList = new ArrayList<>();  //right subtree

        for (int i = start; i <= end; i++) {
            if (i != randomIndex) { // skip the root
                if (arr[i].compareTo(rootValue) < 0) {
                    leftList.add(arr[i]);
                } else {
                    rightList.add(arr[i]);
                }
            }
        }

        root.setLeft(constructTree(leftList.toArray((E[]) new Comparable[0]), 0, leftList.size() - 1));
        root.setRight(constructTree(rightList.toArray((E[]) new Comparable[0]), 0, rightList.size() - 1));

        if (root.getLeft() != null) {
            root.getLeft().setParent(root);
        }
        if (root.getRight() != null) {
            root.getRight().setParent(root);
        }
        return root;
    }

    private static <E extends Comparable<E>> void printExternNodes(Node<E> node) {
        if (node == null) {
            return; // Base case: if the node is null, do nothing
        }

        if (node.left == null && node.right == null) {
            System.out.print(node.getElement() + " ");
            return;
        }

        // Recursively check the left and right subtrees
        printExternNodes(node.left);
        printExternNodes(node.right);
    }

    //public static void main(String [] args) {
    //    int max_n = 100000;
    //    int n_delta = max_n / 50;
    //    for(int n = 50; n < max_n; n+=n_delta) {
    //        DescriptiveStatistics stats = new DescriptiveStatistics();
    //        for(int j = 0; j < 500; ++j) {
    //            LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(n);
    //           double h = height(bt.root);
    //            stats.addValue(h);
    //            if(j > 100 && stats.getStandardDeviation() < 1.5)
    //                break;
    //        }
    //        System.out.println(n + ", " + stats.getMean() + ", " + stats.getStandardDeviation()); } }


    //public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
    //    Integer [] inorder= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    //    Integer [] preorder = {18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16, 17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30};
    //    LinkedBinaryTree <Integer > bt = new LinkedBinaryTree <>();
    //    bt. construct (inorder , preorder );
    //    System.out.println(bt. toBinaryTreeString ());
    //    printExternNodes(bt.root);
    //}

    public static void main(String[] args) {
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        for (int n = 10; n <= 10000; n++) {
            tree.makeRandom(n);
            long startTime = System.nanoTime();
            tree.inorder();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000; // Convert to microseconds
            System.out.println(n + "," + duration);
        }
    }

    //public static void main(String [] args) {
    //    LinkedBinaryTree <String > bt = new LinkedBinaryTree <>();
    //    String [] arr = { "A", "B", "C", "D", "E", null ,"F", null , null ,"G", "H", null ,null, null, null};
    //   bt. createLevelOrder (arr);
    //    System.out.println(bt. toBinaryTreeString ());
    //    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> sibling(Position<E> p){
        Node<E> node = validate(p);
        Node<E> parent = node.getParent();
        if (parent == null) {
            return null;
        }
        if (node == parent.getLeft()) {
            return parent.getRight(); // return right sibling
        } else {
            return parent.getLeft(); // return left sibling
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns true if Position p has one or more children.
     *
     * @param p A valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using an inorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if (left(p) != null) inorderSubtree(left(p), snapshot);
        if (p.getElement() != null) snapshot.addLast(p);
        if (right(p) != null) inorderSubtree(right(p), snapshot);
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if(p.getElement() != null) {
            snapshot.addLast(p);
        }// for preorder, we add position p before exploring subtrees
        for (Position<E> c : children(p)) {
            preorderSubtree(c, snapshot);
        }
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in preorder.
     *
     * @return iterable collection of the tree's positions in preorder
     */
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in inorder.
     *
     * @return iterable collection of the tree's positions reported in inorder
     */
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            inorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a postorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        for(Position<E> c : children(p)) {
            preorderSubtree(c, snapshot);
        }
        snapshot.add(p);
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in postorder.
     *
     * @return iterable collection of the tree's positions in postorder
     */
    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            postorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    public Iterable<Position<E>> positions() {
        return inorder();
    }

    /**
     * Returns the number of levels separating Position p from the root.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int depth(Position<E> p) throws IllegalArgumentException {
        if (isRoot(p)) {
            return 0;
        } else {
            return depth(parent(p))+1;
        }
    }

    /**
     * Returns the height of the tree.
     * <p>
     * Note: This implementation works, but runs in O(n^2) worst-case time.
     */
    private int heightBad() {
        int maxHeight = 0;
        for (Position<E> p : positions()) {
            int height = 0;
            for (Position<E> pos = p; pos != root(); pos = parent(pos)) {
                height++;
            }
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        return maxHeight;
    }

    /**
     * Returns the height of the subtree rooted at Position p.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int height(Position<E> p) throws IllegalArgumentException {
        if (isExternal(p)) {
            return 0;
        } else {
            int h = 0;
            for (Position<E> c : children(p)) {
                h = Math.max(h, height(c));
            }
            return h + 1;
        }
    }

    /**
     * Returns true if Position p represents the root of the tree.
     *
     * @param p A valid Position within the tree
     * @return true if p is the root of the tree, false otherwise
     */
    public boolean isRoot(Position<E> p) {
        return p == root();
    }
    // nonpublic utility

    /**
     * Returns true if Position p does not have any children.
     *
     * @param p A valid Position within the tree
     * @return true if p has zero children, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public boolean isExternal(Position<E> p) {
        if(numChildren(p) == 0) {
            return true;
        } else return false;
    }

    /**
     * Returns an iterable collection of the Positions representing p's children.
     *
     * @param p A valid Position within the tree
     * @return iterable collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Iterable<Position<E>> children(Position<E> p) {
        Node<E> node = validate(p);
        List<Position<E>> children = new ArrayList<>(2);
        if (node.getLeft() != null) {
            children.add(node.getLeft());
        }
        if (node.getRight() != null) {
            children.add(node.getRight());
        }
        return children;
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param p A valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int numChildren(Position<E> p) {
        int children = 0;
        Node<E> node = validate(p);
        if (node.getLeft() != null) {
            children++;
        }
        if (node.getRight() != null) {
            children++;
        }
        return children;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMinimum(Node<E> n) {
        validate(n);
        setRoot(n);
        List<E> elements = new ArrayList<>();
        for (Position<E> p : positions()) {
            elements.add(p.getElement());
        }
        E minValue = elements.stream().min(Comparable::compareTo).orElse(null);
        for (Position<E> p : positions()) {
            if (p.getElement().equals(minValue)) {
                return validate(p);
            }
        }
        return null;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMaximum(Node<E> n) {
        validate(n);
        setRoot(n);
        List<E> elements = new ArrayList<>();
        for (Position<E> p : positions()) {
            elements.add(p.getElement());
        }
        E maxValue = elements.stream().max(Comparable::compareTo).orElse(null);
        for (Position<E> p : positions()) {
            if (p.getElement().equals(maxValue)) {
                return validate(p);
            }
        }
        return null;
    }

    // Recursive function to find an inorder successor
    private Node<E> inorderSuccessor(Node<E> node, Node<E> succ, E key) {
        // TODO
        return null;
    }

    private Node<E> inorderPredecessor(Node<E> node, Node<E> pred, E key) {
        // TODO
        return null;
    }

    public Position<E> inorderSuccessor(E key) {
        return inorderSuccessor(root, null, key);
    }

    public Position<E> inorderPredecessor(E key) {
        return inorderPredecessor(root, null, key);
    }


    /**
     * Returns an iterable collection of positions of the tree in breadth-first order.
     *
     * @return iterable collection of the tree's positions in breadth-first order
     */
    public Iterable<Position<E>> breadthfirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            List<Position<E>> queue = new ArrayList<>();
            queue.add(root());
            while (!queue.isEmpty()) {
                Position<E> p = queue.remove(0);
                snapshot.add(p);
                for (Position<E> c : children(p)) {
                    queue.add(c);
                }
            }
        }
        return snapshot;
    }

    public void construct(E[] inorder, E[] preorder) {
        root = construct_tree(inorder, preorder, 0, preorder.length - 1, 0, inorder.length - 1);
        size = inorder.length;
    }

    private Node<E> construct_tree(E[] inorder, E[] preorder, int pStart, int pEnd, int iStart, int iEnd) {
        if (pStart > pEnd || iStart > iEnd) {
            return null;
        }

        E rootValue = preorder[pStart];
        Node<E> root = createNode(rootValue, null, null, null);

        int rootIndex = iStart;
        for (int i = iStart; i <= iEnd; i++) {
            if (inorder[i].equals(rootValue)) {
                rootIndex = i;
                break;
            }
        }

        int leftTreeSize = rootIndex - iStart;

        root.setLeft(construct_tree(inorder, preorder, pStart + 1, pStart + leftTreeSize, iStart, rootIndex - 1));
        root.setRight(construct_tree(inorder, preorder, pStart + leftTreeSize + 1, pEnd, rootIndex + 1, iEnd));

        if (root.getLeft() != null) {
            root.getLeft().setParent(root);
        }
        if (root.getRight() != null) {
            root.getRight().setParent(root);
        }

        return root;
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) throw new IllegalArgumentException("Not valid position type");
        // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree is not empty");
        }
        root = createNode(e, null, null, null);
        size++;
        return root;
    }

    /*
     * Create a detached node!
     */
    public Position<E> add(E e, Position<E> parent, Position<E> left, Position<E> right) {
        Node<E> newNode = createNode(e, validate(parent), validate(left), validate(right));
        if (parent != null) {
            Node<E> parentNode = validate(parent);
            if (parentNode.getLeft() == null) {
                parentNode.setLeft(newNode);
            } else if (parentNode.getRight() == null) {
                parentNode.setRight(newNode);
            } else {
                throw new IllegalArgumentException("Already has two children");
            }
        }
        size++;
        return newNode;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parentNode = validate(p);
        if (parentNode.getLeft() != null) {
            throw new IllegalArgumentException(p + " has a left child");
        }
        Node<E> child = createNode(e, parentNode, null, null);
        parentNode.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parentNode = validate(p);
        if (parentNode.getRight() != null) {
            throw new IllegalArgumentException(p + " has a right child");
        }
        Node<E> child = createNode(e, parentNode, null, null);
        parentNode.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    public void setRoot(Position<E> e) throws IllegalArgumentException {
        root = validate(e);
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("p has two children");
        }
        Node<E> child;
        if (node.getLeft() != null) {
            child = node.getLeft();
        } else {
            child = node.getRight();
        }
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
    size++;
    }

    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> p, int i) {
        if (i < l.size()) {
            Node<E> temp = new Node<>(l.get(i), null, null, null);
            p = temp;
            Node<E> leftChild = createLevelOrderHelper(l, p.getLeft(), 2 * i + 1);
            Node<E> rightChild = createLevelOrderHelper(l, p.getRight(), 2 * i + 2);
            p.setLeft(leftChild);
            p.setRight(rightChild);
            if (leftChild != null) {
                leftChild.setParent(p);
            }
            if (rightChild != null) {
                rightChild.setParent(p);
            }
        }
        return p;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
        size++;
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (i < arr.length) {
            Node<E> temp = new Node<>(arr[i], null, null, null);
            p = temp;
            Node<E> leftChild = createLevelOrderHelper(arr, p.getLeft(), 2 * i + 1);
            Node<E> rightChild = createLevelOrderHelper(arr, p.getRight(), 2 * i + 2);
            p.setLeft(leftChild);
            p.setRight(rightChild);
            if (leftChild != null) {
                leftChild.setParent(p);
            }
            if (rightChild != null) {
                rightChild.setParent(p);
            }
        }
        return p;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /*
     * Nested static class for a binary tree node.
     */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            // (e)
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            // sb.append(" l:").append(left.element).append(" r:").append(right.element);
            // sb.append();
            return sb.toString();
        }

    }

    /* This class adapts the iteration produced by positions() to return elements. */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }

}
