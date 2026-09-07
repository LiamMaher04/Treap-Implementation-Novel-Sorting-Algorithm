package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;
import java.util.ArrayList;

public class AVLTreeMapAdapter implements DataStructureTest {
    private AVLTreeMap<Integer, Integer> avl;

    public AVLTreeMapAdapter() {
        avl = new AVLTreeMap<>();
    }

    @Override
    public void setup(int[] input) {
        avl = new AVLTreeMap<>();
        for (int key : input) {
            try {
                avl.put(key, key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void insert(int key, int value) {
        try {
            avl.put(key, value);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        return avl.get(key) != null;
    }

    @Override
    public void delete(int key) {
        try {
            avl.remove(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] inOrderTraversal() {
        ArrayList<Integer> result = new ArrayList<>();
        for (Position<Entry<Integer, Integer>> pos : avl.tree.inorder()) {
            if (!avl.isExternal(pos)) {
                result.add(pos.getElement().getKey());
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void clear() {
        avl = new AVLTreeMap<>();
    }
}
