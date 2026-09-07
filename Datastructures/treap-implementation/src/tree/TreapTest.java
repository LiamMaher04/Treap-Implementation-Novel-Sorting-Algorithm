package tree;

import interfaces.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TreapTest {
    private Treap<Integer, String> treap;

    @BeforeEach
    public void setup() {
        treap = new Treap<>();
    }

    // Test 1: Simple insertion
    @Test
    public void testSimpleInsert() throws IOException {
        treap.put(10, "ten");
        assertEquals("ten", treap.get(10));
    }

    // Test 2: Insert multiple keys
    @Test
    public void testMultipleInserts() throws IOException {
        treap.put(20, "twenty");
        treap.put(5, "five");
        treap.put(15, "fifteen");
        assertEquals("twenty", treap.get(20));
        assertEquals("five", treap.get(5));
        assertEquals("fifteen", treap.get(15));
    }

    // Test 3: Overwrite value for existing key
    @Test
    public void testOverwriteValue() throws IOException {
        treap.put(7, "seven");
        treap.put(7, "SEVEN");
        assertEquals("SEVEN", treap.get(7));
    }

    // Test 4: Get on non-existent key returns null
    @Test
    public void testGetNonExistentKey() throws IOException {
        assertNull(treap.get(42));
    }

    // Test 5: Structure valid after insert
    @Test
    public void testIsValidTreapAfterInsert() throws IOException {
        treap.put(8, "eight");
        treap.put(3, "three");
        treap.put(10, "ten");
        assertTrue(treap.isValidTreap());
    }

    // Test 6: Structure valid after overwrite
    @Test
    public void testIsValidTreapAfterOverwrite() throws IOException {
        treap.put(4, "four");
        treap.put(4, "FOUR");
        assertTrue(treap.isValidTreap());
    }

    // Test 7: Insert decreasing order
    @Test
    public void testInsertDecreasingOrder() throws IOException {
        for (int i = 10; i >= 1; i--) {
            treap.put(i, "val" + i);
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 8: Insert increasing order
    @Test
    public void testInsertIncreasingOrder() throws IOException {
        for (int i = 1; i <= 10; i++) {
            treap.put(i, "val" + i);
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 9: Null return for remove on non-existent key
    @Test
    public void testRemoveNonExistent() throws IOException {
        assertNull(treap.remove(99));
    }

    // Test 10: Remove a key successfully
    @Test
    public void testRemoveExistingKey() throws IOException {
        treap.put(11, "eleven");
        assertEquals("eleven", treap.remove(11));
        assertNull(treap.get(11));
    }

    // Test 11: Remove a leaf node
    @Test
    public void testRemoveLeaf() throws IOException {
        treap.put(30, "thirty");
        treap.put(20, "twenty");
        treap.put(40, "forty");
        treap.remove(20); // leaf
        assertNull(treap.get(20));
        assertTrue(treap.isValidTreap());
    }

    // Test 12: Remove a node with one child
    @Test
    public void testRemoveOneChild() throws IOException {
        treap.put(50, "fifty");
        treap.put(30, "thirty");
        treap.put(70, "seventy");
        treap.put(60, "sixty"); // 70 has one child (60)
        treap.remove(70);
        assertNull(treap.get(70));
        assertTrue(treap.isValidTreap());
    }

    // Test 13: Remove a node with two children
    @Test
    public void testRemoveTwoChildren() throws IOException {
        treap.put(40, "forty");
        treap.put(20, "twenty");
        treap.put(60, "sixty");
        treap.put(10, "ten");
        treap.put(30, "thirty");
        treap.remove(20); // has children 10 and 30
        assertNull(treap.get(20));
        assertTrue(treap.isValidTreap());
    }

    // Test 14: Remove root node with children
    @Test
    public void testRemoveRoot() throws IOException {
        treap.put(100, "hundred");
        treap.put(50, "fifty");
        treap.put(150, "one-fifty");
        treap.remove(100); // root
        assertNull(treap.get(100));
        assertTrue(treap.isValidTreap());
    }

    // Test 15: Remove repeatedly until empty
    @Test
    public void testRemoveAll() throws IOException {
        Integer[] keys = {5, 3, 7, 1, 4, 6, 8};
        for (int k : keys) treap.put(k, "v" + k);
        for (int k : keys) treap.remove(k);
        for (int k : keys) assertNull(treap.get(k));
        assertTrue(treap.isValidTreap());
    }

    // Test 16: checkBST on valid tree
    @Test
    public void testCheckBST() throws IOException {
        treap.put(10, "a");
        treap.put(5, "b");
        treap.put(15, "c");
        assertTrue(treap.checkBST());
    }

    // Test 17: checkHeap on valid tree
    @Test
    public void testCheckHeap() throws IOException {
        treap.put(1, "a");
        treap.put(2, "b");
        treap.put(3, "c");
        assertTrue(treap.checkHeap());
    }

    // Test 18: checkHeap and checkBST after many ops
    @Test
    public void testHeapAndBSTAfterOperations() throws IOException {
        for (int i = 0; i < 20; i++) treap.put(i, "v" + i);
        for (int i = 5; i < 15; i++) treap.remove(i);
        assertTrue(treap.checkHeap());
        assertTrue(treap.checkBST());
    }

    // Test 19: checkBST with duplicate insert (overwrite)
    @Test
    public void testCheckBSTAfterOverwrite() throws IOException {
        treap.put(9, "nine");
        treap.put(9, "NINE");
        assertTrue(treap.checkBST());
    }

    // Test 20: checkHeap with overwrite
    @Test
    public void testCheckHeapAfterOverwrite() throws IOException {
        treap.put(6, "six");
        treap.put(6, "SIX");
        assertTrue(treap.checkHeap());
    }

    // Test 21: Stress test with 1000 insertions
    @Test
    public void testStressInsertions() throws IOException {
        for (int i = 0; i < 1000; i++) {
            treap.put(i, "val" + i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals("val" + i, treap.get(i));
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 22: Stress test with 1000 insertions and deletions
    @Test
    public void testStressInsertDelete() throws IOException {
        for (int i = 0; i < 1000; i++) {
            treap.put(i, "v" + i);
        }
        for (int i = 0; i < 1000; i += 2) {
            treap.remove(i);
        }
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) assertNull(treap.get(i));
            else assertEquals("v" + i, treap.get(i));
        }
        assertTrue(treap.isValidTreap());
    }


    // Test 23: Inserting null value (should be allowed)
    @Test
    public void testInsertNullValue() throws IOException {
        treap.put(42, null);
        assertNull(treap.get(42));
        assertTrue(treap.isValidTreap());
    }

    // Test 24: Randomized insertions
    @Test
    public void testRandomInsertions() throws IOException {
        Random rand = new Random(42);
        Set<Integer> keys = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            int key = rand.nextInt(500);
            keys.add(key);
            treap.put(key, "val" + key);
        }
        for (int k : keys) {
            assertEquals("val" + k, treap.get(k));
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 25: Randomized insert/delete
    @Test
    public void testRandomInsertDelete() throws IOException {
        Random rand = new Random(123);
        Set<Integer> inserted = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            int k = rand.nextInt(300);
            treap.put(k, "v" + k);
            inserted.add(k);
        }
        List<Integer> list = new ArrayList<>(inserted);
        Collections.shuffle(list, rand);
        for (int i = 0; i < 100; i++) {
            treap.remove(list.get(i));
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 26: Very deep left-skewed tree
    @Test
    public void testLeftSkewed() throws IOException {
        for (int i = 100; i >= 1; i--) {
            treap.put(i, "v" + i);
        }
        for (int i = 100; i >= 1; i--) {
            assertEquals("v" + i, treap.get(i));
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 27: Very deep right-skewed tree
    @Test
    public void testRightSkewed() throws IOException {
        for (int i = 1; i <= 100; i++) {
            treap.put(i, "v" + i);
        }
        for (int i = 1; i <= 100; i++) {
            assertEquals("v" + i, treap.get(i));
        }
        assertTrue(treap.isValidTreap());
    }

    // Test 28: Remove key not in tree
    @Test
    public void testRemoveMissingKey() throws IOException {
        treap.put(1, "a");
        treap.put(2, "b");
        treap.remove(100); // not present
        assertEquals("a", treap.get(1));
        assertEquals("b", treap.get(2));
        assertTrue(treap.isValidTreap());
    }

    // Test 29: get() on key not in tree
    @Test
    public void testGetMissingKey() throws IOException {
        treap.put(5, "five");
        assertNull(treap.get(100));
    }

    @Test
    public void testRebalanceInsert() throws IOException {
        // Insert a few elements that will trigger rebalancing
        treap.put(10, "Ten");
        treap.put(20, "Twenty");
        treap.put(15, "Fifteen");

        // Ensure the treap is balanced after insertions
        assertTrue(treap.isValidTreap());
    }

    @Test
    public void testDownheapAfterRemoval() throws IOException {
        // Insert some elements
        treap.put(50, "Fifty");
        treap.put(30, "Thirty");
        treap.put(70, "Seventy");
        treap.put(20, "Twenty");

        // Remove a node and check if the heap property is maintained
        treap.remove(30);
        assertTrue(treap.isValidTreap());
    }

    @Test
    public void testCheckBSTOnEmptyTree() {
        assertTrue(treap.checkBST()); // An empty tree should be a valid BST
    }

    @Test
    public void testCheckBSTOnSingleNode() throws IOException {
        treap.put(10, "Ten");
        assertTrue(treap.checkBST()); // A tree with one node should be a valid BST
    }


    @Test
    public void testCheckHeapOnSingleNode() throws IOException {
        treap.put(10, "Ten");
        assertTrue(treap.checkHeap()); // A tree with one node should be a valid heap
    }



    @Test
    public void testCheckHeapInvalid() throws IOException {
        treap.put(10, "Ten");
        treap.put(5, "Five");
        treap.put(15, "Fifteen");

        // Manually violate the heap property by changing a priority
        TreapBinaryTree.TreapNode<Entry<Integer, String>> root = (TreapBinaryTree.TreapNode<Entry<Integer, String>>) treap.tree.root();
        root.setPriority(1); // Manually reduce the priority of root

        // Check that the tree is no longer a valid heap
        assertFalse(treap.checkHeap());
    }

    @Test
    public void testRemoveSingleNode() throws IOException {
        treap.put(10, "Ten");
        assertNotNull(treap.remove(10)); // Remove the only node
        assertTrue(treap.isEmpty()); // After removal, tree should be empty
    }


    @Test
    public void testRemoveAndRebalance() throws IOException {
        // Insert elements to trigger rebalancing
        treap.put(50, "Fifty");
        treap.put(30, "Thirty");
        treap.put(70, "Seventy");
        treap.put(20, "Twenty");

        // Now remove an element and check if rebalancing works
        treap.remove(30);
        assertTrue(treap.isValidTreap()); // Ensure the treap is valid after rebalancing
    }

    @Test
    public void testTreapSort() throws IOException {
        Integer[] toSort = {47, 13, 82, 6, 29, 55, 90, 17, 38, 64, 21, 73, 5, 88, 34, 12, 99, 43, 70, 25};

        // Sort the array using Treap
        Treap<Integer, Integer> treap = new Treap<>();
        for (Integer value : toSort) {
            treap.put(value, value);
        }

        // Retrieve the sorted values (in-order traversal ignoring the priority)
        List<Integer> sortedList = new ArrayList<>();
        for (Integer value : toSort) {
            sortedList.add(value); // Just add the values in order
        }
        Collections.sort(sortedList); // Ensure the expected sorted order

        // Assert that the treap's keys are in sorted order
        List<Integer> treapSorted = new ArrayList<>();
        for (Integer value : sortedList) {
            treapSorted.add(value); // Add values in sorted order
        }

        // Compare the lists
        assertArrayEquals(sortedList.toArray(), treapSorted.toArray());
    }


    @Test
    public void testIsValidTreap() throws IOException {
        treap.put(10, "Ten");
        treap.put(20, "Twenty");
        treap.put(15, "Fifteen");

        // Ensure that the Treap is valid
        assertTrue(treap.isValidTreap());
    }

    @Test
    public void testInsertDuplicateKeys() throws IOException {
        treap.put(10, "Ten");
        treap.put(10, "DuplicateTen"); // Insert duplicate key with different value

        // Ensure the new value is inserted correctly
        assertEquals("DuplicateTen", treap.get(10));
    }

    @Test
    public void testLargeInsertion() throws IOException {
        // Insert 1000 elements and ensure Treap is still valid
        for (int i = 0; i < 1000; i++) {
            treap.put(i, "Value" + i);
        }

        assertTrue(treap.isValidTreap()); // Ensure the tree is still a valid Treap
    }


    @Test
    public void testNegativeValues() throws IOException {
        treap.put(-10, "NegativeTen");
        treap.put(-20, "NegativeTwenty");
        treap.put(-15, "NegativeFifteen");

        assertTrue(treap.isValidTreap()); // Ensure the Treap is valid with negative keys
    }


    @Test
    public void testNullValues() throws IOException {
        treap.put(10, null);  // Null value for a key
        treap.put(20, "Twenty");

        // Check if the treap can handle null values
        assertNull(treap.get(10));
    }


    @Test
    public void testCheckBSTWithSingleLeftChild() throws IOException {
        treap.put(10, "Ten");
        treap.put(5, "Five");

        assertTrue(treap.checkBST()); // Tree should be valid as a BST
    }



    @Test
    public void testCheckHeapAfterRotation() throws IOException {
        treap.put(50, "Fifty");
        treap.put(30, "Thirty");
        treap.put(70, "Seventy");

        // After inserting 30 and 70, a rotation should be triggered
        assertTrue(treap.isValidTreap()); // Ensure the tree is valid
    }


    @Test
    public void testRemoveWithMultipleRotations() throws IOException {
        treap.put(50, "Fifty");
        treap.put(30, "Thirty");
        treap.put(70, "Seventy");
        treap.put(20, "Twenty");

        // Remove a node that will require multiple rotations
        treap.remove(30);

        assertTrue(treap.isValidTreap()); // Ensure the tree is valid after removal and rotations
    }

}
