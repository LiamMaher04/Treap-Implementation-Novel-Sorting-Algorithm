package tree;

// sort/TreapSort.java


import tree.Treap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreapSort implements tree.SortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        try {
            Treap<Integer, Integer> treap = new Treap<>();
            for (int val : input) {
                treap.put(val, val);
            }

            List<Integer> sorted = new ArrayList<>();
            for (var pos : treap.tree.inorder()) {
                if (!treap.isExternal(pos)) {
                    sorted.add(pos.getElement().getKey());
                }
            }

            return sorted.stream().mapToInt(i -> i).toArray();

        } catch (IOException e) {
            throw new RuntimeException("Error during TreapSort", e);
        }
    }
}
