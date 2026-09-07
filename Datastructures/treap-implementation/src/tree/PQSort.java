package tree;

// sort/PQSort.java


import java.util.PriorityQueue;

public class PQSort implements SortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : input) {
            pq.offer(num);
        }

        int[] sorted = new int[input.length];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = pq.poll();
        }

        return sorted;
    }
}
