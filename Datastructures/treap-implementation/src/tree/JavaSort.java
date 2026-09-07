package tree;


// sort/JavaSort.java
import java.util.Arrays;

public class JavaSort implements SortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] copy = Arrays.copyOf(input, input.length);
        Arrays.sort(copy); // Java's built-in Dual-Pivot QuickSort or TimSort
        return copy;
    }
}
