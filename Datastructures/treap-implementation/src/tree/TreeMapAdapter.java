package tree;

import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TreeMapAdapter implements DataStructureTest {
    private TreeMap<Integer, Integer> treeMap;

    public TreeMapAdapter() {
        treeMap = new TreeMap<>();
    }

    @Override
    public void setup(int[] input) {
        treeMap = new TreeMap<>();
        for (int key : input) {
            treeMap.put(key, key);
        }
    }

    @Override
    public void insert(int key, int value) {
        treeMap.put(key, value);
    }

    @Override
    public void batchInsert(int[] keys) {
        for (int key : keys) {
            treeMap.put(key, key);
        }
    }

    @Override
    public boolean search(int key) {
        return treeMap.containsKey(key);
    }

    @Override
    public void delete(int key) {
        treeMap.remove(key);
    }

    @Override
    public int[] inOrderTraversal() {
        List<Integer> keys = new ArrayList<>(treeMap.keySet());
        return keys.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void clear() {
        treeMap.clear();
    }
}
