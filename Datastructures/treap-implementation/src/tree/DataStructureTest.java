package tree;

import java.io.IOException;

public interface DataStructureTest {
    void setup(int[] input) throws IOException; // Setup or batch insert
    void insert(int key, int value) throws IOException; // Single insertion
    boolean search(int key) throws IOException; // Successful/unsuccessful search
    void delete(int key) throws IOException; // Deletion
    int[] inOrderTraversal(); // In-order traversal
    void clear(); // Reset structure for next test
    void batchInsert(int[] keys) throws IOException; // Insert multiple elements (Batch)
}
