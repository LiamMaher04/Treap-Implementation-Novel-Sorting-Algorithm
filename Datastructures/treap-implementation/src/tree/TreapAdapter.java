package tree;

import interfaces.Entry;
import interfaces.Position;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreapAdapter implements DataStructureTest {
    private Treap<Integer, Integer> treap;

    public TreapAdapter() {
        treap = new Treap<>();
    }

    @Override
    public void setup(int[] input) {
        treap = new Treap<>();
        for (int key : input) {
            try {
                treap.put(key, key);
            } catch (IOException e) {
                throw new RuntimeException("Failed to insert key: " + key, e);
            }
        }
    }

    @Override
    public void insert(int key, int value) {
        try {
            treap.put(key, value);
        } catch (IOException e) {
            throw new RuntimeException("Failed to insert key: " + key, e);
        }
    }

    @Override
    public void batchInsert(int[] keys) {
        for (int key : keys) {
            insert(key, key); // Insert each key as both key and value
        }
    }

    @Override
    public boolean search(int key) throws IOException {
        return treap.get(key) != null;
    }

    @Override
    public void delete(int key) {
        try {
            treap.remove(key);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete key: " + key, e);
        }
    }

    @Override
    public int[] inOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        for (Position<Entry<Integer, Integer>> pos : treap.tree.inorder()) {
            if (!treap.isExternal(pos)) {
                result.add(pos.getElement().getKey());
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void clear() {
        treap = new Treap<>();
    }
}
